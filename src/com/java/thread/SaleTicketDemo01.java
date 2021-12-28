package com.java.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：三个售票员        卖出    30张票
 *
 * 如何编写企业级的多线程代码
 * 答：固定的编程套路+模板
 * 1.在高内聚低耦合的前提下
 * 2.如何编写多线性：线程		操作(对外暴露的调用方法)		资源类（实例变量+实例方法）
 *
 */
public class SaleTicketDemo01 {

	public static void main(String[] args) {

		//第一步：实例化资源类
		Ticket ticket = new Ticket();

		/**
		 * 注意：
		 * 1.以下new出的线程.start() 方法并不是立马就运行，而是根据系统cpu自动分配的，启动时间也是不确认的。
		 * 2.线程的状态：Thread.State 可以看到线程一共有6种状态：NEW,RUNNABLE,BLOCKED,WAITING,TIMED_WAITING,TERMINATED
		 * 每次线程调度执行效果都不一样的，运行的线程都是随机的。
		 */
		//******************************************老版本方式一*********************************************
		//Thread(Runnable target, String name)
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (int i = 1; i <= 40; i++) {
//					ticket.sale();
//				}
//			}
//		},"A").start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (int i = 1; i <= 40; i++) {
//					ticket.sale();
//				}
//			}
//		},"B").start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (int i = 1; i <= 40; i++) {
//					ticket.sale();
//				}
//			}
//		},"C").start();
		//******************************************老版本方式一*********************************************


		//******************************************简洁版线程调用(Lambda表达式1.8新特性)*********************************************

		new Thread(() -> { for (int i = 1; i <= 40; i++) ticket.sale(); },"A").start();
		new Thread(() -> { for (int i = 1; i <= 40; i++) ticket.sale(); },"B").start();
		new Thread(() -> { for (int i = 1; i <= 40; i++) ticket.sale(); },"C").start();

	}
}


class Ticket{//资源类
	private int number = 30;

	//核心内容是：Lock
	//添加并发锁，实现独立单线程排队进出。上锁，解锁；使用可重入锁（ReentrantLock）
	Lock lock = new ReentrantLock();
	public void sale() {

		lock.lock();
		try {
			if(number > 0) {
				//输出：当前线程的id
				System.out.println(Thread.currentThread().getName()+"\t卖出第：" + (number--) + "\t 还剩余：" + number);
			}
		} finally {
			lock.unlock();
		}
	}
}