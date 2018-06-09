package cn.runnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import cn.synchronizedDemo.page715_28.LiftOffRunner;

public class TestBlockingQueues {
	static void getkey(){
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static void getkey(String message){
		System.out.println(message);
		getkey();
	}
	static void test(String message,BlockingQueue<LiftOff> queue){
		System.out.println(message);
		LiftOffRunner runner = new LiftOffRunner(queue);
		Thread t = new Thread(runner);
		t.start();
		for (int i = 0; i < 5; i++) {
			runner.add(new LiftOff(5));
		}
		getkey("Press 'Enter' ("+message+")");
		t.interrupt();
		System.out.println("完成"+message+"测试");
	}
	public static void main(String[] args) {
		test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
		test("ArrayBlockingQueue", new ArrayBlockingQueue<>(5));
		test("SynchronousQueue",new SynchronousQueue<LiftOff>());
		
	}
}
