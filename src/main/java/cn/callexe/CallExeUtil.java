package cn.callexe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*需要第三方程序来处理时，有2种方法：
 * 1、直接调用本地exe程序
 * 2、写bat文件或.sh文件，程序调用
 * **/
public class CallExeUtil {

	public static void main(String[] args) throws IOException {
		System.out.println("执行...");
		Process exec = Runtime.getRuntime().exec(new String[]{"C:\\Program Files (x86)\\TeamViewer\\TeamViewer.exe"});
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
		String msg = null;
		while((msg = bufferedReader.readLine())!=null){
			System.out.println(msg);
		}
		//way1();
		//way2();
	}


	private static void way1() {
		try {
			List<String> commands = new ArrayList();
			commands.add("cmd.exe");
			commands.add("abc");
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(commands);
			Process pr = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void way2() {
		try {
			String[] cmds = {"C:/Windows/System32/cmd.exe","/c"," dir","c:",">","d://aa.txt"};
			Process pro = Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
