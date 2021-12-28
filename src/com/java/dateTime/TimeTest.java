package com.java.dateTime;

import java.util.Calendar;

public class TimeTest {
    public static void main(String[] args) {
//        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH) + 1;
//        int date = c.get(Calendar.DATE);
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//        int second = c.get(Calendar.SECOND);
//        System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);

        getYearMonthDay();
    }

    public static String getYearMonthDay(){
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        String dateStr = year + "/" + month + "/" + date + "/";
        System.out.println(dateStr);
        return dateStr;
    }

}
