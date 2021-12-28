package com.java.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeFormatDemo {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-10);

        String endDate = sdf.format(calendar.getTime());
        System.out.println("endDate=" + endDate);


    }
}
