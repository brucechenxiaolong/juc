package com.java.thread.threadpool;

import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池的含义：如现在有一个分行网点，里面有5个受理窗口（这里的5个受理窗口可以同时接纳5名顾客办理银行业务）；
 * 现在有30个人来办理业务，需要等待的顾客是25位。
 */
public class ThreadPoolDemo {

//    private static List<String> list = new ArrayList<>();
    private static List<String> list = new CopyOnWriteArrayList();//全局变量

    //建议 maximumPoolSize=5;blockingQueue=3;加起来正好16 等于一般电脑的cpu处理器数
    private static ExecutorService threadPool = new ThreadPoolExecutor(2,
            3,
            2L,TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());//CallerRunsPolicy-策略方针是：保证所有线程都要执行完毕，不抛出异常。

    public static void main(String[] args) {
        //练习中使用，实际不用
//        testThreadPool();

        //实际开发中使用
        //maximumPoolSize 大小设置可以是：cpu处理器个数+1个线程 = 8+1
        //如何获取电脑的cpu处理器数->逻辑处理器
        System.out.println("CPU处理器数=" + Runtime.getRuntime().availableProcessors());

        //模拟多个线程操作
        //请求的线程过多，会有报错：java.util.concurrent.RejectedExecutionException
        for (int i = 1; i <= 30; i++) {//i的最大值是：maximumPoolSize+blockingQueue=16
//            System.out.println("i="+i);
            threadPool.execute(() ->{
                System.out.println("当前正在执行的线程是：" + Thread.currentThread().getName());
                try {
//                    TimeUnit.SECONDS.sleep(3);
                    for (int j = 0; j < 5; j++) {
                        list.add("xxx" + j);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "我终于执行完了。。。");

            });

//            try {
//                //线程休眠：可以演示出：corePoolSize的作用 只有2个线程在处理
//                TimeUnit.SECONDS.sleep(3);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }

        try {
            Thread.sleep(4000);
            System.out.println("------------------------------------------");
            System.out.println("list.size()=" + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
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
