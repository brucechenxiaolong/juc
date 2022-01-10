package com.java.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁练习
 * ReentrantLock
 */
public class LockDemo {

    private static ImportTaskListener taskListener = new ImportTaskListener();
//    private final ImportTaskListener taskListener;

    public LockDemo(ImportTaskListener taskListener) {
        this.taskListener = taskListener;
    }

    public static void main(String[] args) {
        BuyTickets buyTickets = new BuyTickets();
        new Thread(buyTickets,"B-线程").start();
        new Thread(buyTickets,"A-线程").start();
        new Thread(buyTickets,"C-线程").start();

        //启动任务监听
//        for (int i = 1; i <= 5; i++) {
//            new Thread(() -> {
//                taskListener.startListener();
//                System.out.println(Thread.currentThread().getName() + "线程被启动了！");
//            }, "A" + i).start();
//        }

    }

}

class ImportTaskListener{
    /**
     * 任务队列监听线程
     */
    private Thread taskListener;

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 启动监听
     */
    public void startListener() {
        try {
            lock.lock();
            if (taskListener != null && taskListener.isAlive()) {
                return;
            }
            taskListener = new Thread(() -> {
                while (true) {
                try {
                    System.out.println(Thread.currentThread().getName() + "-执行了。。。");
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            });
            taskListener.setName("taskTake");
            taskListener.start();
        } finally {
            lock.unlock();
        }
    }

}

/**
 * 多线程操作：
 * 公平锁：队列，先来后到
 * 非公平锁：允许加塞插队
 *非公平锁的优点在于吞吐量比公平锁大
 * 可重入锁（也叫递归锁）
 */
class BuyTickets implements Runnable{

    int ticketNums = 10;

    private final ReentrantLock lock = new ReentrantLock();//默认：非公平锁，可重入锁

    @Override
    public void run() {
        while (true){

            try {
                lock.lock();//开锁

                //执行代码块
                if(ticketNums > 0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":" + ticketNums--);
                }else{
                    break;
                }

            }finally {
                lock.unlock();//解锁
            }


        }
    }
}
