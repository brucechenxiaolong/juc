package com.java.dynamic;

/**
 * 1.抽象类不能new；
 * 2.抽象方法没有方法体；
 * 3.抽象方法必须是public修饰
 * 4.抽象类中可以定义构造函数和普通方法所有的功能
 */
public abstract class AbstractDemo {
    public static final String XXX = "xxx111";//public-new的对象能调用
    private static final String BBB = "bbb111";//private-只能在本类中使用，其他类中new的对象不能使用

    protected static final String HHH = "hhh111";

    public String ccc = "";

    private String ddd = "ddd";

    public abstract void xxx();//抽象方法

    private void bbb(){
        System.out.println("method-bbb");
        System.out.println(this.BBB);
    }

    public void ccc(){
        System.out.println("method-ccc");
        System.out.println(this.ddd);
    }

    protected void ccc2(){

        System.out.println("method-ccc2");
    }

    private void ccc3(){

        System.out.println("method-ccc3");
    }

}
