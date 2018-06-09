package cn.synchronizedDemo;

public class SynchObject {
	public static void main(String[] args) {
		final DualSynch ds = new DualSynch();
		new Thread(){
			public void run() {
				ds.f();
			};
		}.start();
				ds.g();
		
	}
}
