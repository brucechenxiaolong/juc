package com.java.new18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Stream操作
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        Employee e1 = new Employee("a1", 15);
        Employee e2 = new Employee("a2", 11);
        Employee e3 = new Employee("a3", 16);
        Employee e4 = new Employee("a4", 13);
        Employee e5 = new Employee("a5", 14);
        Employee e6 = new Employee("a6", 10);
        Employee e7 = new Employee("a7", 12);
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);
        list.add(e6);
        list.add(e7);

//        list.stream()
//                .filter(e -> e.getAge() > 10)
//                .limit(4)
//                .skip(2)
//                // 需要流中的元素重写hashCode和equals方法
//                .distinct()
//                .forEach(x -> {
//                    System.out.println(x.getName() + "-" + x.getAge());
//                });

        //值获取一个字段的值：age
//        list.stream()
//                .map((e) -> e.getAge())
//                .forEach(System.out::println);

        //排序
//        list.stream()
//                .sorted((x1 ,x2) -> {
//                    if (x1.getAge() == x2.getAge()){
//                        return x1.getName().compareTo(x2.getName());
//                    } else{
//                        return (x1.getAge() + "").compareTo(x2.getAge() + "");
//                    }
//                })
//                .forEach(x -> {
//                    System.out.println(x.getAge());
//                });


        //使用 reduce 来汇总list中的值
        List<Integer> list2 = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer count2 = list2.stream().reduce(0, (x, y) -> x + y);
        System.out.println(count2);

        Optional<Integer> sum = list.stream().map(Employee::getAge).reduce(Integer::sum);
        System.out.println(sum.get());

        System.out.println("-----------------------------");

        //收集成新的list
        List<Integer> ageList = list.stream().filter(e -> e.getAge() > 14).map(Employee::getAge).collect(Collectors.toList());
        ageList.stream().forEach(System.out::println);


    }
}
