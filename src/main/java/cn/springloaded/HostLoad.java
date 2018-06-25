package cn.springloaded;

public class HostLoad {

	public static void main(String[] args) {
		new Thread(new HostSon()).start();
	}
	
}

class Host{
	public void print(){
		System.out.println("host");
	}
}
class HostSon extends Host implements Runnable{

	@Override
	public void run() {
		while(true){
			print();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//通过运行时添加删除此方法，看打印信息
	public void print(){
		System.out.println("host son");
	}
}
