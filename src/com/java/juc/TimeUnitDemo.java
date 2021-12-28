package com.java.juc;

import java.util.concurrent.TimeUnit;

/**
 * TimeUnit是java.util.concurrent包下面的一个类，表示给定单元粒度的时间段
 * 作用：
 * 1、时间颗粒度转换
 * 2、延时
 */
public class TimeUnitDemo {
    public static void main(String[] args) {


        //调用方式一：延时,作为参数；线程池使用：前提：池内只有一个线程，循环调用线程：SystemRegist;每次间隔10秒钟。
//        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new SystemRegist(), 10, 10, TimeUnit.SECONDS);

        //调用方式二：延时,单独使用sleep,替代Thread.sleep( 5 * 1000 );
//        new Thread(() -> {
//            try {
//                System.out.println("开始。。。");
//                TimeUnit.SECONDS.sleep(10);
//                System.out.println("延时10秒，完成了。。。");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        },"xxx").start();

        //调用方式三：时间颗粒度转换 小转大，不精确，大转小靠谱
        System.out.println(TimeUnit.SECONDS.toMinutes(110));//秒转分钟
        System.out.println(TimeUnit.SECONDS.toMillis(1));//秒转毫秒

    }
}
