import java.util.*;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Temperature
{

	public static Random rand = new Random();

	public static CyclicBarrier barrier;
	public static int NUM_SENSORS = 8;
	public static int LOWER_TEMP_BOUND = -100;
	public static int UPPER_TEMP_BOUND = 70;
	public static ArrayList<Integer> min;
	public static ArrayList<Integer> max;

	public static ConcurrentLinkedList temperatureStorage;

	// Scaling 1 hour to 60 iterations, where each iteration is a minute
	public static int HOUR = 60;	

	public static void main(String[] args)
	{
		barrier = new CyclicBarrier(NUM_SENSORS, new PrinterThread());
		temperatureStorage = new TemperatureLinkedList();
		multiSensorRead();
	}

	public static void multiSensorRead()
	{
		Thread[] threads = new Thread[NUM_SENSORS];

		try {
			for (int i = 0; i < NUM_SENSORS; i++) {
				threads[i] = new Thread(new SensorThread());
				threads[i].start();
			}

			for (int i = 0; i < NUM_SENSORS; i++) {
				threads[i].join();
			}

		} catch (Exception e) {
			System.err.println("Error with one of the threads");
		}
	}


	static class SensorThread extends Thread
	{
		@Override
		public void run()
		{
			int i = 0;
			while (i < HOUR)
			{
				for (int j = 0; j < 10; j++)
				{
					// System.out.println("RUnning inner loop");
					temperatureStorage.add(readTemperature());
				}
				try {
					barrier.await();
				} catch (Exception e) {
					//TODO: handle exception
				}
			}
		}

		public int readTemperature()
		{
			// System.out.println("Reading");

			int randomNum = rand.nextInt((UPPER_TEMP_BOUND - LOWER_TEMP_BOUND) + LOWER_TEMP_BOUND);
			System.out.println(randomNum);
			return randomNum;
		}
	} 

	static class PrinterThread implements Runnable
	{
		@Override
		public void run()
		{
			ArrayList<Integer> temp = temperatureStorage.getMinimum();
			System.out.println(temp);
		}
	}
}