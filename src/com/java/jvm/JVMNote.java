package com.java.jvm;

import java.util.Random;

/**
 * java 栈stack
 *
 * 	栈管运行，堆管存储
 * 	栈保存哪些数据：8种基本类型的变量+对象的引用变量+实例方法 都在栈内存中分配
 * 	栈溢出错误：java.lang.StackOverflowError
 */
public class JVMNote {

	/**
	 * java 栈测试
	 * 以下方法抛出异常：栈溢出错误（不是异常）-java.lang.StackOverflowError
	 *
	 */
	public static void m1() {
		m1();
	}

	//java.lang.OutOfMemoryError:java heap space
	//设置VM options
	public static void m2(){
		//大对象：40MB
		byte[] bytes = new byte[40 * 1024 * 1024];
	}

	//GC日志信息参数分析
	public static void m3(){
		String str = "不断往堆中添加字符串数据";
		while (true){
			str += str + new Random().nextInt(88888888) + new Random().nextInt(99999999);
		}
	}

	public static void main(String[] args) {
		System.out.println("begin");
		m3();
		System.out.println("end");
	}

}
