import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicMarkableReference;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Party
{
	// Number of threads we are using
	public static int NUM_SERVANTS = 4;
	
	// Number of presents from guests
	public static int NUM_PRESENTS = 5000;

	public static BlockingQueue<Integer> bag;

	public static ConcurrentLinkedList presentChain;

	public static AtomicInteger noteCount;

	public static Random rand;

	public static void main(String[] args)
	{
		
		bag = new ArrayBlockingQueue<Integer>(NUM_PRESENTS, false);
		
		presentChain = new ConcurrentLinkedList();
		
		fillBag();

		noteCount = new AtomicInteger(0);

		rand = new Random();

		multiThreadHelp();

	}

	public void run()
	{
		;
	}


	public static void fillBag()
	{
		for (int i = 0; i < NUM_PRESENTS; i++)
		{
			try
			{
				bag.add(Integer.valueOf(i));

			}
			catch (Exception e)
			{

			}
		}
	}

	public static void multiThreadHelp()
	{
		Thread[] threads;
		
		threads = new Thread[NUM_SERVANTS];

		try
		{
			for (int i = 0; i < NUM_SERVANTS; i++)
			{
				threads[i] = new Thread(new PartyThread());
				threads[i].start();
			}
	
			for (int i = 0; i < NUM_SERVANTS; i++)
			{
				threads[i].join();
			}
			
		}
		catch (Exception e)
		{

		}
	}


static class PartyThread extends Thread {
	private int threadNumber;

	@Override
	public void run() 
	{
		while (noteCount.get() < NUM_PRESENTS) 
		{
			int randNum = rand.nextInt(2) + 1;

			int res = rand.nextInt(2);
			Integer present = bag.poll();
			if (present == null)
				return;
				
			if (res == 0) 
			{
				if (presentChain.add(present))
					// System.out.println("Added " + present + " to the list");
				if (presentChain.remove(present))
					// System.out.println("Removed " + present + " from the list");
				noteCount.incrementAndGet();
			} else 
			{
				if (presentChain.contains(present))
					System.out.println(present + " is in the list");
			}
		}
	}
}
}