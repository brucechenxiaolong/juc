package com.java.new18;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 新的日期API LocalDate | LocalTime | LocalDateTime
 * 新的日期API都是不可变的，更使用于多线程的使用环境中
 */
public class NewDateDemo {
    public static void main(String[] args) {
//        method01();
//        formatDate();


        //2018-05-04
        LocalDate today = LocalDate.now();
        // 取本月第1天： 2018-05-01
        LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDayOfThisMonth);
        // 取本月第2天：2018-05-02
        LocalDate secondDayOfThisMonth = today.withDayOfMonth(2);
        System.out.println(secondDayOfThisMonth);
        // 取本月最后一天，再也不用计算是28，29，30还是31： 2018-05-31
        LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        // 取下一天：2018-06-01
        LocalDate firstDayOf2015 = lastDayOfThisMonth.plusDays(1);
        System.out.println(firstDayOf2015);
        // 取2018年10月第一个周三 so easy?：  2018-10-03
        LocalDate thirdMondayOf2018 = LocalDate.parse("2018-10-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        System.out.println(thirdMondayOf2018);
        //16:25:46.448(纳秒值)
        LocalTime todayTimeWithMillisTime = LocalTime.now();
        System.out.println(todayTimeWithMillisTime);
        //16:28:48 不带纳秒值
        LocalTime todayTimeWithNoMillisTime = LocalTime.now().withNano(0);
        System.out.println(todayTimeWithNoMillisTime);
        LocalTime time1 = LocalTime.parse("23:59:59");
        System.out.println(time1);

        //转化为时间戳  毫秒值
        long time2 = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long time3 = System.currentTimeMillis();
        System.out.println(time3);

        //时间戳转化为localdatetime
        DateTimeFormatter df= DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS");
        System.out.println(df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time2),ZoneId.of("Asia/Shanghai"))));

    }

    private static void method01() {
    /*
    // 从默认时区的系统时钟获取当前的日期时间。不用考虑时区差
    LocalDateTime date = LocalDateTime.now();
    //2022-01-05T20:44:31.597
    System.out.println(date);

    System.out.println(date.getYear());
    System.out.println(date.getMonthValue());
    System.out.println(date.getDayOfMonth());
    System.out.println(date.getHour());
    System.out.println(date.getMinute());
    System.out.println(date.getSecond());
    System.out.println(date.getNano());

    // 手动创建一个LocalDateTime实例
    LocalDateTime date2 = LocalDateTime.of(2017, 12, 17, 9, 31, 31, 31);
    System.out.println(date2);
    // 进行加操作，得到新的日期实例
    LocalDateTime date3 = date2.plusDays(12);
    System.out.println(date3);
    // 进行减操作，得到新的日期实例
    LocalDateTime date4 = date3.minusYears(2);
    System.out.println(date4);

    System.out.println("-----------------------------------");

    // 时间戳  1970年1月1日00：00：00 到某一个时间点的毫秒值
    // 默认获取UTC时区
    Instant ins = Instant.now();
    System.out.println(ins);

    System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
    System.out.println(System.currentTimeMillis());

    System.out.println(Instant.now().toEpochMilli());
    System.out.println(Instant.now().atOffset(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());

    System.out.println("------------------------------------------------");

    // Duration:计算两个时间之间的间隔
    // Period：计算两个日期之间的间隔

    Instant ins1 = Instant.now();

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    Instant ins2 = Instant.now();
    Duration dura = Duration.between(ins1, ins2);
    System.out.println(dura);
    System.out.println(dura.toMillis());

    System.out.println("======================");
    LocalTime localTime = LocalTime.now();
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    LocalTime localTime2 = LocalTime.now();
    Duration du2 = Duration.between(localTime, localTime2);
    System.out.println(du2);
    System.out.println(du2.toMillis());

    System.out.println("-----------------------------");

    LocalDate localDate =LocalDate.now();
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    LocalDate localDate2 = LocalDate.of(2016,12,12);
    Period pe = Period.between(localDate, localDate2);
    System.out.println(pe);

    System.out.println("-----------------------------");

    // temperalAdjust 时间校验器
    // 例如获取下周日  下一个工作日
    LocalDateTime ldt1 = LocalDateTime.now();
    System.out.println(ldt1);

    // 获取一年中的第一天
    LocalDateTime ldt2 = ldt1.withDayOfYear(1);
    System.out.println(ldt2);
    // 获取一个月中的第一天
    LocalDateTime ldt3 = ldt1.withDayOfMonth(1);
    System.out.println(ldt3);

    LocalDateTime ldt4 = ldt1.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    System.out.println(ldt4);

    // 获取下一个工作日
    LocalDateTime ldt5 = ldt1.with((t) -> {
        LocalDateTime ldt6 = (LocalDateTime)t;
        DayOfWeek dayOfWeek = ldt6.getDayOfWeek();
        if (DayOfWeek.FRIDAY.equals(dayOfWeek)){
            return ldt6.plusDays(3);
        }
        else if (DayOfWeek.SATURDAY.equals(dayOfWeek)){
            return ldt6.plusDays(2);
        }
        else {
            return ldt6.plusDays(1);
        }
    });
    System.out.println(ldt5);*/
    }

    private static void formatDate() {
        // DateTimeFormatter: 格式化时间/日期
        // 自定义格式
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String strDate1 = ldt.format(formatter);
        String strDate = formatter.format(ldt);
        System.out.println(strDate);
        System.out.println(strDate1);

        // 使用api提供的格式
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime ldt2 = LocalDateTime.now();
        String strDate3 = dtf.format(ldt2);
        System.out.println(strDate3);

        // 解析字符串to时间
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        LocalDateTime ldt4 = LocalDateTime.parse("2017-09-28 17:07:05",df);
        System.out.println("LocalDateTime转成String类型的时间："+localTime);
        System.out.println("String类型的时间转成LocalDateTime："+ldt4);

        System.out.println("------------------------------");

        //获取当前日期,只含年月日 固定格式 yyyy-MM-dd    2018-05-04
        LocalDate today = LocalDate.now();
        System.out.println(today);

        // 根据年月日取日期，5月就是5，
        LocalDate oldDate = LocalDate.of(2018, 5, 1);
        System.out.println(oldDate);

        // 根据字符串取：默认格式yyyy-MM-dd，02不能写成2
        LocalDate yesteday = LocalDate.parse("2018-05-03");
        System.out.println(yesteday);

        // 如果不是闰年 传入29号也会报错
//        LocalDate.parse("2018-02-29");//java.time.format.DateTimeParseException
    }
}
