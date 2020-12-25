package com.java.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * SemaphoreDemo juc辅助工具类
 * 实现：6个人开车，抢3个车位，每部车都有一次进入停车场的机会
 *
 */
public class SemaphoreDemo {
	public static void main(String[] args) {
		Semaphore sp = new Semaphore(3);//3个空车位

		for (int i = 1; i <= 6; i++) {
			new Thread(() -> {
				try {
					sp.acquire();//获取车位
					System.out.println(Thread.currentThread().getName() + "\t抢占到了车位");
					TimeUnit.SECONDS.sleep(5);
					System.out.println(Thread.currentThread().getName() + "\t离开了车位");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					sp.release();//释放车位
				}

			},String.valueOf(i) + "-threadName:").start();
		}

	}
}
