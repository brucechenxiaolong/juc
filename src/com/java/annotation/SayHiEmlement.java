package com.java.annotation;

public class SayHiEmlement {

    private String name = "xxxx";

    // 普通的方法
    public void sayHiDefault(String name) {
        System.out.println("1.Hi, " + name);// 1.Hi, zhangsan
    }

    //有参的注解
    @DefineAnnotation(paramValue = "jack")
    public void sayHiAnnotation(String name){
        System.out.println("2.Hi, " + name);// 2.Hi, jack
    }

    //默认值注解
    @DefineAnnotation
    public void sayHiAnnotationDefault(String name){
        System.out.println("3.Hi, " + name);// 3.Hi, bruce
    }
}
