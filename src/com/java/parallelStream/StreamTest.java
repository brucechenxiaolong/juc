package com.java.parallelStream;

import java.util.ArrayList;
import java.util.List;

/**
 * 循环代码性能测试
 * for-forEach-stream.forEach
 */
public class StreamTest {

    /**
     * 测试用时单位为毫秒
     * @param args
     */
    public static void main(String[] args) {
        // 测试源
        List<String> sourceList = new ArrayList<>();
        for (int i = 0;i<1000000;i++) {//百万级，可以用平行流遍历数据，如果数据量很小，不建议使用。
            sourceList.add("第" + i + "条数据");
        }
        System.out.println("数据条数：" + sourceList.size());

        long a1=System.currentTimeMillis();
        for (int i = 0;i < sourceList.size();i++) doSome();
        long a2=System.currentTimeMillis();
        System.out.println("普通for循环用时：" + (a2-a1));

        long b1=System.currentTimeMillis();
        for (String t:sourceList) doSome();
        long b2=System.currentTimeMillis();
        System.out.println("增强for循环用时：" + (b2-b1));

        long c1=System.currentTimeMillis();
        sourceList.forEach((t)-> doSome());
        long c2=System.currentTimeMillis();
        System.out.println("forEach循环用时：" + (c2-c1));

        long d1=System.currentTimeMillis();
        sourceList.stream().forEach((t)-> doSome());
        long d2=System.currentTimeMillis();
        System.out.println("Stream-forEach循环用时：" + (d2-d1));

        long e1=System.currentTimeMillis();
        sourceList.parallelStream().forEach((t)-> doSome());
        long e2=System.currentTimeMillis();
        System.out.println("parallelStream-forEach循环用时：" + (e2-e1));
    }

    /**
     * 线程休眠？？毫秒
     */
    private static void doSome() {
        try {
            Thread.sleep(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
