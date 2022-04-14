import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class ConcurrentLinkedList
{
	class Node
	{
		Integer item;
		int key;
		AtomicMarkableReference<Node> next;


		public Node(Integer item)
		{
			this.item = item;
			this.key = item.hashCode();
			this.next = new AtomicMarkableReference<Node>(null, false);
		}

		public Node(int key, Node next)
		{
			this.item = null;
			this.key = key;
			this.next = new AtomicMarkableReference<Node>(null, false);
		}

	}

	Node head;
	AtomicInteger noteCount;

	class Window 
	{
		public Node pred, curr;
		Window(Node myPred, Node myCurr)
		{
			pred = myPred; curr = myCurr;
		}
	}
	

	public ConcurrentLinkedList()
	{

		Node tail = new Node(Integer.MAX_VALUE, null);
		head = new Node(Integer.MIN_VALUE, tail);
		noteCount = new AtomicInteger(0);

		while (!head.next.compareAndSet(null, tail, false, false));
	}

	public int getNoteCount()
	{
		return noteCount.get();
	}
		 
	public Window find(Node head, int key) 
	{
		Node pred = null, curr = null, succ = null;
		boolean[] marked = { false };
		boolean snip;
		retry: while (true) 
		{
			pred = head;
			curr = pred.next.getReference();
			while (true) 
			{
				succ = curr.next.get(marked);
				while (marked[0]) 
				{
					snip = pred.next.compareAndSet(curr, succ, false, false);
					if (!snip)
						continue retry;
					curr = succ;
					succ = curr.next.get(marked);
				}
				if (curr.key >= key)
					return new Window(pred, curr);
				pred = curr;
				curr = succ;
			}
		}
	}


	public boolean add(Integer item) 
	{
		int key = item.hashCode();
		while (true) 
		{
			Window window = find(head, key);
			Node pred = window.pred, curr = window.curr;
			if (curr.key == key)
			{
				return false;
			} 
			else 
			{
				Node node = new Node(item);
				node.next = new AtomicMarkableReference<Node>(curr, false);
				if (pred.next.compareAndSet(curr, node, false, false)) 
				{
					return true;
 				}
 			}
 		}
 	}

	 

	public boolean remove(Integer item) 
	{
		int key = item.hashCode();
		boolean snip;
		while (true) 
		{
			Window window = find(head, key);
			Node pred = window.pred, curr = window.curr;
		
			if (curr.key != key) 
			{
				return false;
			} 
			else 
			{
				Node succ = curr.next.getReference();
				snip = curr.next.compareAndSet(succ, succ, false, true);
				
				if (!snip)
					continue;
				pred.next.compareAndSet(curr, succ, false, false);
				noteCount.incrementAndGet();
				return true;
			}
		}
	}

	public boolean contains(Integer item) 
	{
		int key = item.hashCode();
		Window window = find(head, key);
		Node pred = window.pred, curr = window.curr;

		return (curr.key == key);
	}

	public static void main(String [] args)
	{

	}
}