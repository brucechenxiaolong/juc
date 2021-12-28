package com.java.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * java反射-实现注解
 * 通过反射：实例对象；并操作对象
 */
public class Test01 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        //利用反射操作注解
        Class<?> c1 = Class.forName("com.java.annotation.Student2");//通过反射获取class对象
        Annotation[] annotations = c1.getAnnotations();//获取Student2 的注解
        for (Annotation annotation : annotations) {
            System.out.println(annotation);//@com.java.reflection.TableAnnotation(value=db_table)
        }

        System.out.println("-------------------------------------");

        //获取Student2 注解@TableAnnotation 的value值
        TableAnnotation tableAnnotation = c1.getAnnotation(TableAnnotation.class);
        System.out.println(tableAnnotation.value());

        System.out.println("-------------------------------------");

        //获取获取Student2 中属性的注解 的 value值
        Field f = c1.getDeclaredField("studentName");
        FieldAnnotation fieldAnnotation = f.getAnnotation(FieldAnnotation.class);
        System.out.println(fieldAnnotation.columnName());
        System.out.println(fieldAnnotation.type());
        System.out.println(fieldAnnotation.length());


    }
}

@TableAnnotation("db_table")
class Student2{
    @FieldAnnotation(columnName = "db_id", type = "int", length = 10)
    private int id;
    @FieldAnnotation(type = "int", length = 10, columnName = "db_age")
    private int age;
    @FieldAnnotation(length = 50, type = "int", columnName = "db_name")
    private String studentName;

    public Student2(int id, int age, String studentName) {
        this.id = id;
        this.age = age;
        this.studentName = studentName;
    }

    public Student2() {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Student2{id="+id+",age="+age+",studentName="+studentName+"}";
    }
}

/**
 * 为类或接口，自定义注解接口
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface TableAnnotation{
    String value();
}

/**
 * 为属性，自定义注解接口
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface FieldAnnotation{
    String columnName();
    String type();
    int length();
}
