package com.java.quartz;

import java.util.TimerTask;

public class TimerTaskBean extends TimerTask {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 运行结束");
    }



}
