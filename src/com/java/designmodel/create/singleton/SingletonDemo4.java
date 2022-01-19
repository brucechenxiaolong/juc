package com.java.designmodel.create.singleton;

public class SingletonDemo4 {
    public static void main(String[] args) {
        SingletonEnum xx = SingletonEnum.INSTANCE;
        xx.sayOK();
        SingletonEnum xx2 = SingletonEnum.INSTANCE;
        xx2.sayOK();
        System.out.println(xx == xx2);
    }
}

/**
 * 单例模式
 * 枚举也能单例
 */
enum SingletonEnum{
    INSTANCE;
    public void sayOK(){
        System.out.println("ok...");
    }
}
