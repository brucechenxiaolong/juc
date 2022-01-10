package com.java.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * 自旋锁：指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，
 * 这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU
 *
 * 实例演示：两个线程(A,B)同时访问一个资源类：A在访问的时候，B在循环等待；A用完后，B再进入。
 *
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //线程占位
    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in !");
        while (!atomicReference.compareAndSet(null, thread)){

            try {
                System.out.println(Thread.currentThread().getName() + "\t 等待中。。。");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //线程执行完后，释放位置。给其他线程使用
    public void unLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked unLock()");
    }

    public static void main(String[] args) {
        //线程操作资源类
        SpinLockDemo spinLockDemo = new SpinLockDemo();


        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        }, "A").start();

        //主线程停止1秒，保证A线程先运行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        }, "B").start();

    }
}
