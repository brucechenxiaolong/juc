package com.java.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS=compareAndSet  比较并赋值
 * 第一个参数=期望值
 * 第二个参数=目标值
 */
public class CASDemo {

    private static AtomicInteger atomicInteger = new AtomicInteger(5);

    public static void main(String[] args) {
        System.out.println(atomicInteger.compareAndSet(5, 20));//true
        System.out.println(atomicInteger.get());//20

        System.out.println(atomicInteger.compareAndSet(5, 30));//false
        System.out.println(atomicInteger.get());//20
    }
}
