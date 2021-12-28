package com.java.reflection;

/**
 * 类加载器执行顺序
 *
 */
public class ClassLoadTest {
    public static void main(String[] args) {

        //实例化对象
        //或读取静态代码块，会读取构造方法
//        Aa a = new Aa();
//        System.out.println(a.m);

        //直接调用静态变量：会读取构造方法
        System.out.println(Aa.m);

    }
}

class Aa{
    static {
        System.out.println("Aa类静态代码块初始化...");
        m = 300;
    }

    public Aa() {
        System.out.println("Aa类的无参构造初始化...");
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
}


