package com.java.thread;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 带返回参数的多线程用法：Callable
 * callable线程的使用场景：
 * 1.
 */
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1.待返回参数的 线程调用
//        callableMethod01();

        //2.实现Callable接口，重写call方法（线程有返回值）
        //callableMethod02();

        //--------------------------------------------callable的核心用法-------------------------------------------
        //3.一个方法中同时做了几件事，每一件事情都要返回结果，然后再合并处理
        /**
         * 如查一个数据集合，第一页至第一百页，返回总页数的总结集，然后导出。
         * 一次需要limit 0 10000,这样，一个SQL查询出非常慢。
         * 但用100个线程，一个线程只查limit0 10 就非常快了，
         * 利用多线程的特性，返回多个集合，在顺序合并成总集合。
         */
        //3.callable-有序
        method03();

        //4.callable-无序
//        method04();
        //---------------------------------------------callable的核心用法------------------------------------------
    }

    /**
     * 定义线程池
     */
    public static ExecutorService executors = Executors.newFixedThreadPool(5);

    /**
     * 4.callable-无序
     */
    private static void method04() {
        List<User> totalList = new ArrayList<User>();
        CompletionService<List<User>> cs = new ExecutorCompletionService<List<User>>(executors);
        for(int i = 0; i < 5; i++) {
            cs.submit(new DemoThread(i));
        }
        // 合并多线程执行结果
        for(int i = 0; i < 5; i++) {
            try {
                totalList.addAll(cs.take().get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //得到分页后结果总共集合
        for (int i = 0; i < totalList.size(); i++) {
            System.out.println(totalList.get(i).getName());
        }
        executors.shutdownNow();
    }

    /**
     * 3.一个方法中同时做了几件事，每一件事情都要返回结果，对这些返回结果进行并集操作。
     */
    private static void method03() {
        List<User> totalList = new ArrayList<User>();
        List<Future<List<User>>> list = new ArrayList<Future<List<User>>>();
        try {
            for (int page = 0; page < 5; page++) {
                //现场休眠3秒
                try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
                //executos.submit 返回线程未来结果
                Future<List<User>> future = executors.submit(new DemoThread(page));
                list.add(future);
            }

            //获取现场的返回结果值
            for (Future<List<User>> dataFuture : list) {
                //获得第一个任务的结果，如果调用get方法，当前线程会等待任务执行完毕后才往下执行
                totalList.addAll(dataFuture.get());  //dataFuture.get()  这里会阻塞，并且有顺序。
            }

            System.out.println();
            System.out.println("合并后输出结果如下：");

            //合并多线程执行结果
            for (int i = 0; i < totalList.size(); i++) {
                System.out.println(totalList.get(i).getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        executors.shutdownNow();

    }

    /**
     * 2.实现Callable接口，重写call方法（线程有返回值）
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
     * 1.待返回参数的 线程调用
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void callableMethod01() throws InterruptedException, ExecutionException {
        Obj obj = new Obj();
        System.out.println("开始调用callable线程...");
        FutureTask<String> task = new FutureTask<>(() ->
            obj.getStr()
        );
        new Thread(task, "BB").start();
        System.out.println("callable线程调用返回值是：" + task.get());
    }

}


class DemoThread implements Callable<List<User>> {

    private int page;

    public DemoThread(int page) {
        this.page = page;
    }

    @Override
    public List<User> call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "；查询第" + page + "页");
        List<User> list = new ArrayList<User>();
        User user = new User();
        user.setAge("" + page);
        user.setName("zhangsan" + page);
        list.add(user);
        return list;
    }
}

class User{
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

class Obj{
    public String getStr(){
//        if(1 == 1){
//            int i = 1/0;
//        }
        //现场休眠3秒
        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
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
