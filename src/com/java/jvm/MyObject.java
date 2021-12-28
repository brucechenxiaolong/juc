package com.java.jvm;

public class MyObject {
	public static void main(String[] args) {
		Object obj = new Object();//祖宗类，
		//jdk定义的Object类的类加载器是：Bootstrap
//		System.out.println(obj.getClass().getClassLoader());//null
//		System.out.println(obj.getClass().getClassLoader().getParent());//异常：java.lang.NullPointerException

		//类加载器的四个级别：Bootstrap - Extension - App - User Defined
		MyObject object = new MyObject();
		System.out.println(object.getClass().getClassLoader().getParent().getParent());//null
		System.out.println(object.getClass().getClassLoader().getParent());//sun.misc.Launcher$ExtClassLoader@1540e19d
		//自定义类的类加载器是：AppClassLoader
		System.out.println(object.getClass().getClassLoader());//sun.misc.Launcher$AppClassLoader@14dad5dc
		System.out.println(object.getClass());

	}
}

