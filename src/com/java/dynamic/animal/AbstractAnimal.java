package com.java.dynamic.animal;

public abstract class AbstractAnimal {
    String name;//名字
    String color;//颜色
    //构造方法
    public AbstractAnimal(String name,String color){
        this.name = name;
        this.color = color;
    }
    //非抽象方法
    public void eat(){
        System.out.println(name+"吃东西!!!");
    }
    //抽象方法:好处在于子类可以定义自己的方法
    public abstract void run();
}

class Dog2 extends AbstractAnimal{
    public Dog2(String name,String color){
        super(name,color);
    }

    @Override
    public void run() {
        System.out.println(name+"四条腿跑得快！！");
    }
}

class Fish2 extends AbstractAnimal{

    public Fish2(String name, String color) {
        super(name, color);
    }

    @Override
    public void run() {
        System.out.println(name+"摇摇尾巴游啊游！！");
    }
}

