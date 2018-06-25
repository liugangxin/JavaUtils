Instrumentation JDK中对它介绍如下：这个类为JVM上运行时的程序提供测量手段。很多工具通过Instrumenation 修改方法字节码 实现收集数据目的。这些通过Instrumentaion搜集数据的工具不会改变程序的状态和行为。这些良好的工具包括  monitoring agents,profilers, coverage analyzers, 和 event loggers。
有两种方式来获取Instrumentation接口实例：
1、启动JVM时指定agent类。这种方式，Instrumentation的实例通过agent class的premain方法被传入。
2、JVM提供一种当JVM启动完成后开启agent机制。这种情况下，Instrumention实例通过agent代码中的的agentmain传入。
     
方式一：
1）打包：在bin路径下，对class文件编辑打包，执行：jar -cmf manifest.txt agent.jar cn/hotswap/base/Agent.class
2）修改jar包中的MANIFEST.MF文件，一般会漏了第三行，完整的内容如下：
Manifest-Version: 1.0
Created-By: 1.8.0_111 (Oracle Corporation)
Premain-Class: cn.hotswap.base.Agent
3）执行jar包即可，执行命令：java -javaagent:agent.jar -cp . cn.hotswap.base.AgentTest
方式二：
1）直接在eclipse中，右键->Export导出jar包，然后执行方式一的2、3步骤即可。