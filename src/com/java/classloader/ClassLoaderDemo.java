package com.java.classloader;

/**
 * getClass()方法的作用
 * 获得了Person这个(类)Class，进而通过返回的Class对象获取Person的相关信息，
 * 比如：获取Person的构造方法，方法，属性有哪些等等信息。
 *
 */
public class ClassLoaderDemo {
    public static void main(String[] args) {
//        ClassLoader xx = ClassLoader.getSystemClassLoader();
//        ClassLoaderDemo xx = new ClassLoaderDemo();
        //输出的是用户自定义类加载器：sun.misc.Launcher$AppClassLoader@14dad5dc
//        System.out.println(xx.getClass().getClassLoader());

        Person person = new Person(1,"zs");
        System.out.println(person.getClass());
        System.out.println(person.getClass().getName());
    }
}

class Person{
    int id;
    String name;
    public Person(int id, String name){
        super();
        this.id = id;
        this.name = name;
    }
}
