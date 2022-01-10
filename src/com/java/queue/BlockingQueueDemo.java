package com.java.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 阻塞队列
 * 应用场景：1.生产者消费者模式；2.线程池；3.消息中间件；
 */
public class BlockingQueueDemo {

	public static void main(String[] args) throws InterruptedException {
		//1.阻塞队列基本用法
		method01();

		//2.阻塞队列：操作资源类
//		method02();

		//3.SynchronousQueue没有容量，不存储元素的BlockingQueue
		//每一个put操作必须要等待一个take操作，否则不能继续添加元素，反之亦然。
//		method03();

	}

	/**
	 * 3.SynchronousQueue没有容量，不存储元素的BlockingQueue
	 * 每一个put操作必须要等待一个take操作，否则不能继续添加元素，反之亦然。
	 */
	private static void method03() {

		//3.SynchronousQueue没有容量，不存储元素的BlockingQueue
		//每一个put操作必须要等待一个take操作，否则不能继续添加元素，反之亦然。
		BlockingQueue<String> queue = new SynchronousQueue<>();

		//下面定义两个线程：一个线程来put，一个线程来take
		//1.put
		new Thread(() -> {
			try {
				System.out.println(Thread.currentThread().getName() + "\t put 1");
				queue.put("1");

				System.out.println(Thread.currentThread().getName() + "\t put 2");
				queue.put("2");

				System.out.println(Thread.currentThread().getName() + "\t put 3");
				queue.put("3");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}).start();

		//2.get
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
				System.out.println(Thread.currentThread().getName() + "\t 获取了：" + queue.take());

				TimeUnit.SECONDS.sleep(5);
				System.out.println(Thread.currentThread().getName() + "\t 获取了：" + queue.take());

				TimeUnit.SECONDS.sleep(5);
				System.out.println(Thread.currentThread().getName() + "\t 获取了：" + queue.take());

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}).start();
	}

	/**
	 * 队列使用代码实例2
	 * 代码标准用法
	 * @throws InterruptedException
	 */
	private static void method02() throws InterruptedException {
		//阻塞队列：
		BlockingQueue<MyData> queue = new ArrayBlockingQueue<>(3);//必须设定队列可以添加几个值
		MyData data1 = new MyData("1", "zhangsan", new Date());
		queue.offer(data1);
		MyData data2 = new MyData("2", "lisi", new Date());
		queue.offer(data2);
		MyData data3 = new MyData("3", "wangwu", new Date());
		queue.offer(data3);
		MyData data4 = new MyData("4", "wangwu2", new Date());
		queue.offer(data4);

		new Thread(() -> {
			while (true) {
				System.out.println("*****" + queue.peek() + "*****");//检查队列是否有值：如果没有值，则返回null
				if (!queue.isEmpty()) {
					MyData model = queue.poll();//消费队列
					System.out.println(model.getDataName() + "  " + model.getBirthday());
				}else{
					try {
						Thread.sleep(5000);
						System.out.println("queue is empty");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	/**
	 * 1.阻塞队列基本用法
	 */
	private static void method01() throws InterruptedException {
		//阻塞队列：
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);//必须设定队列可以添加几个值

		//**************方式一：抛出异常*****************
		//添加元素：抛出异常方法
//		queue.add("A");
//		queue.add("B");
//		queue.add("C");
		//queue.add("X");//注意：此行代码异常，IllegalStateException: Queue full
		//消费元素：抛出异常方法
//		System.out.println(queue.remove());
//		System.out.println(queue.remove());
//		System.out.println(queue.remove());
		//System.out.println(queue.remove());//异常
//		System.out.println(queue.element());
		//**************方式一：抛出异常*****************


		//**************方式二：特殊值；返回 boolean true or false*****************
		//添加元素:返回 true or false
//		System.out.println(queue.offer("A"));
//		System.out.println(queue.offer("B"));
//		System.out.println(queue.offer("C"));
//		System.out.println(queue.offer("D"));//不报错，展示返回boolean类型
// 		System.out.println("插不进去了：" + queue.offer("D",3L,TimeUnit.SECONDS));//3秒插不进去就离开
//		//消费元素:返回 true or false
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());//没有元素删除：返回null
//		System.out.println("获取不到了：" + queue.poll(3L,TimeUnit.SECONDS));//3秒取不到就离开
		//检查
//		System.out.println(queue.peek());//检查queue中是否有数据，如果没有返回null
//		System.out.println(queue.peek());
		//**************方式二：boolean true or false*****************


		//**********************方式三：阻塞*****************
		//添加元素
		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					queue.put("A" + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		//消费元素
		new Thread(() -> {
			while (true) {
				try {
					if(queue.isEmpty()){
						System.out.println("queue is empty...");
						return;
					}else {
						System.out.println(queue.take());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"A").start();


		//**************方式三：阻塞*****************


		//--------------------------------------------ConcurrentLinkedQueue 用法展示------------------------------------------------
//		Queue queue = new ConcurrentLinkedQueue();
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		queue.offer("哈哈哈");
//		System.out.println("offer后，队列是否空？" + queue.isEmpty());
//		System.out.println("从队列中poll：" + queue.poll());//移除
//		System.out.println("从队列中remove：" + queue.remove());//移除  有异常
//		System.out.println("pool后，队列是否空？" + queue.isEmpty());
//		System.out.println("队列长度是？" + queue.size());

	}
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
class MyData{
	private String id;
	private String dataName;
	private Date birthday;
}