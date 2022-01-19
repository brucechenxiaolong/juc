package com.java.gc;

/**
 * jvm参数调优
 * 典型的设置案例：
 * -Xms128m -Xmx4096m -Xss1024k -XX:MetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseParallelGC
 */
public class JVMParamInit {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("******hello world!*******");
//        Thread.sleep(Integer.MAX_VALUE);
        //默认初始一个50MB的大字节数组。把jvm内存撑爆
        // VM options = -Xms10m -Xmx10m -XX:+PrintGCDetails
        byte[] b = new byte[50*1024*1024];
        //上面代码抛异常：java.lang.OutOfMemoryError: Java heap space
    }
}
