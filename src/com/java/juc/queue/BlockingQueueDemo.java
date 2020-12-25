package com.java.juc.queue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列实例
 */
public class BlockingQueueDemo {

	public static void main(String[] args) throws InterruptedException {

		//阻塞队列：
//		BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

		//**************方式一：抛出异常*****************
		//添加元素：抛出异常方法
//		queue.add("A");
//		queue.add("B");
//		queue.add("C");
//		queue.add("X");
//		//删除元素：抛出异常方法
//		System.out.println(queue.remove());
//		System.out.println(queue.remove());
//		System.out.println(queue.remove());
//		System.out.println(queue.remove());
//		System.out.println(queue.element());
		//**************方式一：抛出异常*****************

		//**************方式二：返回 boolean true or false*****************
		//添加元素:返回 true or false
//		System.out.println(queue.offer("A"));
//		System.out.println(queue.offer("B"));
//		System.out.println(queue.offer("C"));
//		System.out.println(queue.offer("D"));
//		//删除元素:返回 true or false
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());

		//检查
//		System.out.println(queue.peek());
//		System.out.println(queue.peek());
		//**************方式二：boolean true or false*****************

		//**************方式三：阻塞*****************
//		System.out.println(queue.offer("A"));
//		System.out.println(queue.offer("B"));
//		System.out.println(queue.offer("C"));
//		System.out.println("插不进去了：" + queue.offer("D",3L,TimeUnit.SECONDS));//3秒插不进去就离开
//
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());
//		System.out.println("获取不到了：" + queue.poll(3L,TimeUnit.SECONDS));//3秒取不到就离开
		//**************方式三：阻塞*****************

		Queue queue = new ConcurrentLinkedQueue();
		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
		System.out.println("offer后，队列是否空？" + queue.isEmpty());
		System.out.println("从队列中poll：" + queue.poll());//移除
		System.out.println("从队列中remove：" + queue.remove());//移除  有异常
		System.out.println("pool后，队列是否空？" + queue.isEmpty());
		System.out.println("队列长度是？" + queue.size());
	}
}