package com.java.test;

import java.util.ArrayList;
import java.util.List;

public class ListOperations {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("test001");
        list.add("test002");
        list.add("test003");
        list.add("test004");
        list.add("test005");
        list.add("test006");
        list.add("test007");
        list.add("test008");
        list.add("test009");
        list.add("test010");
        list.add("test011");
        list.add("test012");
        list.add("test013");

        List<String> list2 = startPage(list, 10, 2);
        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }

    }


    /**
     * 	开始分页
     * @param list
     * @param pageSize 每页多少条数据
     * @param pageNum 页码，第几页
     * @return
     */
    public static List startPage(List list, Integer pageSize, Integer pageNum) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List pageList = list.subList(fromIndex, toIndex);

        return pageList;
    }

}
