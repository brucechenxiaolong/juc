package com.java.jmm;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO CXL-综合应用
 * 涵盖的技术有：volatile/CAS/AtomicInteger/BlockingQueue/Thread/
 *
 * 实现如下效果：
 *
 * A_product生产线程启动...
 * B_consumer消费线程启动...
 *
 *
 * A_product	 插入队列--1--成功
 * B_consumer	 消费队列--1--成功
 * A_product	 插入队列--2--成功
 * B_consumer	 消费队列--2--成功
 * A_product	 插入队列--3--成功
 * B_consumer	 消费队列--3--成功
 * A_product	 插入队列--4--成功
 * B_consumer	 消费队列--4--成功
 * A_product	 插入队列--5--成功
 * B_consumer	 消费队列--5--成功
 *
 *
 *
 * 5秒钟时间到，线程叫停，活动结束！！！！
 * A_product	 停止生产，表示FLAG=false
 * B_consumer	 超过2秒钟没有取到蛋糕，消费退出！
 *
 */
public class ProdConsumerBlockQueueDemo {

    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));

        //生产者方法调用
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "生产线程启动...");
            try {
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A_product").start();

        //消费者方法调用
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "消费线程启动...");
            System.out.println();
            System.out.println();
            try {
                myResource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B_consumer").start();


        //5秒执行完后main线程退出，不加如下代码则一直循环生产-消费
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒钟时间到，线程叫停，活动结束！！！！");
        myResource.stop();

    }
}

/**
 * 资源类
 */
class MyResource{

    private boolean FLAG = true;//默认开启：进行生产+消费； 必须用：volatile 进行修饰
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;
    //参数传递的必须是接口。接口开发,灵活开发
    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println("当前接口实现类使用的是：" + blockingQueue.getClass().getName());
    }

    /**
     * 生产者
     */
    public void myProd() throws InterruptedException {

        String data;
        boolean retFlag;
        while (FLAG){
            data = atomicInteger.incrementAndGet() + "";
            retFlag = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if(retFlag){
                System.out.println(Thread.currentThread().getName() + "\t 插入队列--" + data + "--成功");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t 插入队列--" + data + "--失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(Thread.currentThread().getName() + "\t 停止生产，表示FLAG=false");
    }

    /**
     * 消费者
     */
    public void myConsumer() throws InterruptedException {
        String result = null;
        while (FLAG){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);//消费队列
            if(result == null || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒钟没有取到蛋糕，消费退出！");
                System.out.println();
                System.out.println();
                return;//*****
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列--" + result + "--成功");
        }
    }

    /**
     * 指定什么时候停止调用
     */
    public void stop(){
        this.FLAG = false;//volatile-修饰的变量，具有全局通知的功能。
    }

}
