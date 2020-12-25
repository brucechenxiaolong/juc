package com.java.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *资源类
 */
class AirCondition{

	private int number = 0;

	//jdk1.6版本写法：
//	public synchronized void increment() throws Exception{
//		//1.判断
//		while(number != 0) {//此处不能用if
//			this.wait();//线程等待
//		}
//
//		//2.干活
//		number++;
//		System.out.println(Thread.currentThread().getName() + "\t" + number);
//
//		//3.通知
//		this.notifyAll();//通知其他线程运行：notify可能会导致死锁
//	}
//
//	public synchronized void decrement() throws Exception{
//		//1.判断
//		while(number == 0) {//不能用if判断：参考
//			this.wait();//线程等待
//		}
//
//		//2.干活
//		number--;
//		System.out.println(Thread.currentThread().getName() + "\t" + number);
//
//		//3.通知
//		this.notifyAll();
//	}


	//以上代码优化：
	//jdk1.8版本写法：
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void increment() throws Exception{

		lock.lock();
		try {
			//1.判断
			while(number != 0) {//此处不能用if
				condition.await();//线程等待
			}

			//2.干活
			number++;
			System.out.println(Thread.currentThread().getName() + "\t" + number);

			//3.通知
			condition.signalAll();//通知其他线程运行：notify可能会导致死锁
		}finally {
			lock.unlock();
		}

	}

	public void decrement() throws Exception{

		lock.lock();
		try {
			//1.判断
			while(number == 0) {//此处不能用if
				condition.await();//线程等待
			}

			//2.干活
			number--;
			System.out.println(Thread.currentThread().getName() + "\t" + number);

			//3.通知
			condition.signalAll();//通知其他线程运行：notify可能会导致死锁
		}finally {
			lock.unlock();
		}

	}


}

/**
 *题目：现在两个线程，可以操作初始值为0的一个变量，
 *实现一个线程对该变量加1，另一个线程对该变量减1
 *实现交替，来10轮，变量初始值为0
 *
 *多线程编程的套路：
 *1.高内聚低耦合前提下，线程-操作-资源类
 *2.判断、干活、通知
 *3.防止虚假唤醒
 *
 *
 */
public class ProdConsumerDemo {

	public static void main(String[] args) {

		int sum = 0;
		int j = 1;
		while (j <= 100) {
			sum = sum + j;
			System.out.println("sum=" + sum);
			j++;
		}


		//1.实例化资源类
		AirCondition airCondition = new AirCondition();

		//2.线程调用
		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					airCondition.increment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},"A").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					airCondition.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},"B").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					Thread.sleep(400);
					airCondition.increment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},"C").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					Thread.sleep(400);
					airCondition.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},"D").start();
	}
}
