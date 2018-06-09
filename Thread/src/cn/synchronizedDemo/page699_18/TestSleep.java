package cn.synchronizedDemo.page699_18;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TestSleep implements Runnable{

	@Override
	public void run() {
		int i = 1;
		while (!Thread.interrupted()) {
			try {
				//System.out.println(Thread.interrupted());
				TimeUnit.SECONDS.sleep(1);
				System.out.println("secound : "+i++);
			} catch (InterruptedException e) {
				System.out.println("interrupt()");
			}
		}
		
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		//Future<?> submit = exec.submit(new TestSleep());
		Thread thread = new Thread(new TestSleep());
		thread.start();
		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.interrupted());
		thread.interrupt();
		System.out.println(Thread.interrupted());
		//.cancel(true);
		System.out.println("调用interrupt");
	}
	 
	
}
