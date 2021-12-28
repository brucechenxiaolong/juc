package com.java.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁练习
 * ReentrantLock
 */
public class LockDemo {
    public static void main(String[] args) {
        BuyTickets buyTickets = new BuyTickets();
        new Thread(buyTickets,"A-线程").start();
        new Thread(buyTickets,"B-线程").start();
        new Thread(buyTickets,"C-线程").start();
    }
}

class BuyTickets implements Runnable{

    int ticketNums = 10;


    private final ReentrantLock lock = new ReentrantLock();

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
