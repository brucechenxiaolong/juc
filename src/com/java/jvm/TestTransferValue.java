package com.java.jvm;

public class TestTransferValue {
    public void f1(int age){
        age = 30;
    }
    public void f2(Person person){
        person.setPersonName("xxx");
    }
    public void f3(String str){
        str = "xxx";//****特殊字符串常量池
    }

    public static void main(String[] args) {
        TestTransferValue ttv = new TestTransferValue();
        int age = 20;
        ttv.f1(age);
        System.out.println("age=" + age);

        //引用传递的是地址，对象还是同一个对象：21行的 “Person p” 和 7行的“Person person” 地址指向的都是：new Person；从abc改成xxx；所以23行的结果是：xxx
        Person p = new Person("abc");
        ttv.f2(p);
        System.out.println("personName=" + p.getPersonName());


        //字符串常量池
        String str = "abc";
        ttv.f3(str);
        System.out.println("string=" + str);//输出结果还是：abc
    }
}

class Person{

    private String personName;

    public Person(String personName){
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
