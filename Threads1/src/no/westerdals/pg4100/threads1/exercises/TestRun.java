package no.westerdals.pg4100.threads1.exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import no.westerdals.pg4100.threads1.RunnableExample;

public class TestRun {
	public static void main(String[] args) {
		RunnableExample runnable = new RunnableExample();
		//Thread thread = new Thread(runnable);
		ExecutorService executor = Executors.newCachedThreadPool();
		System.out.println("TestRun: Reading from object runnable.getI()= "
				+ runnable.getI());
		//thread.start();
		executor.execute(runnable);
		
		// Wait...
		JOptionPane.showMessageDialog(null, "Wait...");
		System.out.println("TestRun: Reading from object after run"
				+ " has terminated: runnable.getI()= " + runnable.getI());
		
		System.out.println("TestRun: Trying to start the thread again......");
		runnable.setI(5);
		// thread.start();	// throws IllegalStateException
		//new Thread(runnable).start();
		executor.execute(runnable);	// reuse previous identical threads
		System.out.println("TestRun: Ending main()");
	}
}