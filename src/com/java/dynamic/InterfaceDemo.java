package com.java.dynamic;

/**
 * 1.接口中的修饰符只能用public
 * 2.接口中可以定义常量
 * 3.1.8后，可以定义default修饰的默认方法；
 * 4.1.8后，可以定义静态方法
 */
public interface InterfaceDemo {

    //接口中：定义常量
    public static final String INTERFACE_XXX ="xxx";

    public void eee();

    public void fff();

    //接口中可以定义多个默认方法
    public default int mul1(int x, int y) {
        return x*y;
    }

    public default int mul2(int x, int y) {
        return x*y;
    }

    //接口中：可以定义静态方法：静态方法直接调用：InterfaceDemo.div1(1,2);
    public static int div1(int x, int y) {
        return x/y;
    }

    public static int div2(int x, int y) {
        return x/y;
    }

}
