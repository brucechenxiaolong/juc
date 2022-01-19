package com.java.javabase;

import java.util.HashMap;
import java.util.Map;

public class BaseCodeDemo {
    public static final Map<String,Long> workType = getWorkType();

    private static Map<String, Long> getWorkType() {
        Map<String, Long> map = new HashMap<>();
        map.put("数据接收",1L);map.put("数据检测",2L);map.put("著录编目",3L);
        map.put("数据入库",4L);map.put("实体检测",5L);map.put("实体消杀",6L);
        map.put("实体入库",7L);map.put("档案征集",8L);map.put("档案捐赠",9L);
        return map;
    }




    public static void main(String[] args) {
//        Long type = 1L;
//      if (type == workType.get("数据接收")){
        //正确用法
//        if (type.equals(workType.get("数据接收"))){
//            System.out.println("true");
//        }else{
//            System.out.println("false");
//        }

        //100万条数据，加入map中
        Map<String, String> map = new HashMap<>();

        long start = 0;
        long end = 0;
// 先垃圾回收
        System.gc();
        start = Runtime.getRuntime().freeMemory();

        for (int i = 1; i <= 1000000; i++) {
            map.put("QZH01-Y-WS.W-1950-001-00" + i, i + "");
        }

        // 快要计算的时,再清理一次
        System.gc();
        end = Runtime.getRuntime().freeMemory();
        System.out.println("一个HashMap对象占内存:" + (end - start)/1000.0 + "KB");


    }
}
