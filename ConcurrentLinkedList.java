import java.util.*;

public class ConcurrentLinkedList 
{
	private class Node
	{
		T item;
		int key;
		Node next;
	}

	class Window 
	{
		public Node pred, curr;
		Window(Node myPred, Node myCurr)
		{
			pred = myPred; curr = myCurr;
		}
	}

	public boolean compareAndSet(
		T expectedReference,
		T newReference,
		boolean expectedMark,
		boolean newMark);
	)
	{
		;
	}

	public boolean attemptMark(T expectedReference, boolean newMark)
	{
		;
	}

	public T get(boolean[] marked)
	{
		;
	}

	public boolean attemptMark(T expectedReference,
 	boolean newMark);


	public T get(boolean[] marked);
	
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

	1

	public boolean add(T item) 
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
				node.next = new AtomicMarkableReference(curr, false);
				if (pred.next.compareAndSet(curr, node, false, false)) 
				{
					return true;
 				}
 			}
 		}
 	}

	 17

	public boolean remove(T item) 
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
				return true;
			}
		}
	}

	public static void main(String [] args)
	{

	}
}