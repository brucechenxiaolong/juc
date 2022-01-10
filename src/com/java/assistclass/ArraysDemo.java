package com.java.assistclass;

import java.util.Arrays;
import java.util.List;

/**
 * Arrays.asList(strArray)返回值是仍然是一个可变的集合，但是返回值是其内部类，不具有add方法，可以通过set方法进行增加值，默认长度是10
 * 通过set替换指定值
 *
 * Collections.singletonList()返回的是不可变的集合，但是这个长度的集合只有1，可以减少内存空间。但是返回的值依然是Collections的内部实现类，同样没有add的方法，调用add，set方法会报错
 */
public class ArraysDemo {
    public static void main(String[] args) {
        //转list
        List<String> xx = Arrays.asList(new String[]{"id", "approve_id", "form_id", "table_id", "entity_id","6","7","8","9","10"});
//        xx.add("xxx");
        xx.set(2, "xxx");//替换下标为2的值，替换form_id 为 xxx
        xx.forEach(bb -> {
            System.out.println(bb);
        });

        System.out.println("---------------------------------------------");

        String ids = "1111,2222";
        List<String> idList = Arrays.asList(ids.split(","));
        System.out.println(idList.size());
        System.out.println(idList);

        List<String> b = Arrays.asList(ids);
        System.out.println(b.size());
        System.out.println(b);

        List<Integer> cc = Arrays.asList(new Integer[]{1, 10});
        cc.forEach(c -> {
            System.out.println(c);
        });


    }
}
