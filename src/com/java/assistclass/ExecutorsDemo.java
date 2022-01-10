package com.java.assistclass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors 可以获取线程池
 * 常用的获取线程池有三种方式
 */
public class ExecutorsDemo {
    public static void main(String[] args) {

        //一池固定线程数
//        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        //一池一线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //一池多线程
        ExecutorService threadPool = Executors.newCachedThreadPool();
    }

}
