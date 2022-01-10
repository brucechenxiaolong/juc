package com.java.new18;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * JDK1.8 新特性：
 * 1.Lambda表达式
 * 2.函数式接口
 * 3.方法引用和构造器调用
 */
public class LambdaDemo {
    public static void main(String[] args) {

        List<Product> list = new ArrayList<>();
        Product p1 = new Product("红色", 100);
        Product p2 = new Product("红色", 200);
        Product p3 = new Product("紫色", 300);
        list.add(p1);
        list.add(p2);
        list.add(p3);

        //1.普通过滤方法
        //筛选颜色为红色 并且 价格小于300千的
//        List<Product> list2 = filterProductByColor(list);
//        List<Product> list3 = filterProductByPrice(list2);
//        System.out.println(list3);

        //2.使用匿名内部类过滤筛选
//        list = filterProductByPredicate(list, new MyPredicate<Product>() {
//            @Override
//            public boolean test(Product product) {
//                return product.getPrice() < 300;
//            }
//        });
//        list = filterProductByPredicate(list, new MyPredicate<Product>() {
//            @Override
//            public boolean test(Product product) {
//                return "红色".equals(product.getColor());
//            }
//        });
//        System.out.println(list);

        //3.使用lambda表达式
//        list = filterProductByPredicate(list, p -> p.getPrice() < 300);
//        list = filterProductByPredicate(list, p -> "红色".equals(p.getColor()));
//        System.out.println(list);

        //4.使用stream
//        list.stream()
//                .filter(p -> p.getPrice() < 300)
//                .filter(p -> "红色".equals(p.getColor()))
//                .forEach(x -> {
//            System.out.println(x.getPrice() + "---" + x.getColor());
//        });


        /**
         *注意：
         *   1.lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致！
         *   2.若lambda参数列表中的第一个参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName::method
         */
        //此接口只有一个方法
        ConsumerInterface<Integer> con = (x) -> System.out.println(x);
        con.accept(100);

        // 方法引用-对象::实例方法
        ConsumerInterface<Integer> con2 = System.out::println;
        con2.accept(200);

        // 方法引用-类名::静态方法名
        //两个大小比较
        BiFunction<Integer, Integer, Integer> biFun = (x, y) -> Integer.compare(x, y);
        BiFunction<Integer, Integer, Integer> biFun2 = Integer::compare;
        Integer result = biFun.apply(300, 200);// 300 > 200 = 如果为true 打印 1
        System.out.println(result);

        // 方法引用-类名::实例方法名
        //判断两个字段值是否一样
        BiFunction<String, String, Boolean> fun1 = (str1, str2) -> str1.equals(str2);
        BiFunction<String, String, Boolean> fun2 = String::equals;
        Boolean result2 = fun2.apply("hello", "world");
        System.out.println(result2);

        System.out.println("-----------------------------------------------------");

        // 构造方法引用  类名::new
        Supplier<Employee> sup = () -> new Employee("zhangsan", 18);
        System.out.println(sup.get().getName());
//        Supplier<Employee> sup2 = Employee("lisi","20")::new;
//        System.out.println(sup2.get().getName());

        System.out.println("-----------------------------------------------------");

        // 数组引用
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] strArray = fun.apply(3);
        strArray = new String[]{"1", "2", "3"};
        Arrays.stream(strArray).forEach(System.out::println);

    }


    /**
     * 2.使用匿名内部类过滤筛选
     * @param list
     * @param mp
     * @return
     */
    public static List<Product> filterProductByPredicate(List<Product> list,MyPredicate<Product> mp){
        List<Product> prods = new ArrayList<>();
        for (Product prod : list){
            if (mp.test(prod)){
                prods.add(prod);
            }
        }
        return prods;
    }

    // 筛选颜色为红色
    public static List<Product> filterProductByColor(List<Product> list){
        List<Product> prods = new ArrayList<>();
        for (Product product : list){
            if ("红色".equals(product.getColor())){
                prods.add(product);
            }
        }
        return prods;
    }

    // 筛选价格小于200千的
    public static List<Product> filterProductByPrice(List<Product> list){
        List<Product> prods = new ArrayList<>();
        for (Product product : list){
            if (product.getPrice() < 300){
                prods.add(product);
            }
        }
        return prods;
    }


}

class Product{
    private String color;
    private int price;

    public Product(String color, int price){
        this.color = color;
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

class Employee{
    private String name;
    private int age;

    public Employee(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
