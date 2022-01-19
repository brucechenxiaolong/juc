package com.java.gc;

import java.util.concurrent.TimeUnit;

/**
 * java.lang.OutOfMemoryError:unable to create new native thread（不能创建本地线程）
 * 问题：创建线程太多，linux中一个进程可以创建最多线程数1024个；超过1024就会出现异常
 * 解决办法：改代码将线程数降到最低；或者修改linux服务器配置，扩大linux默认限制。
 */
public class NativeThreadDemo {
    public static void main(String[] args) {
        for (int i = 0; ; i++) {//死循环
            System.out.println("**************i=" + i);
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "" + i).start();
        }
    }
}
