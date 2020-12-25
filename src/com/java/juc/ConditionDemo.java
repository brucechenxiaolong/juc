package com.java.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *多线程condition
 *
 *题目：多线程之间按照顺序调用，实现 A->B->C
 *
 *三个线程启动，要求如下：
 *A线程打印5次，B线程打印10次，C线程打印15次
 *然后分别轮询10次
 *
 *
 */
public class ConditionDemo {
	public static void main(String[] args) {
		ShareData shareData = new ShareData();
		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				shareData.print5();
			}
		},"A").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				shareData.print10();
			}
		},"B").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				shareData.print15();
			}
		},"C").start();
	}
}

class ShareData{
	private int number = 1;//A=1;B=2;C=3
	private Lock lock = new ReentrantLock();//锁

	private Condition c1 = lock.newCondition();//钥匙1
	private Condition c2 = lock.newCondition();//钥匙1
	private Condition c3 = lock.newCondition();//钥匙1

	public void print5() {
		lock.lock();
		try {
			//1.判断
			while(number != 1) {
				//等待
				c1.await();
			}
			//2.业务处理
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			//3.标志位修改
			number = 2;
			//4.通知第二个方法
			c2.signal();

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public void print10() {
		lock.lock();
		try {
			//1.判断
			while(number != 2) {
				//等待
				c2.await();
			}
			//2.业务处理
			for (int i = 1; i <= 10; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			//3.标志位修改
			number = 3;
			//4.通知第二个方法
			c3.signal();

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public void print15() {
		lock.lock();
		try {
			//1.判断
			while(number != 3) {
				//等待
				c3.await();
			}
			//2.业务处理
			for (int i = 1; i <= 15; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i);
			}
			//3.标志位修改
			number = 1;
			//4.通知第二个方法
			c1.signal();

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

}
