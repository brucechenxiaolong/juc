package com.java.jmm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * volatile:
 * 1.保证可见性
 * 2.不保证原子性
 *
 * java.util.concurrent.atomic
 *多线程操作资源类
 * 知识点：volatile
 *1.验证volatile的可见性
 * 1.1：变量定义中如果加入volatile修饰符，可以解决可见性问题。
 *
 * 2.验证volatile不保证原子性
 * 2.1：原子性，完整性
 *
 */
public class VolatileDemo {

    public static void main(String[] args) {
        //1.可见性代码验证
        visibleValidate();

        //2.volatile不保证原子性
//        notAtomicVolatile();

        //3.原子性：AtomicInteger
//        atomicIntegerMethod();

    }

    /**
     * 3.原子性：AtomicInteger
     */
    private static void atomicIntegerMethod() {
        //公共资源类
        MyData myData = new MyData();
        for (int i = 1; i <= 10; i++) {
            new Thread(() ->{
                for (int j = 1; j <= 1000; j++) {
                    myData.addAtomic();
                }
            },"ssss").start();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("myData.atomicInteger is " + myData.atomicInteger.toString());
    }

    /**
     * 2.volatile不保证原子性
     */
    private static void notAtomicVolatile() {
        //公共资源类
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() ->{
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlus();
                }
            },"ssss").start();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "mydata.number is " + myData.number);//最终输出的结果小于：20000
    }

    /**
     * 1.可见性代码验证************************volatile-案例的核心方法****************************
     */
    private static void visibleValidate() {
        //公共资源类
        MyData myData = new MyData();

        //多线程，操作资源类
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.addto60();
                System.out.println(Thread.currentThread().getName() + "mydata.number is " + myData.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        //main线程,只要number=0就一直循环调用,线程等待...
        while (myData.number == 0){
            //main线程一直在等待，直到number值被修改，跳出当前循环
//            System.out.println(Thread.currentThread().getName() + " myData.number is running...");
        }

        System.out.println(Thread.currentThread().getName() + "main线程执行结束！mydata.number is " + myData.number);
    }
}

/**
 * 资源类
 * volatile 关键词，修饰变量，使用了jmm内存模型的：可见性 特征。保证其他线程也能及时收到被修改变量的通知。
 */
class MyData{
//    int number = 0;//线程私有
    volatile int number = 0;//多线程之间可见性
    public void addto60(){
        this.number = 60;
    }

    /**
     * 方法中加入：synchronized 关键词修饰，可以保证原子性
     * public synchronized void addPlus()
     */
    public void addPlus(){
        number++;//多线程操作number变量，不能保证计算结果完整正确。会出现丢数据或少数据
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
        atomicInteger.incrementAndGet();
    }

}

/**
 * 指令重排
 * 了解内容
 */
class ReSortSequence{
    int a = 0;
    boolean flag = false;

    public void method01() {
        a = 1;
        flag = true;
    }
    public void method02(){
        if(flag){
            a = a + 5;
            System.out.println("a is " + a);
        }
    }
}
