package cn.hotswap.agent;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
//只可以修改方法内部，不可增加、删除方法等
public class Agent {
	// agentArgs就是VirtualMachine.loadAgent()的第二个参数
	public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException{
		/*inst.addTransformer(new Transformer(), true); 
        inst.retransformClasses(Task.class); 
        System.out.println("Agent Main Done"); */
		/*Class[] classes = inst.getAllLoadedClasses();
        for(Class cls :classes){
            System.out.println(cls.getName());
        }*/
        try {
        	String[] split = agentArgs.split(",");
        	String oldClassName = split[0];
        	String newClassPath = split[1];
			File f = new File(newClassPath);//读取新的class文件内容
			byte[] reporterClassFile = new byte[(int) f.length()];
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			in.readFully(reporterClassFile);
			in.close();

			// 把旧类的定义与新的类定义关联起来
			ClassDefinition reporterDef = new ClassDefinition(Class.forName(oldClassName),
					reporterClassFile);
			inst.redefineClasses(reporterDef);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
