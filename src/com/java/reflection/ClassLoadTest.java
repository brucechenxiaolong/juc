package com.java.reflection;

/**
 * 类加载器执行顺序
 *
 */
public class ClassLoadTest {
    public static void main(String[] args) {

        //实例化对象
        //或读取静态代码块，会读取构造方法
        Aa a = new Aa("xxx");
        System.out.println(a.a5());

        //直接调用静态变量，会读取静态代码块
//        System.out.println(Aa.m);

    }
}

/**
 * 代码实例化调用方法：以下加载顺序是：
 * 1.静态代码块(不论怎么实例化都会调用此处代码)
 * 2.构造：只有实例化才会调用
 * 3.正常调用成员变量或方法
 */
class Aa{

    //无论什么操作都会先调用静态代码块
    static {
        System.out.println("----------------------------1---------------------------");
        System.out.println("Aa类静态代码块初始化...");
        m = 300;
    }

    public Aa() {
        System.out.println("----------------------------2---------------------------");
        System.out.println("Aa类的无参构造初始化...");
    }

    public Aa(String field1) {
        System.out.println("----------------------------3---------------------------");
        System.out.println("Aa类的有参构造初始化...；参数值是：" + field1);
    }

    static int m = 100;

    private void a1(){
        System.out.println("a1");
    }

    /**
     * 默认修饰符
     */
    void a2(){
        System.out.println("a2");
    }

    protected void a3(){
        System.out.println("a3");
    }

    public void a4(){
        System.out.println("a4");
    }

    public String a5(){
        return "a5";
    }
}


