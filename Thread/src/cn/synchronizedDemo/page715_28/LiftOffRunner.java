package cn.synchronizedDemo.page715_28;

import java.util.concurrent.BlockingQueue;

import cn.runnable.LiftOff;

public class LiftOffRunner implements Runnable{
	private BlockingQueue<LiftOff> rockets;
	
	public LiftOffRunner(BlockingQueue<LiftOff> rockets) {
		super();
		this.rockets = rockets;
	}

	public void add(LiftOff liftOff){
		try {
			rockets.put(liftOff);
		} catch (InterruptedException e) {
			System.out.println("Interrupded during put()");
		}
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				LiftOff take = rockets.take();
				take.run();
			}
		} catch (Exception e) {
			System.out.println("醒着的花");
		}
		System.out.println("退出 LiftOffRunner");
		
		
	}

}
