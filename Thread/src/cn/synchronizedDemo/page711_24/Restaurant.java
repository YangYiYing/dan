package cn.synchronizedDemo.page711_24;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Meal{
	private final int orderNum;
	public Meal(int orderNum){
		this.orderNum = orderNum;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Meal " + orderNum ;
	}
	
}
//服务生
class WaitPerson implements Runnable{
	private Restaurant restaurant;
	public WaitPerson(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal == null) {
						wait();
					}
				}
				//上餐
				System.out.println("Waitperson got "+restaurant.meal);
				synchronized (restaurant.chef) {
					restaurant.meal = null;
					restaurant.chef.notifyAll();
				}
				synchronized (restaurant.busBoy) {
					System.out.println("通知BusBoy");
					restaurant.busBoy.isClear = true;
					restaurant.busBoy.notifyAll();
				}
			}
			
		} catch (InterruptedException e) {
			System.out.println("WaitPerson intreeupted!");
		}
		
	}
	
}
//厨师
class Chef implements Runnable{
	Restaurant restaurant;
	private int count = 0;
	public Chef(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal != null) {
						wait();
					}
				}
				if (++count == 10) {
					System.out.println(count);
					System.out.println("Out of food closing"); 
					restaurant.exec.shutdownNow();
					//return ;
				}
				System.out.println("Order up");
				synchronized (restaurant.waitPerson) {
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.notify();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (Exception e) {
			System.out.println("Chef interrupted");
		}
	}
	
}

class BusBoy implements Runnable{
	private Restaurant restaurant;
	boolean isClear = false;
	public BusBoy(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (restaurant.busBoy) {
					while (!isClear) {
						wait();
					}
					System.out.println("清理盘子！");
					System.out.println("清理完毕");
					isClear = false;
				
					TimeUnit.MILLISECONDS.sleep(100);
					restaurant.busBoy.notifyAll();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}

public class Restaurant {
	Meal meal;
	ExecutorService exec = Executors.newCachedThreadPool();
	WaitPerson waitPerson = new WaitPerson(this);
	Chef chef = new Chef(this);
	BusBoy busBoy = new BusBoy(this);
	public Restaurant(){
		exec.execute(chef);
		exec.execute(waitPerson);
		exec.execute(busBoy);
	}
	public static void main(String[] args) {
		new Restaurant();
	}
	
	
}
