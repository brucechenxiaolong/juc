package com.java.thread.example;

/**
 * 多线程操作同一个资源类，会出现线程不安全，数据紊乱
 */
public class TestTreadRunnable implements Runnable{

    //电影院有10张票
    private int ticketNumber = 10;

    @Override
    public void run() {
        while (true){
            if(ticketNumber <= 0){
                break;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "，获得了第：" + ticketNumber-- + "张票");
        }
    }

    public static void main(String[] args) {

        //多线程，操作同一个资源类
        TestTreadRunnable ticket = new TestTreadRunnable();

        new Thread(ticket,"小明").start();
        new Thread(ticket, "老师").start();
        new Thread(ticket,"黄牛").start();
    }

}
