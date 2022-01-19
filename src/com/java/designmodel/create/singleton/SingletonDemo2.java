package com.java.designmodel.create.singleton;

/**
 * 懒汉式：线程不安全
 * 效率太低，不推荐使用
 */
public class SingletonDemo2 {
    private static SingletonDemo2 instance = null;

    //1.构造器私有化，外部不能new
    private SingletonDemo2() {}

    //线程不安全（要想线程安全加：synchronized）
    public static synchronized SingletonDemo2 getInstance() {
//        if (instance == null) {
//            synchronized (instance) {
        if (instance == null) {
            instance = new SingletonDemo2();
        }
//            }
//        }
        return instance;
    }

    static {
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
        SingletonDemo2 xx = SingletonDemo2.getInstance();
        xx.opt1();
        xx.opt2();
        SingletonDemo2 xx2 = SingletonDemo2.getInstance();
        System.out.println(xx == xx2);//true
        System.out.println(xx.hashCode());
        System.out.println(xx2.hashCode());

    }
}
