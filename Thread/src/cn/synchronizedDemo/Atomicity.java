package cn.synchronizedDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Atomicity implements Runnable {
	int i;
	 void f1(){
		i++;
		System.out.println("f1() = " + i);
	}
	 void f2(){
		i += 3;
		System.out.println("f2() = " + i);
	}
	@Override
	public synchronized void run() {
		f1();
		f2();
		
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		Atomicity atomicity = new Atomicity();
		for (int i = 0; i < 10; i++) {
			exec.execute(atomicity);
		}
		
	}

}
