package cn.hotswap.agent;

public class Task {

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Task t = new Task();
				for (int i = 0; i < 100; i++) {
					System.out.println(t.getNum());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public int getNum() {
		return 666;
	}
	
}
