package com.java.test;

import java.util.Arrays;
import java.util.List;

public class BaseDemo {

    public static void main(String[] args) {
        String[] xx = getQueryFields().toArray(new String[]{});//List<String> 直接转换成 String[]
        for (int i = 0; i < xx.length; i++) {
            System.out.println(xx[i]);
        }
    }

    /**
     * @return 查询字段
     */
    public static List<String> getQueryFields() {
        return Arrays.asList("id","approve_id","form_id","table_id","entity_id");
    }
}
