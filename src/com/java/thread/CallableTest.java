package com.java.thread;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 带返回参数的多线程用法：Callable
 */
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        callableMethod01();
        callableMethod02();



    }

    /**
     * 3.实现Callable接口，重写call方法（线程有返回值）
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void callableMethod02() throws InterruptedException, ExecutionException {
        //3.实现Callable接口，重写call方法（线程有返回值）
        MyThread1 myThread = new MyThread1();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(myThread);
        new Thread(futureTask, "A").start();
        //多个线程调用Callable，最终只执行一次。要想执行多次必须实例化多个FutureTask
        new Thread(futureTask, "B").start();
        //获取futureTask的线程返回值
        System.out.println("执行结果是：" + futureTask.get());

        int result01 = 100;
        int result02 = futureTask.get();
        System.out.println("计算结果是：" + result01 + result02);
    }

    /**
     * 待返回参数的 线程调用
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void callableMethod01() throws InterruptedException, ExecutionException {
        Obj obj = new Obj();
        FutureTask<String> task = new FutureTask<>(() ->
            obj.getStr()
        );
        new Thread(task, "BB").start();
        System.out.println("线程调用返回值是：" + task.get());
    }


}

class Obj{
    public String getStr(){
        return "xxxxx";
    }
}


class MyThread1 implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception {
//        int i = 1/0;//代码异常测试
        System.out.println(Thread.currentThread().getName() + " 被执行！");
        TimeUnit.SECONDS.sleep(4);
        return 1024;
    }
}
