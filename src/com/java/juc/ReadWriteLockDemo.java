package com.java.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock juc-接口
 * 作用：
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是：如果有一个线程想去写共享资源类，那么就不应该再有其他线程可以对该资源进行读或写
 * 小总结：
 * 读能共享
 * 读-写不能共存
 * 写-写不能共存
 */
public class ReadWriteLockDemo {

	public static void main(String[] args) {

		//普通方法调用
		//Xxxxx myCache = new MyCache();

		//ReadWriteLock-处理后的方法调用
		Xxxxx myCache = new MyCacheReadWriteLock();


		for (int i = 1; i <= 5; i++) {
			final int tempInt = i;
			new Thread(() -> {
				myCache.putData("" + tempInt, "" + tempInt);
			}, String.valueOf(i)).start();
		}

		for (int i = 1; i <= 5; i++) {
			final int tempInt = i;
			new Thread(() -> {
				myCache.getData("" + tempInt);
			}, String.valueOf(i)).start();
		}
	}
}

/**
 * 实现：王map中写入数据，然后再读取数据
 * 要求：全部写完之后，才能读取数据（相当于：oracle事务中的ACID）
 *
 */

interface Xxxxx{
	public void putData(String key, Object value);
	public void getData(String key);
}
/**
 * 普通代码
 * @author bruce
 *
 */
class MyCache implements Xxxxx{
	private volatile Map<String, Object> map = new HashMap<>();

	public void putData(String key, Object value) {

		System.out.println(Thread.currentThread().getName() + "\t写入数据：" + key);
		try {
			TimeUnit.MILLISECONDS.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		map.put(key, value);
		System.out.println(Thread.currentThread().getName() + "\t写入成功。");

	}

	public void getData(String key) {
		try {
			TimeUnit.MILLISECONDS.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Object obj = map.get(key);
		System.out.println(Thread.currentThread().getName() + "\t读取成功:" + obj);
	}

}

/**
 * ReadWriteLock-代码
 * @author bruce
 *
 */
class MyCacheReadWriteLock implements Xxxxx{

	private volatile Map<String, Object> map = new HashMap<>();

	//关键代码：
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public void putData(String key, Object value) {

		readWriteLock.writeLock().lock();
		try {

			System.out.println(Thread.currentThread().getName() + "\t写入数据：" + key);
			try {
				TimeUnit.MILLISECONDS.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.put(key, value);
			System.out.println(Thread.currentThread().getName() + "\t写入成功。");

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			readWriteLock.writeLock().unlock();
		}


	}

	public void getData(String key) {
		readWriteLock.readLock().lock();
		try {
			try {
				TimeUnit.MILLISECONDS.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Object obj = map.get(key);
			System.out.println(Thread.currentThread().getName() + "\t读取成功:" + obj);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			readWriteLock.readLock().unlock();
		}

	}
}