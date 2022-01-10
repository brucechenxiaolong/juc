package com.java.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程操作AtomicInteger
 */
public class AtomicIntegerDemo {
    private static final int THREADS_COUNT = 10;//定义10个线程
//    public static int count = 0;//多线程操作，数据会丢失
    //线程安全：AtomicInteger
    public static AtomicInteger count = new AtomicInteger(0); //AtomicInteger类里面有： volatile 修饰的int变量。private volatile int value;

    public static void increase(){
//        count++;//不安全
        count.incrementAndGet();//线程安全
    }

    public static void main(String[] args) throws InterruptedException {

        count.addAndGet(10);
        System.out.println("数据共：" + count.toString() + "条");

//        atomicIntegerTest();

//        AtomicIntegerTest atomicIntegerTest = new AtomicIntegerTest();
//        Thread thread1 = new Thread(atomicIntegerTest);
//        Thread thread2 = new Thread(atomicIntegerTest);
//        thread1.start();
//        thread2.start();
//        //join()方法是为了让main主线程等待thread1、thread2两个子线程执行完毕
//        thread1.join();
//        thread2.join();
//        System.out.println("AtomicInteger add result = " + AtomicIntegerTest.atomicInteger.get());
//        System.out.println("CommonInteger add result = " + AtomicIntegerTest.commonInteger);

    }

    private static void atomicIntegerTest() {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    for (int j = 0; j < 1000; j++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 2){//保证前面的线程都执行完
            Thread.yield();
        }
        System.out.println(count);
        //这里运行了10个线程，每个线程对count变量进行1000此自增操作，如果上面这段代码能够正常并发的话，最后的结果应该是10000才对，但实际结果却发现每次运行的结果都不相同，都是一个小于10000的数字。
    }
}

class AtomicIntegerTest implements Runnable {

    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static int commonInteger = 0;

    public void addAtomicInteger() {
        atomicInteger.getAndIncrement();
    }

    public void addCommonInteger() {
        commonInteger++;
    }

    @Override
    public void run() {
        //可以调大10000看效果更明显
        for (int i = 0; i < 10000; i++) {
            addAtomicInteger();
            addCommonInteger();
        }
    }
}
