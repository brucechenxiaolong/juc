package com.java.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过反射来：读取自定义注解
 */
public class AnnotationTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        SayHiEmlement say = new SayHiEmlement();// 初始化一个实例，用于方法调用
        //反射获取Class
        Class c1 = SayHiEmlement.class;
        Field name = c1.getDeclaredField("name");
        System.out.println(name.toString());
        Method[] methods = c1.getDeclaredMethods();// 获得所有方法

        for (Method method:methods){
            System.out.println(method.getName());//打印方法名称
            DefineAnnotation annotation = method.getAnnotation(DefineAnnotation.class);
            if(annotation != null){//检测是否使用了我们的注解
                //invoke-激活方法
                method.invoke(say, annotation.paramValue());//如果使用了我们的注解，我们就把注解里的"paramValue"参数值作为方法参数来调用方法
            }else{
                method.invoke(say, "zhangsan");// 如果没有使用我们的注解，我们就需要使用普通的方式来调用方法了
            }
            System.out.println("---------------------");

        }
    }
}
