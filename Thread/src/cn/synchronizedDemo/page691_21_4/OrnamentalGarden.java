package cn.synchronizedDemo.page691_21_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGarden {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executors = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			executors.execute(new Entrance(i));
		}
		TimeUnit.SECONDS.sleep(3);
		Entrance.cancel();
		executors.shutdown();
		if (!executors.awaitTermination(250, TimeUnit.MILLISECONDS)) {
			System.out.println("some tasks were not terminaterd!");
		}
		System.out.println("Total : "+ Entrance.getTotalCount());
		System.out.println("Sum of Entrances : "+ Entrance.sunEntrances());
	}
}
