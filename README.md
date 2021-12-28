# juc use concurrent package coding your projects
java.util.concurrent

# include packages
java.util.concurrent.atomic
java.util.concurrent.locks
java.util.function

# parallelStream-用法说明及注意事项：
在项目开发过程中，使用流式处理时，在数据量较大的情况下，通过并行流可以开启多个线程来执行处理，parallelStream与Stream的区别在于parallelStream开启了多线程的处理方式，所以当对方法替换的同时，必须关注方法处理的过程中，是否用到线程不安全的类型例如HashMap,ArrayList等待，方法内部使用不会出现线程安全问题，当变量在方法外部定义尤为重要，使用并行流需谨慎，时刻考虑线程安全问题。
否则可能造成程序死锁，或数据的准确性。造成的后果完全取决于使用非线程安全类的效果。