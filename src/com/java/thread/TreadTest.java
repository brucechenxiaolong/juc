package com.java.thread;

import java.util.concurrent.Callable;

public class TreadTest {

    public static void main(String[] args) {
        //1.继承Thread类，重写run方法（其实Thread类本身也实现了Runnable接口）
//		ThreadTest1 myThread = new ThreadTest1();
//		myThread.start();

        //2.实现Runnable接口，重写run方法
//		ThreadTest2 myThread = new ThreadTest2();
//        Thread thread = new Thread(myThread);
//        thread.start();

        //3.实现Callable接口，重写call方法（线程有返回值）
//		ThreadTest3 myThread = new ThreadTest3();
//        FutureTask<String> futureTask = new FutureTask<String>(myThread);
//        Thread thread = new Thread(futureTask);
//        thread.start();
        //或者
//		try {
//	        FutureTask<String> ft = new FutureTask<String>(new ThreadTest3());
//	        new Thread(ft, "A").start();
//	        ft.get();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}

        //4.使用线程池（有返回值）：通过Executors提供四种线程池
//		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        for (int i = 0; i < 5; i++) {
//            final int j = i;
//            cachedThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    for (int k = 0; k < 2; k++) {
//                        System.out.printf("Thread %d is printing\n", j);
//                        try {
//                            Thread.currentThread().sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }

        //线程反复调用不停止
//        new MainThread().start();

    }

}

/**
 * 线程反复调用不停止
 *  while(true){}
 */
class MainThread extends Thread{

    /**
     * 线程休眠时间设置 毫秒
     */
    public static final long SLEEP_TIME = 2000;

    @Override
    public void run(){
        try{
            while(true){
                System.out.println("线程被调用");
                try {
                    this.sleep(this.SLEEP_TIME*5);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}

/**
 * 1.继承Thread类，重写run方法（其实Thread类本身也实现了Runnable接口）
 * @author bruce
 *
 */
class ThreadTest1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadTest1 is printing");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 2.实现Runnable接口，重写run方法
 * @author bruce
 *
 */
class ThreadTest2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadTest2 is printing");
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 3.实现Callable接口，重写call方法（有返回值）
 * 方法有返回值：类型有很多：对象，集合，String,Integer ....
 * 方法有异常
 * 优势：有返回值
 * @author bruce
 *
 */
class ThreadTest3 implements Callable<String> {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadTest3 is printing");
            Thread.currentThread().sleep(2000);
        }
        return "Done";
    }
}