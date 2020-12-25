package com.java.jvm;

import jdk.nashorn.internal.runtime.linker.Bootstrap;

public class MyObject {
	public static void main(String[] args) {
		Object obj = new Object();//祖宗类，
//		System.out.println(obj.getClass().getClassLoader());//null
//		System.out.println(obj.getClass().getClassLoader().getParent());//异常：java.lang.NullPointerException

		//类加载器的四个级别：Bootstrap - Extension - App - User Defined
		MyObject object = new MyObject();
		System.out.println(object.getClass().getClassLoader().getParent().getParent());//ExtClassLoader
		System.out.println(object.getClass().getClassLoader().getParent());//AppClassLoader
		System.out.println(object.getClass().getClassLoader());
		System.out.println(object.getClass());

	}
}

