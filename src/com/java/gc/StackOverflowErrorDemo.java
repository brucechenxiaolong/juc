package com.java.gc;

/**
 * 栈溢出错误
 * java.lang.StackOverflowError
 * 定义的方法太多，容易出现此问题。或者死循环调用方法也会出现此问题。
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        operation1();//死循环调用方法栈：java.lang.StackOverflowError
    }

    private static void operation1() {
        operation1();
    }
}
