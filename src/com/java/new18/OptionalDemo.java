package com.java.new18;

import java.util.Optional;

/**
 * 使用Optional容器可以快速的定位NPE，并且在一定程度上可以减少对参数非空检验的代码量。
 *
 * Optional.of(T t); // 创建一个Optional实例
 * Optional.empty(); // 创建一个空的Optional实例
 * Optional.ofNullable(T t); // 若T不为null，创建一个Optional实例，否则创建一个空实例
 * isPresent();    // 判断是够包含值
 * orElse(T t);   //如果调用对象包含值，返回该值，否则返回T
 * orElseGet(Supplier s);  // 如果调用对象包含值，返回该值，否则返回s中获取的值
 * map(Function f): // 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty();
 * flatMap(Function mapper);// 与map类似。返回值是Optional
 * 总结：Optional.of(null)  会直接报NPE
 *
 */
public class OptionalDemo {
    public static void main(String[] args) {
//        Optional<Employee> op = Optional.of(new Employee("zhansan", 11));
//        System.out.println(op.get().getName());

//        Optional<Employee> op2 = Optional.of(null);
//        System.out.println(op2);

        Optional<Object> op = Optional.empty();
        System.out.println(op);

        // No value present
        System.out.println(op.get());

    }
}
