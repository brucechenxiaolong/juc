package com.java.thread.locktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁代码实例
 * lock.lock();//加锁
 * lock.unlock();//解锁
 */
public class ReentrantLockTest {
    private static final Lock lock = new ReentrantLock();

    /**
     * 实现下面效果：
     * 线程A获取了锁
     * 线程A释放了锁
     * 线程B获取了锁
     * 线程B释放了锁
     * 线程C获取了锁
     * 线程C释放了锁
     * 线程D获取了锁
     * 线程D释放了锁
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> test(),"线程A").start();
        new Thread(() -> test(),"线程B").start();
        new Thread(() -> test(),"线程C").start();
        new Thread(() -> test(),"线程D").start();
    }

    public static void test(){
        try{
            lock.lock();//加锁
            System.out.println(Thread.currentThread().getName() + "获取了锁");
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName() + "释放了锁");
            lock.unlock();//解锁
        }
    }
}
