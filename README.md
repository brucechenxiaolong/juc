# juc use concurrent package coding your projects
java.util.concurrent

# include core packages
java.util.concurrent.atomic

java.util.concurrent.locks

java.util.function



java-线程越多越快吗？

1.线程多了可以提高程序并行执行的速度，但是并不是越多越好，其中，每个线程都要占用内存，多线程就意味着更多的内存资源被占用； 

2.一个CPU不是同时执行两个线程的，他是轮流执行的，所以线程太多，CPU必须不断的在各个线程间快速更换执行，线程间的切换无意间消耗了许多时间，所以CPU有效利用率反而是下降的。


Java线程共有5中状态，分别为：新建(new)、就绪(runnable)、运行(running)、堵塞(blocked)、死亡(dead)。

1、新建状态：当创建一个线程时，此线程进入新建状态，但此时还未启动。

2、就绪状态：创建好线程后调用线程的start()方法，该状态的线程位于可运行线程池中，等待被线程调度选中，获取cpu 的使用权。等待期间线程处于就绪状态

3、运行状态：当线程获得cpu的使用权后，线程进入运行状态，开始执行run()方法。

4、阻塞状态：线程在运行过程中可能由于各种原因放弃了cpu 使用权，暂时停止运行，即进入阻塞状态。阻塞的情况分为：等待阻塞、同步阻塞、其他阻塞等三种情况。

5、死亡状态：线程run()、main() 方法执行结束，或者因异常退出了run()方法，则该线程结束生命周期。


# parallelStream-用法说明及注意事项：
在项目开发过程中，使用流式处理时，在数据量较大的情况下，通过并行流可以开启多个线程来执行处理，parallelStream与Stream的区别在于parallelStream开启了多线程的处理方式，所以当对方法替换的同时，必须关注方法处理的过程中，是否用到线程不安全的类型例如HashMap,ArrayList等待，方法内部使用不会出现线程安全问题，当变量在方法外部定义尤为重要，使用并行流需谨慎，时刻考虑线程安全问题。
否则可能造成程序死锁，或数据的准确性。造成的后果完全取决于使用非线程安全类的效果。












idea的快捷键
1. Ctrl + E (显示历史文件访问记录)
2. Alt + Enter (可以补全调用方法返回的类型)
3. Ctrl + O (查看实现方法)
4. Shift + Alt + M (提取当前选择为方法)
5. Ctrl + Alt + B (查找接口实现类的方法)
6. Ctrl + G (查看方法被哪些地方调用)
7. Fn + Alt + Insert (Bean类属性实现：getter 和 setter 方法)
8. Shift + F6 (重命名文件、方法、属性等)
9. Ctrl + Shift + U (大小写转换)
10. Alt + Shift +R (重命名类名)
11. Ctrl + Alt + V (提取当前选择为变量)
12. Ctrl + Alt + F (提取当前选择为属性)
13. Ctrl + Alt + C (提取当前选择为常量)
14. Ctrl + Alt + P (提取当前选择为参数)
15. Ctrl + Alt + T (提取代码块至if、try等结构中)
16. Ctrl + Shift + Alt + S (打开项目构造页面，添加包)
17. Ctrl + F9  (idea编译文件，免重启服务)
18. sout + 回车（Idea 生成 System.out.println(); ）
