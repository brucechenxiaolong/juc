package com.java.jvm;

/**
 * 堆内存调优：
 * 在实际开发环境中：
 * -Xms 和 -Xmx 初始内存大小和最大内存大小必须一致，防止内存忽高忽低，产生停顿。
 *
 */
public class RunTimeDemo {
    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();//获取java虚拟机的最大内存量(占物理总内存的1/4)
        long totalMemory = Runtime.getRuntime().totalMemory();//获取java虚拟机的内存总量，初始分配大小(占物理总内存的1/64)
//        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("-Xmx=" + maxMemory + "(字节)、"+(maxMemory/(double)1024/1024/1024) + "GB");
        System.out.println("-Xms=" + totalMemory + "(字节)、" + (totalMemory/(double)1024/1024) + "MB");
//        System.out.println("-free=" + freeMemory + "(字节)、" + (freeMemory/(double)1024/1024) + "MB");
    }
}
