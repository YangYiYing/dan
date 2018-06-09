package cn.synchronizedDemo.page706_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Demo1 implements Runnable{

	public synchronized void run() {
		try {
			
			wait();
			System.out.println("我解放了");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
class Demo2{
	public void a(){
		Demo1 demo1 = new Demo1();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(demo1);
		
		try {
			Thread.sleep(1000);
			synchronized (demo1) {
				demo1.notifyAll();
			}
			exec.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public class page706_21 {
	public static void main(String[] args) {
		Demo2 demo2 = new Demo2();
		demo2.a();
	}
	
}
