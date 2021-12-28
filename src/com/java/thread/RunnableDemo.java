package com.java.thread;

class MyThread implements Runnable{

    private String name;
    public MyThread(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "==>"+i);
        }
    }
}

/**
 * 普通线程调用
 */
public class RunnableDemo {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("ThreadA");
        MyThread thread2 = new MyThread("ThreadB");
        MyThread thread3 = new MyThread("ThreadC");

        new Thread(thread1).start();
        new Thread(thread2).start();
        new Thread(thread3).start();
    }
}


