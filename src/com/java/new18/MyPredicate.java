package com.java.new18;

/**
 * 匿名内部类 一般是作用在接口上，接口只有一个方法
 * @param <T> 可以传递任何参数
 */
public interface MyPredicate<T> {
    boolean test(T t);
}
