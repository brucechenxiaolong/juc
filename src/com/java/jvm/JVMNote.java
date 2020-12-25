package com.java.jvm;

/**
 * java 栈stack
 *
 * 	栈管运行，堆管存储
 * 	栈保存哪些数据：8种基本类型的变量+对象的引用变量+实例方法 都在栈内存中分配
 * 	栈溢出异常：java.lang.StackOverflowError
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

	public static void main(String[] args) {
		System.out.println("begin");
		m1();
		System.out.println("end");

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
