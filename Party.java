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
	public static int NUM_PRESENTS = 500000;

	public static BlockingQueue<Integer> bag;

	public static ConcurrentLinkedList presentChain;

	public static AtomicInteger noteCount ;

	public static Random rand;

	// Toggle this to true to enable print checking
	public static boolean printToggle = false;

	public static void main(String[] args)
	{
				
		presentChain = new ConcurrentLinkedList();
		
		fillBag();


		rand = new Random();

		final long startTime = System.currentTimeMillis();
		multiThreadHelp();
		final long endTime = System.currentTimeMillis();
		System.out.println("It took: " + (endTime - startTime) + " ms to finish");
		System.out.println("There are " + presentChain.getNoteCount() + " thank-you notes");

	}

	public static void fillBag()
	{
		ArrayList<Integer> tempList = new ArrayList<>(NUM_PRESENTS);
		for (int i = 0; i < NUM_PRESENTS; i++)
		{
			try
			{
				tempList.add(Integer.valueOf(i));

			}
			catch (Exception e)
			{

			}
		}
		// System.out.println(bag.size());
		Collections.shuffle(tempList);
		bag = new ArrayBlockingQueue<Integer>(NUM_PRESENTS, false, tempList);
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
			System.err.println("Error with one of the threads");
		}
	}


	static class PartyThread extends Thread 
	{
		@Override
		public void run() 
		{
			while (presentChain.getNoteCount() < NUM_PRESENTS) 
			{
				int randNum = rand.nextInt(2) + 1;

				int res = rand.nextInt(2);
				
				if (res == 0)
				{
					// System.out.println("Calling contains");
					int presentToFind = rand.nextInt(NUM_PRESENTS);
					if (presentChain.contains(presentToFind) && printToggle)
						System.out.println(presentToFind + " is in the list");
				}
				else 
				{
					Integer present = bag.poll();
					if (present == null)
						return;

					if (presentChain.add(present) && printToggle) 
						System.out.println("Added " + present + " to the list");

					if (presentChain.remove(present) && printToggle) 
						System.out.println("Removed " + present + " from the list");
				} 
			}
		}
	}

}