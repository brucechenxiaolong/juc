package com.java.timeunit;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * TimeUnit.DAYS          //天
 * TimeUnit.HOURS         //小时
 * TimeUnit.MINUTES       //分钟
 * TimeUnit.SECONDS       //秒
 * TimeUnit.MILLISECONDS  //毫秒
 *
 * public long toMillis(long d)    //转化成毫秒
 * public long toSeconds(long d)  //转化成秒
 * public long toMinutes(long d)  //转化成分钟
 * public long toHours(long d)    //转化成小时
 * public long toDays(long d)     //转化天
 *
 */
public class TimeUnitDemo {
    public static void main(String[] args) {

        //1天有24个小时    1代表1天：将1天转化为小时
        System.out.println(TimeUnit.DAYS.toHours(1) + "小时");

        //1小时有3600秒
        System.out.println(TimeUnit.HOURS.toSeconds(1) + "秒");

        //把3天转化成72小时
        System.out.println(TimeUnit.HOURS.convert( 3 , TimeUnit.DAYS ) + "小时");

        //延时用法
        try {
            TimeUnit.SECONDS.sleep(3);//线程延时3秒执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("执行完毕！");


        //结合其他JUC接口用法。
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
        try {
            boolean retFlag = blockingQueue.offer("1", 2L, TimeUnit.SECONDS);//超时时间设置为2秒
            if(retFlag){
                System.out.println("往队列中添加数据成功");
            }
            TimeUnit.SECONDS.sleep(3);

            String result = blockingQueue.poll(2L, TimeUnit.SECONDS);//消费队列
            System.out.println("队列的内容是：" + result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
