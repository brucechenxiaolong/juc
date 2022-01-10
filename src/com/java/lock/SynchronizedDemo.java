package com.java.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
		TimeUnit.SECONDS.sleep(3);
		System.out.println("*****sendEmail");
	}
	public synchronized void sendSMS() throws Exception{
		System.out.println("*****sendSMS");
	}

	//静态同步锁：static全局锁Phone.class
	public static synchronized void sendEmail2() throws Exception{
		TimeUnit.SECONDS.sleep(3);
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

/**
 * 非公平可重入锁
 */
class Phone2 implements Runnable{//资源类

	//普通锁：锁的是this对象 new Phone()
	public synchronized void sendSMS() throws Exception {
		System.out.println(Thread.currentThread().getName() + "\t *****sendSMS");
		sendEmail();
	}

	public synchronized void sendEmail() throws Exception {
		System.out.println(Thread.currentThread().getName() + "\t *****sendEmail");
	}

	//--------------------------------ReentrantLock-也是：非公平可重入锁---------------------------------

	Lock lock = new ReentrantLock();

	@Override
	public void run() {
		get();
	}

	private void get() {
		lock.lock();
		lock.lock();//加锁和解锁必须配对使用，否则线程一直卡死，不能释放。

		try{
			System.out.println(Thread.currentThread().getName() + "\t *****invoked get()");
			set();
		}finally {
			lock.unlock();
//			lock.unlock();//加锁和解锁必须配对使用，否则线程一直卡死，不能释放。
		}
	}

	private void set() {
		lock.lock();
		try{
			System.out.println(Thread.currentThread().getName() + "\t *****invoked set()");
		}finally {
			lock.unlock();
		}
	}
}

/**
 * synchronized-修饰的方法，又名：对象锁
 * 是一种非公平锁，可重入锁
 */
public class SynchronizedDemo {

	public static void main(String[] args) throws InterruptedException {

		//1.加修饰符：synchronized 的两个方法调用
//		method01();
		//打印结果是：
		//*****sendEmail
		//*****sendSMS
		//什么原理？

		//2.加修饰符：synchronized 的一个方法调用和普通方法调用
//		method02();
		//打印结果是：
		//*****sayHello
		//*****sendEmail
		//什么原理？

		//3.加修饰符：synchronized 的两个方法调用；但是是不同对象访问
//		method03();
		//打印结果是：
		//*****sendSMS
		//*****sendEmail
		//什么原理？

		//4.加修饰符：static synchronized 的两个方法调用
//		method04();
		//打印结果是：
		//*****sendEmail2
		//*****sendSMS2
		//什么原理？

		//5.加修饰符：static synchronized 的两个方法调用；但是是不同对象访问
//		method05();
		//打印结果是：
		//*****sendEmail2
		//*****sendSMS2
		//什么原理？

		//6.1个静态同步方法，1个普通同步方法，同一部手机，先打印谁？
//		method06();
//		method07();

		//非公平锁，可重入锁
		//加锁的方法，调用加锁的方法，都是同一个线程操作。线程名称一样。
//		method08();




	}

	/**
	 * 非公平锁，可重入锁
	 * @throws InterruptedException
	 */
	private static void method08() throws InterruptedException {
		//非公平锁，可重入锁
		Phone2 p = new Phone2();

		new Thread(() -> {
			try {
				p.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"A").start();

		new Thread(() -> {
			try {
				p.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"B").start();

		TimeUnit.SECONDS.sleep(3);

		System.out.println("\t");
		System.out.println("\t");
		System.out.println("\t");
		System.out.println("\t");

		Thread t3 = new Thread(p, "C");
		Thread t4 = new Thread(p, "D");
		t3.start();
		t4.start();
	}

	private static void method07() throws InterruptedException {
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
				phone2.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}

	private static void method06() throws InterruptedException {
		Phone phone = new Phone();

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
				phone.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}

	/**
	 * 静态同步锁
	 * 5.加修饰符：static synchronized 的两个方法调用；但是是不同对象访问,结果是：互不影响
	 * @throws InterruptedException
	 */
	private static void method05() throws InterruptedException {
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
				phone2.sendSMS2();
			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}

	/**
	 * 静态同步锁
	 * 4.加修饰符：static synchronized 的两个方法调用
	 * @throws InterruptedException
	 */
	private static void method04() throws InterruptedException {
		Phone phone = new Phone();
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
				phone.sendSMS2();
			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}

	/**
	 * 3.加修饰符：synchronized 的两个方法调用；但是是不同对象访问
	 * @throws InterruptedException
	 */
	private static void method03() throws InterruptedException {
		Phone phone = new Phone();
		Phone phone2 = new Phone();
		new Thread(() -> {
			try {
				phone.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"A").start();

		Thread.sleep(100);

		new Thread(() -> {
			try {
				phone2.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}

	/**
	 * 2.加修饰符：synchronized 的一个方法调用和普通方法调用
	 * @throws InterruptedException
	 */
	private static void method02() throws InterruptedException {
		Phone phone = new Phone();
		new Thread(() -> {
			try {
				phone.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"A").start();

		Thread.sleep(100);

		new Thread(() -> {
			try {
				phone.sayHello();
			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}

	/**
	 * 1.加修饰符：synchronized
	 * @throws InterruptedException
	 */
	private static void method01() throws InterruptedException {
		Phone phone = new Phone();
		new Thread(() -> {
			try {
				phone.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"A").start();

		Thread.sleep(100);

		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}

		},"B").start();
	}
}
