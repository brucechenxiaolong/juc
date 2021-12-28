package com.java.thread;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 带返回参数的多线程用法：Callable
 */
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Obj obj = new Obj();
        FutureTask<String> task = new FutureTask<>(() ->
            obj.getStr()
        );
        Thread thread = new Thread(task);
        thread.start();
        System.out.println(task.get());
    }


}

class Obj{
    public String getStr(){
        return "xxxxx";
    }
}
