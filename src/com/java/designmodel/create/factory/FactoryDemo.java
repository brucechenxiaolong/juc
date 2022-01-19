package com.java.designmodel.create.factory;

import java.util.Calendar;

/**
 * 1、创建型模式，共五种：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式。
 * 抽象工程模式
 */
public class FactoryDemo {
    public static void main(String[] args) {

//        AbsFactory mOrderPizza = new LDFactory("London");
//        Pizza xx = mOrderPizza.createPizza("cheese");
//        xx.box();
//
//        mOrderPizza = new BJFactory("beijing");
//        xx = mOrderPizza.createPizza("chocolate");
//        xx.box();

        //jdk中用到的工程模式类：
        //静态方法：getInstance()
        Calendar c = Calendar.getInstance();
        System.out.println("年：" + c.get(Calendar.YEAR));

    }
}

interface AbsFactory{
    Pizza createPizza(String ordertype);
}

class LDFactory implements AbsFactory{

    private String name;

    public LDFactory(String name){
        this.name = name;
    }

    @Override
    public Pizza createPizza(String ordertype) {
        Pizza pizza = null;
        if ("cheese".equals(ordertype)) {
            pizza = new LDCheesePizza();
        } else if ("chocolate".equals(ordertype)) {
            pizza = new LDChocolatePizza();
        }
        return pizza;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class BJFactory implements AbsFactory{

    private String name;

    public BJFactory(String name){
        this.name = name;
    }

    @Override
    public Pizza createPizza(String ordertype) {
        Pizza pizza = null;
        if ("cheese".equals(ordertype)) {
            pizza = new BJCheesePizza();
        } else if ("chocolate".equals(ordertype)) {
            pizza = new BJChocolatePizza();
        }
        return pizza;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Pizza{
    private String name;
    public void box(){
        System.out.println(name + "-box");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

//------------------------伦敦披萨店------------------------
//芝士披萨
class LDCheesePizza extends Pizza{
    @Override
    public void box(){
        System.out.println("LDCheesePizza");
    }
}
//巧克力披萨
class LDChocolatePizza extends Pizza{
    @Override
    public void box(){
        System.out.println("LDChocolatePizza");
    }
}

//---------------------北京披萨店--------------------------
//芝士披萨
class BJCheesePizza extends Pizza{
    @Override
    public void box(){
        System.out.println("BJCheesePizza");
    }
}
//巧克力披萨
class BJChocolatePizza extends Pizza{
    @Override
    public void box(){
        System.out.println("BJChocolatePizza");
    }
}


