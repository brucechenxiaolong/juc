package com.java.juc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;


/**
 *1 故障现象
 *java.util.ConcurrentModificationException(并发修改异常)
 *
 *
 *2.导致原因
 *ArrayList-线程不安全 没有加锁
 *
 *
 *3.解决方法
 *List list = new Vector<>();//xx版本适用于1.2 --不建议使用
 *List list = Collections.synchronizedList(new ArrayList<>());//Collections-接口工具类 --不建议使用
 *
 *4.优化建议
 *使用：juc的CopyOnWriteArrayList
 *
 *
 */
public class NotSafeDemo {

	//多线程往list中添加Object值时，线程不安全解决方案
	public static void main(String[] args) {

		//list线程不安全解决方法
		//listNotSafeMethod();

		//set线程不安全解决方法
		//setNotSafeMethod();

		//map线程不安全解决方法
		mapNotSafeMethod();

	}



	/**
	 * 1.list线程不安全
	 */
	private static void listNotSafeMethod() {
		//List list = new ArrayList<>();

		//替换方式一：
		//List list = new Vector<>();//xx版本适用于1.2
		//替换方式二：
		//List list = Collections.synchronizedList(new ArrayList<>());//Collections-接口工具类
		List list = new CopyOnWriteArrayList();//解决ConcurrentModificationException异常的方案是：写时复制的list，java8 juc 新特性

		//以下代码如果用：list = new ArrayList<>();  进行添加输出的结果是：抛异常：ConcurrentModificationException
		for (int i = 1; i <= 30; i++) {
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0,8));
				System.out.println(list);
			}, "" + i).start();
		}
	}

	/**
	 * 2.set线程不安全
	 */
	private static void setNotSafeMethod() {

		Set<String> set = new CopyOnWriteArraySet<String>();//CopyOnWriteArraySet 底层是：CopyOnWriteArrayList
		for (int i = 1; i <= 5; i++) {
			new Thread(() -> {
				//HashSet-底层数据结构是什么？map = new HashMap<>();
				set.add(UUID.randomUUID().toString().substring(0,8));
				System.out.println(set);
			}, "" + i).start();
		}
	}

	/**
	 * 3.map线程不安全
	 */
	private static void mapNotSafeMethod() {

		Map<String, String> map = new ConcurrentHashMap<String, String>();//ConcurrentHashMap 底层是：Node
		for (int i = 1; i <= 5; i++) {
			new Thread(() -> {
				//HashSet-底层数据结构是什么？map = new HashMap<>();
				map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
				System.out.println(map);
			}, "" + i).start();
		}
	}


}

//CopyOnWriteArrayList 代码详解：
//public boolean add(E e) {
//    final ReentrantLock lock = this.lock;
//    lock.lock();
//    try {
//        Object[] elements = getArray();
//        int len = elements.length;
//        Object[] newElements = Arrays.copyOf(elements, len + 1);
//        newElements[len] = e;
//        setArray(newElements);
//        return true;
//    } finally {
//        lock.unlock();
//    }
//}
