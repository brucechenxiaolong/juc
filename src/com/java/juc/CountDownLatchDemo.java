package com.java.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 辅助工具类的作用：
 * 当教室里有6个人要放学，只有等6个都走了，班长才能锁门
 *
 */
public class CountDownLatchDemo {

	public static void main(String[] args) throws InterruptedException {

		CountDownLatch countDownLatch = new CountDownLatch(6);

		for (int i = 1; i <= 6; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "离开教室");
				countDownLatch.countDown();
			}, String.valueOf(i) + "-threadName:").start();
		}
		countDownLatch.await();
		System.out.println(Thread.currentThread().getName() + "离开教室");
	}

}
