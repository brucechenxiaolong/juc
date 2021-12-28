package com.java.thread;

import java.util.concurrent.*;

public class ThreadCallableTest implements Callable<Boolean> {

    private String name;
    public ThreadCallableTest(String name){
        this.name = name;
    }

    @Override
    public Boolean call() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(name + "线程正在执行，当前执行到："+i);
        }
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadCallableTest thread1 = new ThreadCallableTest("张三");
        ThreadCallableTest thread2 = new ThreadCallableTest("李四");
        ThreadCallableTest thread3 = new ThreadCallableTest("王五");

        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //提交执行
        Future<Boolean> r1 = executorService.submit(thread1);
        Future<Boolean> r2 = executorService.submit(thread2);
        Future<Boolean> r3 = executorService.submit(thread3);

        //获取线程的执行结果
        boolean rs1 = r1.get();
        boolean rs2 = r2.get();
        boolean rs3 = r3.get();

        System.out.println(rs1);
        System.out.println(rs2);
        System.out.println(rs3);

        executorService.shutdownNow();
    }
}
