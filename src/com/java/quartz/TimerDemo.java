package com.java.quartz;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

    private static final long PERIOD = 5 * 1000;// 1秒钟

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTaskBean();
        System.out.println("start");
        //0表示立即执行一次，以后每隔一段时间执行一次
        timer.schedule(task, 0, PERIOD);
    }

    public static void circleRunTime() {

        int i = 1;
        while (true){
            Timer timer = new Timer("任务 - "+i);
            TimerTask task = new TimerTaskBean();
            timer.schedule(task, 5000);
            System.out.println("第" + i + "次任务，已启动，于3秒后执行");
        }

    }
}
