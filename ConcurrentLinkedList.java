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

	public static void main(String [] args)
	{

	}
}