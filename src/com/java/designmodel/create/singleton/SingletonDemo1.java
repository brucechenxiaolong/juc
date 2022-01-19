package com.java.designmodel.create.singleton;

/**
 * 1.单例模式
 * 定义：确保一个类最多只有一个实例，并提供一个全局访问点
 * 单例模式可以分为两种：预加载和懒加载
 *
 * 如何保证：懒加载的线程安全。
 *1.保证懒加载的线程安全：
 * 我们首先想到的就是使用synchronized关键字。synchronized加载getInstace()函数上确实保证了线程的安全。但是，如果要经常的调用getInstance()方法，不管有没有初始化实例，都会唤醒和阻塞线程。为了避免线程的上下文切换消耗大量时间，如果对象已经实例化了，我们没有必要再使用synchronized加锁，直接返回对象。
 *
 *2.new一个对象的代码是无法保证顺序性的，因此，我们需要使用另一个关键字volatile保证对象实例化过程的顺序性
 *
 *
 * 饿汉式（静态变量）
 * 效率太低，不推荐使用
 */
public class SingletonDemo1 {

    private static SingletonDemo1 instance = new SingletonDemo1();

    //1.构造器私有化，外部不能new
    private SingletonDemo1() {}

    //我们把sychronized加在if(instance==null)判断语句里面，保证instance未实例化的时候才加锁
    public static SingletonDemo1 getInstance() {
        return instance;
    }

    static {
//        instance = new SingletonDemo1();
        System.out.println("1.static...");
    }

    private void opt1(){
        System.out.println("opt1...");
    }

    public void opt2(){
        System.out.println("opt2...");
    }

    /**
     * 对象实例只有一个
     * @param args
     */
    public static void main(String[] args) {
        SingletonDemo1 xx = SingletonDemo1.getInstance();
        xx.opt1();
        xx.opt2();
        SingletonDemo1 xx2 = SingletonDemo1.getInstance();
        System.out.println(xx == xx2);//true
        System.out.println(xx.hashCode());
        System.out.println(xx2.hashCode());

    }


}
