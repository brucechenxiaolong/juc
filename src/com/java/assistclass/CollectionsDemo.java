package com.java.assistclass;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Arrays.asList(strArray)返回值是仍然是一个可变的集合，但是返回值是其内部类，不具有add方法，可以通过set方法进行增加值，默认长度是10
 * Collections.singletonList()返回的是不可变的集合，但是这个长度的集合只有1，可以减少内存空间。但是返回的值依然是Collections的内部实现类，同样没有add的方法，调用add，set方法会报错
 */
public class CollectionsDemo {
    public static void main(String[] args) {
        //1.singletonList-的用法
        //返回的是不可变的集合，不允许add数据，否则异常，不允许任何操作
//        StringBuilder sb = new StringBuilder();
//        sb.append(" ALTER TABLE ").append("T_100_ARCHIVES");
//        sb.append(" ADD (");
//        sb.append("XXX");
//        sb.append(")");
//        List<String> xx = Collections.singletonList(sb.toString());//集合的长度只有1，可以减少内存空间
//        for (String s : xx) {
//            System.out.println(s);
//        }
//        xx.add("bb");//抛异常：java.lang.UnsupportedOperationException
//        xx.remove(0);//抛异常：java.lang.UnsupportedOperationException


        //2.多线程操作list，保证list是线程安全的。
//        List<String> aList = new ArrayList<>();
//        List<String> checkCodeList = Collections.synchronizedList(new ArrayList<>());
//        aList.parallelStream().forEach(po -> {
//            checkCodeList.add(po);
//        });

        //3.获取list中的最大值和最小值
//        List<Integer> fmtList = new ArrayList<Integer>();
//        for (int i = 1; i <= 10; i++) {
//            fmtList.add(i);
//        }
//        String bb = Collections.min(fmtList) + "-" + Collections.max(fmtList);
//        System.out.println(bb);

        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        //通过使用.collect(Collectors.toList()); 可以任意操作list
        List<String> b = a.stream().collect(Collectors.toList());
        b.add("4");
        b.forEach(bb -> {
            System.out.println(bb);
        });

    }
}
