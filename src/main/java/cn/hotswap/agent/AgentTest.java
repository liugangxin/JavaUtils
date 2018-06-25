package cn.hotswap.agent;

import java.io.IOException;
import java.lang.reflect.Field;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

public class AgentTest {

	public static void main(String[] args) throws AgentLoadException, AgentInitializationException, IOException,
			AttachNotSupportedException, NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		// jdk下的jre的bin目录,VirtualMachine need the attach.dll in the jre of the JDK.
		System.setProperty("java.library.path", "F:\\JAVA\\JDK8\\jre\\bin");
		Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
		fieldSysPath.setAccessible(true);
		fieldSysPath.set(null, null);
		VirtualMachine vm = VirtualMachine.attach("9136");// jvm的pid号
		// vm.loadAgent("/Users/java/agent.jar");
		// 参数[代理jar的位置, 传递给代理的参数]
		vm.loadAgent("c:/agent.jar", "cn.hotswap.agent.Task,c:/Task.class");
	}

}
