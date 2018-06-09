package cn.synchronizedDemo;

public class SynchronizedEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;

	public  int next() {
		++currentEvenValue;
		//Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}
	public static void main(String[] args) {
		EvenChecker.test(new SynchronizedEvenGenerator());
	}

}
