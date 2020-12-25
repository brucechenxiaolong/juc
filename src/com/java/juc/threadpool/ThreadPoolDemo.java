package com.java.juc.threadpool;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * 线程池的含义：如现在有一个分行网点，里面有5个受理窗口（这里的5个受理窗口可以同时接纳5名顾客办理银行业务）；
 * 现在有30个人来办理业务，需要等待的顾客是25位。
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //练习中使用，实际不用
//        testThreadPool();


        //实际开发中使用
        //maximumPoolSize 大小设置可以是：cpu处理器个数+1
        //如何获取电脑的cpu处理器数
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                2L,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 1; i <= 8; i++) {
            threadPool.execute(() ->{
                System.out.println("当前正在执行的线程是：" + Thread.currentThread().getName());
            });
        }
    }

    private static void testThreadPool() {
        //ExecutorService 定义线程池的接口
        //Executors 线程池数组类
        //方式一：固定线程池数；线程池中有5个线程，类似有个银行网点有5个受理窗口
//        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(5);
        //方式二：线程池中有只有1个线程，类似有个银行网点只有1个受理窗口
//        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
        //方式三：线程池中定义N个线程，可伸缩线程池
//        ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
        //线程池调用
//        try {
//            for (int i = 1; i <= 10; i++) {
//                threadPoolExecutor.execute(() ->{
//                    System.out.println("当前正在执行的线程是：" + Thread.currentThread().getName());
//                });
//                TimeUnit.SECONDS.sleep(1);//方式三演示时需要删除此行代码才能看到效果
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
