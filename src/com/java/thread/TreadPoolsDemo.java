package com.java.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试线程池
 */
public class TreadPoolsDemo {

//    public static List<String> list = new ArrayList<>();

    private static List<String> list = new CopyOnWriteArrayList();//全局变量，
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);//定义线程池

    public static void main(String[] args) {

        //implements Runnable 的线程类，提交用execute
        //implements Callable 的线程类，提交用submit
        executorService.execute(new MyTestThread(list));
        executorService.execute(new MyTestThread(list));
        executorService.execute(new MyTestThread(list));
        executorService.execute(new MyTestThread(list));

        //implements Runnable 的线程类,结束用shutdown()
        //implements Callable 的线程类，结束用shutdownnow()
        executorService.shutdown();

        try {
            Thread.sleep(4000);
            //最后输出全局变量list的最终大小值
            System.out.println("size=" + list.size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class MyTestThread implements Runnable{

    private List<String> list;

    public MyTestThread(List<String> list){
        this.list = list;
    }

    @Override
    public void run() {
        System.out.println("当前执行的线程名称是：" + Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            list.add("xx" + i);
        }
        System.out.println(list + " ------ list.size()=" + list.size());

    }
}
