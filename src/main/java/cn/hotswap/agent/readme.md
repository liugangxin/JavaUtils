1、在 Java SE 6 的 Instrumentation 当中，有一个跟 premain“并驾齐驱”的“agentmain”方法，可以在 main 函数开始运行之后再运行。跟 premain 函数一样， 开发者可以编写一个含有“agentmain”函数的 Java 类：
2、与“Premain-Class”类似，开发者必须在 manifest 文件里面设置“Agent-Class”来指定包含 agentmain 函数的类。
3、但agentmain 需要在 main 函数开始运行后才启动。
4、agentmain方式无法像pre main方式那样在命令行指定代理jar，因此需要借助Attach Tools API
使用com.sun.tools.attach 的VirtualMachine类
使用attach pid 来得到相应的 VirtumalMachine，
使用loadAgent 方法指定AgentMain所在类并加载
5、Attach API 不是 Java 的标准 API，而是 Sun 公司提供的一套扩展 API，用来向目标 JVM ”附着”（Attach）代理工具程序的。有了它，开发者可以方便的监控一个 JVM，运行一个外加的代理程序。


本次示例：
1）将Agent.java这一个文件单独打jar包
2）修改MANIFEST.MF文件，如下：
Manifest-Version: 1.0
Agent-Class: cn.hotswap.agent.Agent
Can-Redefine-Classes: true
3）找到需要修改class的进程id，连同参数(旧class名,新class文件路径)，一起传入执行AgentTest进行load前面的agent。