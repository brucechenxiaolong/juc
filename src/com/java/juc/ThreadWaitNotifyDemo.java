package com.java.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程等待通告-实例代码演示
 * 题目：实现两个线程，可以操作初始值为0的一个变量；
 * 实现一个线程对变量加1，一个线程对该变量减1
 * 实现交替，来10轮，变量初始值还是0
 *
 * 实现步骤和思路：
 * 1.高内聚低耦合前提下，线程操作资源类
 * 2.判断/干活/通知
 * 3.多线程交互中，必须要防止多线程的虚假唤醒（不能用if判断，只能用while）
 */
public class ThreadWaitNotifyDemo {
	public static void main(String[] args) {
		Xx x = new Xx();
		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					x.increment();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"A").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					x.decrement();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"B").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					x.increment();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"C").start();

		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					x.decrement();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"D").start();
	}
}


class Xx{

	public int number = 0;
	public synchronized void increment() throws InterruptedException {
		//1.判断
		while(number != 0) {
			this.wait();
		}
		//2.干活
		number++;
		System.out.println(Thread.currentThread().getName() + "线程，\t当前变量被加后的值是：" + number);
		//3.通知
		this.notifyAll();
	}

	public synchronized void decrement() throws InterruptedException {
		//1.判断
		while(number == 0) {
			this.wait();
		}
		//2.干活
		number--;
		System.out.println(Thread.currentThread().getName() + "线程，\t当前变量被减后的值是：" + number);
		//3.通知
		this.notifyAll();
	}
}