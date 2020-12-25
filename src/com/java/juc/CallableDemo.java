package com.java.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 有返回值的线程操作方法
 * 作用：在一个主线程中，如果有个线程处理的事情耗时很长可以单独出来一个带有返回值的线程，等这个返回值正确返回后此线程结束
 */
class MyThread3 implements Callable<Integer>{//Callable后面尖括号内容：支持各种数据类型

	@Override
	public Integer call() throws Exception {
		System.out.println(Thread.currentThread().getName() + "，线程被调用了。*****");
		return 1024;//返回值
	}

}

/**
 *另一种方式线程调用：有返回值
 */
public class CallableDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread3());
		new Thread(futureTask,"A").start();
		//返回值获取，必须放在主线程最后调用
		Integer returnVal = futureTask.get();
		System.out.println("此线程的返回值是：" + returnVal);
	}
}
