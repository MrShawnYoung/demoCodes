package jdk;

import java.util.concurrent.locks.LockSupport;

/**
 * 阻塞工具类
 * 
 * @author 杨弢
 * 
 */
public class LockSupportDemo {
	public static Object obj = new Object();
	static ChangeObjectThread t1 = new ChangeObjectThread("t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("t2");

	public static class ChangeObjectThread extends Thread {

		public ChangeObjectThread(String name) {
			super.setName(name);
		}

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println("in" + getName());
				LockSupport.park();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(100);
		t2.start();
		LockSupport.unpark(t1);
		LockSupport.unpark(t2);
		t1.join();
		t2.join();
	}
}