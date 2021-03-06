java基础  
**Arrays.sort实现原理和Collection实现原理**（后者是用前者实现的，如下 ）  

    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        Object[] a = list.toArray();
        Arrays.sort(a, (Comparator)c);
        ListIterator i = list.listIterator();
        for (int j=0; j<a.length; j++) {
            i.next();
            i.set(a[j]);
        }
    }
**foreach和while的区别(编译之后)**  
**线程池的种类，区别和使用场景**（[线程池](http://1181731633.iteye.com/admin/blogs/2342551)）  
**分析线程池的实现原理和线程的调度过程**（同上）  
**线程池如何调优**（1设置最大线程数，与cpu处理器一致。2最小数，防止大量线程空闲造成浪费。3设置任务最大值，防止太多任务，返回合适错误）  
**线程池的最大线程数目根据什么确定**（一般核数乘2，也可核数*((计算时间x+等待时间y)/x)）  
**动态代理的几种方式**（jdk动态代理、cglib动态代理。前者需要继承接口invocationHandler实现；后者底层使用ASM在内存中动态的生成被代理类的子类，简单易容且速度更快）  
**HashMap的并发问题**（因为多线程操作hash指定的链表，可能造成同时操作，比如覆盖丢失上一个链表最后一个元素的next等。）  
**了解LinkedHashMap的应用吗**（除了支持默认的插入顺序，还支持访问顺序。所谓访问顺序(access-order)是指在迭代遍历列表中的元素时最近访问的元素会排在LinkedHashMap的尾部，可以用来做LRU算法）  
**反射的原理，反射创建类实例的三种方式是什么？**([文章](https://blog.csdn.net/qq_35415600/article/details/70199995)[原理](https://www.cnblogs.com/techspace/p/6931397.html)反射是指在运行状态中,动态获取类的信息以及动态调用对象的方法的功能。是从缓存或JVM获得类的信息的，即缓存没有就去jvm拿，其实通过类加载机制就可看出，反射的基础是加载了类的信息到jvm中。三种方式分别为1、通过对象的getClass方法进行获取；2、任何数据类型(包括基本数据类型)都具备着一个静态的属性class，通过它可直接获取到该类型对应的Class对象。使用具体的类，调用类中的静态属性class即可；3、通过Class.forName()方法获取。)  
**cloneable接口实现原理，浅拷贝or深拷贝**（实现该接口，如果接口简单实用super.clone()那仅仅实现了浅拷贝，即对象类型的属性字段是直接赋值引用的。若想深拷贝，必须针对对象类型的属性字段手动拷贝或特殊处理等。）  
**Java NIO使用**（IO是阻塞面向流。NIO非阻塞式面向缓存区(缓冲区（Buffer）、通道（Channel）、选择器（Selector）)，核心在于通道channel和缓存区，缓存区有ByteBuffer、IntBuffer等，NIO的IO多路复用模型是建立在内核提供的多路分离函数select基础之上的，即select一个线程处理多个IO请求操作。可用来做聊天通讯，以及游戏引擎等）  
**hashtable和hashmap的区别及实现原理，hashmap会问到数组索引，hash碰撞怎么解决**  
hashtable和hashmap的区别有：线程安全不同（HashTable的方法同步，HashMap的方法未同步）、对null的处理不同（HashTable的key和value都不允许null值的存在，HashMap的key和value允许null值的存在，另外ConcurrentHashMap也一样不允许null值存在）、增长率不同（HashTable是默认为11，增长的方式是oid * 2+1
HashMap是默认16，而且增长一定是2的指数增长）、哈希值的使用不同（HashTable是直接使用对象的哈希值，HashMap是使用处理过的哈希值）
hashmap的结构：数组+链表组成，根据hash值定位到数组某个位置上，再put时候，用if (e.hash == hash && ((k = e.key) == key || key.equals(k))) 来判断是否(Object) key相等。另外，在java8中，HashMap加入了红黑树结构，当链表元素较多(默认8个)时，链表就会转化为红黑树

**arraylist和linkedlist区别及实现原理**  
ArrayList实现原理就是动态数组，为什么叫动态数组呢，就是当ArrayList容量扩大时，底层的数组的容量会自动扩大50%,并且ArrayList是线程不安全的。
其优点是查询消耗的时间短，属于随机查询。而相对于LinkedList而言它的添加、删除操作要更耗时。当元素超出数组内容，会产生一个新数组，将原来数组的数据复制到新数组中，再将新的元素添加到新数组中。
对于ArrayList在存储大量数据时会大量浪费很多空间，原因就是数组的起始容量为10，当数组要增长时增长公式（newcapacity=(oldcapacity * 3)/2+1），相当于增加的50%，这样就会浪费大量空间，如果在你知道你要存储数据的容量前提下，最好声明容量大小。也可以使用trimToSize去掉多余的空间。  
LinkedList的实现原理基于链表，用引用指向下一个元素。其优点就是方便添加和删除元素，而arraylist的优点恰恰的LinkedList的缺点，其查询方法消耗的时间要大于arraylist。

**反射中，Class.forName和ClassLoader区别**  
ClassLoader就是遵循双亲委派模型最终调用启动类加载器的类加载器，实现的功能是“通过一个类的全限定名来获取描述此类的二进制字节流”，获取到二进制流后放到JVM中。Class.forName()方法实际上也是调用的CLassLoader来实现的，只是实现也是调用了CLassLoader。但Class.forName加载类是默认将类进了初始化，而ClassLoader的loadClass并没有对类进行初始化，只是把类加载到了虚拟机中。  
**String，Stringbuffer，StringBuilder的区别？**  
运行速度快慢为：StringBuilder > StringBuffer > String，String最慢的原因：
String为字符串常量，而StringBuilder和StringBuffer均为字符串变量，即String对象一旦创建之后该对象是不可更改的，但后两者的对象是变量，是可以更改的。
而StringBuilder和StringBuffer的对象是变量，对变量进行操作就是直接对该对象进行更改，而不进行创建和回收的操作，所以速度要比String快很多。就是说，如果String有str+"1",str+"2"等操作，就会造成创建、回收对象，而Stringbuilder仅一个对象操作，就很快。
String：适用于少量的字符串操作的情况
StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况  

**有没有可能2个不相等的对象有相同的hashcode**（可能，因为对象的hashCode()方法可以被重载，同理equal()方法也可以重载。另外hashCode()重载一般要求不变性，即一个Object对象，不能总变化hash值，应该在对象构造时确定后就不再变更。）  

**简述NIO的最佳实践，比如netty，mina**  
**TreeMap的实现原理**（红黑树，TreeMap初始化的时候会初始化下列参数，第一个Comparator是可以自己定义实现的一个比较的实现，默认为Null,那么默认的比较方式就是compare方法。Entry<K,V> root默认为Null。其中Entry内部维护了left,right,parent,color其中color默认是black。）  

JVM相关  
**类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，他们的执行顺序**  
1． 父类静态成员和静态初始化块 ，按在代码中出现的顺序依次执行
2． 子类静态成员和静态初始化块 ，按在代码中出现的顺序依次执行
3． 父类实例成员和实例初始化块 ，按在代码中出现的顺序依次执行
4． 父类构造方法
5． 子类实例成员和实例初始化块 ，按在代码中出现的顺序依次执行
6． 子类构造方法
结论：对象初始化的顺序，先静态方法，再构造方法，每个又是先基类后子类。  

JVM内存分代  
Java 8的内存分代改进  
JVM垃圾回收机制，何时触发MinorGC等操作  
jvm中一次完整的GC流程（从ygc到fgc）是怎样的，重点讲讲对象如何晋升到老年代，几种主要的jvm参数等  
你知道哪几种垃圾收集器，各自的优缺点，重点讲下cms，g1  
新生代和老生代的内存回收策略  
Eden和Survivor的比例分配等  
深入分析了Classloader，双亲委派机制  
JVM的编译优化  
对Java内存模型的理解，以及其在并发中的应用  
指令重排序，内存栅栏等  
OOM错误，stackoverflow错误，permgen space错误  
JVM常用参数  
tomcat结构，类加载器流程  
volatile的语义，它修饰的变量一定线程安全吗  
g1和cms区别,吞吐量优先和响应优先的垃圾收集器选择  
说一说你对环境变量classpath的理解？如果一个类不在classpath下，为什么会抛出ClassNotFoundException异常，如果在不改变这个类路径的前期下，怎样才能正确加载这个类？  
说一下强引用、软引用、弱引用、虚引用以及他们之间和gc的关系  

JUC/并发相关
ThreadLocal用过么，原理是什么，用的时候要注意什么  
Synchronized和Lock的区别  
synchronized 的原理，什么是自旋锁，偏向锁，轻量级锁，什么叫可重入锁，什么叫公平锁和非公平锁  
concurrenthashmap具体实现及其原理，jdk8下的改版  
用过哪些原子类，他们的参数以及原理是什么  
cas是什么，他会产生什么问题（ABA问题的解决，如加入修改次数、版本号）  
如果让你实现一个并发安全的链表，你会怎么做  
简述ConcurrentLinkedQueue和LinkedBlockingQueue的用处和不同之处  
简述AQS的实现原理  
countdowlatch和cyclicbarrier的用法，以及相互之间的差别?  
concurrent包中使用过哪些类？分别说说使用在什么场景？为什么要使用？  
LockSupport工具  
Condition接口及其实现原理  
Fork/Join框架的理解  
jdk8的parallelStream的理解  
分段锁的原理,锁力度减小的思考  

Spring  
Spring AOP与IOC的实现原理  
Spring的beanFactory和factoryBean的区别  
为什么CGlib方式可以对接口的实现进行代理？  
RMI与代理模式  
Spring的事务隔离级别，实现原理  
对Spring的理解，非单例注入的原理？它的生命周期？循环注入的原理，aop的实现原理，说说aop中的几个术语，它们是怎么相互工作的？  
Mybatis的底层实现原理  
MVC框架原理，他们都是怎么做url路由的  
spring boot特性，优势，适用场景等  
quartz和timer对比  
spring的controller是单例还是多例，怎么保证并发的安全  

分布式相关  
Dubbo的底层实现原理和机制  
描述一个服务从发布到被消费的详细过程  
分布式系统怎么做服务治理  
接口的幂等性的概念  
消息中间件如何解决消息丢失问题  
Dubbo的服务请求失败怎么处理  
重连机制会不会造成错误  
对分布式事务的理解  
如何实现负载均衡，有哪些算法可以实现？  
Zookeeper的用途，选举的原理是什么？  
数据的垂直拆分水平拆分。  
zookeeper原理和适用场景  
zookeeper watch机制  
redis/zk节点宕机如何处理  
分布式集群下如何做到唯一序列号  
如何做一个分布式锁  
用过哪些MQ，怎么用的，和其他mq比较有什么优缺点，MQ的连接是线程安全的吗  
MQ系统的数据如何保证不丢失  
列举出你能想到的数据库分库分表策略；分库分表后，如何解决全表查询的问题。  

算法&数据结构&设计模式  
海量url去重类问题（布隆过滤器）  
数组和链表数据结构描述，各自的时间复杂度  
二叉树遍历  
快速排序  
BTree相关的操作  
在工作中遇到过哪些设计模式，是如何应用的  
hash算法的有哪几种，优缺点，使用场景  
什么是一致性hash  
paxos算法  
在装饰器模式和代理模式之间，你如何抉择，请结合自身实际情况聊聊  
代码重构的步骤和原因，如果理解重构到模式？  

数据库  
MySQL InnoDB存储的文件结构  
索引树是如何维护的？  
数据库自增主键可能的问题  
MySQL的几种优化  
mysql索引为什么使用B+树  
数据库锁表的相关处理  
索引失效场景  
高并发下如何做到安全的修改同一行数据，乐观锁和悲观锁是什么，INNODB的行级锁有哪2种，解释其含义  
数据库会死锁吗，举一个死锁的例子，mysql怎么解决死锁  

Redis&缓存相关  
Redis的并发竞争问题如何解决  
了解Redis事务的CAS操作吗  
缓存机器增删如何对系统影响最小，一致性哈希的实现  
Redis持久化的几种方式，优缺点是什么，怎么实现的  
Redis的缓存失效策略  
缓存穿透的解决办法  
redis集群，高可用，原理  
mySQL里有2000w数据，redis中只存20w的数据，如何保证redis中的数据都是热点数据  
用Redis和任意语言实现一段恶意登录保护的代码，限制1小时内每用户Id最多只能登录5次  
redis的数据淘汰策略  

网络相关  
http1.0和http1.1有什么区别  
TCP/IP协议  
TCP三次握手和四次挥手的流程，为什么断开连接要4次,如果握手只有两次，会出现什么  
TIME_WAIT和CLOSE_WAIT的区别  
说说你知道的几种HTTP响应码  
当你用浏览器打开一个链接的时候，计算机做了哪些工作步骤  
TCP/IP如何保证可靠性，数据包有哪些数据组成  
长连接与短连接  
Http请求get和post的区别以及数据包格式  
简述tcp建立连接3次握手，和断开连接4次握手的过程；关闭连接时，出现TIMEWAIT过多是由什么原因引起，是出现在主动断开方还是被动断开方。  

其他  
maven解决依赖冲突,快照版和发行版的区别  
Linux下IO模型有几种，各自的含义是什么  
实际场景问题，海量登录日志如何排序和处理SQL操作，主要是索引和聚合函数的应用  
实际场景问题解决，典型的TOP K问题  
线上bug处理流程  
如何从线上日志发现问题  
linux利用哪些命令，查找哪里出了问题（例如io密集任务，cpu过度）  
场景问题，有一个第三方接口，有很多个线程去调用获取数据，现在规定每秒钟最多有10个线程同时调用它，如何做到。  
用三个线程按顺序循环打印abc三个字母，比如abcabcabc。  
常见的缓存策略有哪些，你们项目中用到了什么缓存系统，如何设计的  
设计一个秒杀系统，30分钟没付款就自动关闭交易（并发会很高）  
请列出你所了解的性能测试工具  
后台系统怎么防止请求重复提交？  
