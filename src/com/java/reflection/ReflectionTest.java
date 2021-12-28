package com.java.reflection;

/**
 * java反射
 * 通过反射获取Class
 */
public class ReflectionTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Person p = new Student();
        System.out.println("这个人是：" + p.getPersonName() + "；年龄是：" + p.getAge());

        //多种方式，获取Class
        //方式一：通过包名
        Class<?> c1 = Class.forName("com.java.reflection.Student");
        System.out.println(c1.hashCode());

        //方式二：通过对象获取
        Class<? extends Person> c2 = p.getClass();
        System.out.println(c2.hashCode());

        //方式三：通过类名.class获得
        Class c3 = Student.class;
        System.out.println(c3.hashCode());

        //获取父类
        Class<?> c4 = c2.getSuperclass();
        System.out.println(c4);

        //---------------------------------------------
        //只要元素类型与维度一样，就是同一个Class
        int[] a = new int[10];
        int[] b = new int[100];
        System.out.println(a.getClass().hashCode());
        System.out.println(b.getClass().hashCode());


    }
}

class Person{
    private String personName;
    private int age;
    public Person(){

    }

    public Person(String personName, int age){
        this.personName = personName;
        this.age = age;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name="+personName+"}";
    }
}

class Student extends Person{
    public Student(){
        this.setPersonName("学生");
        this.setAge(18);
    }
}
