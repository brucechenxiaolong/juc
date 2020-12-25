package com.java.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * juc: CyclicBarrier 辅助工具类的作用
 *当7个人都到齐后，才能开会。（收集到7颗龙珠，才能召唤神龙）
 */
public class CyclicBarrierDemo {

	public static void main(String[] args) {
		CyclicBarrier cb = new CyclicBarrier(7, () -> {System.out.println("*******召唤神龙*********");});

		for (int i = 1; i <= 7; i++) {
			final int xx = i;
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "\t收集到第" + xx + "颗龙珠了。");
				try {
					cb.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			},""+i).start();
		}

	}

}
