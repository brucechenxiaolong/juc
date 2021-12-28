package com.java.animal;

public class Dog {

    /**
     * 不允许访问，私有
     */
    private void a1(){
        System.out.println("a1");
    }

    /**
     * 同一个包内可以访问
     */
    void a2(){
        System.out.println("a2");
    }

    /**
     * 同一个包内可以访问
     */
    protected void a3(){
        System.out.println("a3");
    }

    /**
     * 跨包可以访问
     */
    public void a4(){
        System.out.println("a4");
    }
}
