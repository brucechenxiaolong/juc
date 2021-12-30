# juc use concurrent package coding your projects
java.util.concurrent

# include packages
java.util.concurrent.atomic
java.util.concurrent.locks
java.util.function

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
