package com.java.designmodel.create.singleton;

/**
 * 双重检查：解决效率问题，也解决了线程安全问题
 * 推荐使用
 */
public class SingletonDemo3 {
    //1.volatile 修饰
    private static volatile SingletonDemo3 instance;

    //1.构造器私有化，外部不能new
    private SingletonDemo3() {}

    //我们把sychronized加在if(instance==null)判断语句里面，保证instance未实例化的时候才加锁
    //线程不安全（要想线程安全加：synchronized）
    public static synchronized SingletonDemo3 getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo3.class) {
                if (instance == null) {
                    instance = new SingletonDemo3();
                }
            }
        }
        return instance;
    }

    static {
        System.out.println("1.static...");
    }

    //静态内部类实现单例
    private static class XxxYyy {
        private static final SingletonDemo3 INSTANCE = new SingletonDemo3();
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
        SingletonDemo3 xx = SingletonDemo3.getInstance();
        xx.opt1();
        xx.opt2();
        SingletonDemo3 xx2 = SingletonDemo3.getInstance();
        System.out.println(xx == xx2);//true
        System.out.println(xx.hashCode());
        System.out.println(xx2.hashCode());

    }
}
