package com.java.dynamic.animal;

/**
 *
 */
public class Animal {
    public String name;//名字
    public String color;//颜色

    public Animal(String name,String color){
        this.name = name;
        this.color = color;
    }
    public void run(){
        System.out.println(name+"-四条腿跑的很快！！！");
    }

    public void run2(){
        System.out.println(name + "-run2 ！！！");
    }

    private static void run3(){
        System.out.println("run3 ！！！");
    }

}

//狗类继承动物类
class Dog1 extends Animal {
    public Dog1(String name,String color){
        super(name,color);
    }

    @Override
    public void run2(){
        System.out.println(name + "-run2 Dog1 ！！！");
    }
}
//鱼类继承动物类
class Fish1 extends Animal {

    public Fish1(String name, String color) {
        super(name, color);
    }
}
