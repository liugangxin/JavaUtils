package cn.io.file;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadUtil {

	
	public static void main(String[] args) throws InterruptedException {
		
		ThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1000);
		for(int i=0;i<1000;i++){
			pool.submit(new MyTestRun("线程"+i));
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.shutdown();
		for(int i=0;i<10;i++){
			Thread.sleep(1000);
			
			System.out.println(pool.isTerminated());
		}
	}
	
	static class MyTestRun implements Runnable{

		String name;
		
		public MyTestRun(String name) {
			super();
			this.name = name;
		}

		@Override
		public void run() {
			try {
				for(int i=0;i<6;i++){
					System.out.println(name+":"+i);
					Thread.sleep(avgRandom(1000,3000));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static Random r = new Random();

	public static boolean randomPersent(int persent) {
		return r.nextInt(100) < persent;
	}

	public static boolean random(double rate) {
		return Math.random() < rate;
	}

	public static int avgRandom(int min, int max) {
		if (min > max) {
			int temp = max;
			max = min;
			min = temp;
		}
		int rNum = r.nextInt(max - min + 1);
		return rNum + min;
	}
}
