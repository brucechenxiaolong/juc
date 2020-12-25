package com.java.juc;

import java.util.concurrent.TimeUnit;

/**
 * 锁池和等待池
 * 锁池:假设线程A已经拥有了某个对象(注意:不是类)的锁，而其它的线程想要调用这个对象的某个synchronized方法(或者synchronized块)，
 * 由于这些线程在进入对象的synchronized方法之前必须先获得该对象的锁的拥有权，但是该对象的锁目前正被线程A拥有，所以这些线程就进入了该对象的锁池中。
 * 等待池:假设一个线程A调用了某个对象的wait()方法，线程A就会释放该对象的锁后，进入到了该对象的等待池中
 *
 *
 *只要资源类中存在一个：synchronized 修饰的方法（可以存在多个）；
 *那么多个线程同时访问此方法，只能优先的第一个线程访问完之后，其他的线程才能逐个访问。（排队随机访问，线程的执行先后顺序不一定，由CPU分配）
 *synchronized 锁的不是方法，而是整个资源类（俗称：对象锁）
 */
class Phone{//资源类

	//普通锁：锁的是this对象 new Phone()
	public synchronized void sendEmail() throws Exception{
		TimeUnit.SECONDS.sleep(4);
		System.out.println("*****sendEmail");
	}
	public synchronized void sendSMS() throws Exception{
		System.out.println("*****sendSMS");
	}

	//静态锁：static全局锁Phone.class
	public static synchronized void sendEmail2() throws Exception{
		TimeUnit.SECONDS.sleep(4);
		System.out.println("*****sendEmail2");
	}
	public static synchronized void sendSMS2() throws Exception{
		System.out.println("*****sendSMS2");
	}

	//普通方法，不受锁影响，线程调用时，正常访问
	public void sayHello() throws Exception{
		System.out.println("*****sayHello");
	}
}

public class Lock8Demo05 {

	public static void main(String[] args) throws InterruptedException {

		Phone phone = new Phone();
		Phone phone2 = new Phone();

		new Thread(() -> {
			try {
				phone.sendEmail2();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"A").start();

		Thread.sleep(100);

		new Thread(() -> {
			try {
//				phone.sendSMS();
//				phone.sayHello();
				phone2.sendSMS2();
//				phone.sendSMS();

			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}
}
