package com.train;


import java.util.Random;

public class ThreadCreate1 implements Runnable {
	private final int sleepTime; // random sleep time for thread
	private final String taskName; // name of task
	private final static Random generator = new Random();
	public ThreadCreate1(String name)
	{
		taskName = name; // set task name
		// pick random sleep time between 0 and 5 seconds
		sleepTime = generator.nextInt(5000); // milliseconds
	} // end PrintTask constructor

	public void run()
	{
		try // put thread to sleep for sleepTime amount of time
		{
			System.out.printf("%s going to sleep for %d milliseconds.\n",
			taskName, sleepTime);
			Thread.sleep(sleepTime); // put thread to sleep
		} // end try
		catch (InterruptedException exception)
		{
			System.out.printf("%s %s\n", taskName,	"terminated prematurely due to interruption");
		} // end catch
		// print task name
		System.out.printf("%s done sleeping\n", taskName);
	} // end method run

	public static void main(String[] args) {
		// create each thread with a new targeted runnable
		Thread thread1 = new Thread(new ThreadCreate1("task1"));
		Thread thread2 = new Thread(new ThreadCreate1("task2"));
		Thread thread3 = new Thread(new ThreadCreate1("task3"));
		System.out.println("Threads created, starting tasks.");

		// start threads and place in runnable state
		thread1.start();
		thread2.start();
		thread3.start();
		System.out.println("Tasks started, main ends.\n");
	}
} 

