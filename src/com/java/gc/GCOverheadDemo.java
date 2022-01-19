package com.java.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * GC超过最大限制数
 * java.lang.OutOfMemoryError:GC overhead limit exceeded
 * JVM参数配置演示：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 * GC回收时间过长时会抛出：java.lang.OutOfMemoryError: GC overhead limit exceeded
 * Full GC 无法回收堆内存（连续多次GC都只回收了不到2%的堆内存，这种极端情况下才会抛出OOM GC overhead limit exceeded）
 * CPU使用率一直是100%，而GC却没有任何成果。
 *
 */
public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        }catch (Throwable e){
            System.out.println("*******i=" + i);
            e.printStackTrace();
            throw e;
        }
    }
}
