package com.java.jvm;

/**
 *堆内存溢出
 */
public class SetHeapMemory {
	public static void main(String[] args) {

		//大对象：40MB的byte字节
		byte[] bytes = new byte[40 * 1024 * 1024];//字节 < KB < MB < GB

	}
}
