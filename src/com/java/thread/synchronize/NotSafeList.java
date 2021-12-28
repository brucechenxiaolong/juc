package com.java.thread.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//ArrayList线程不安全
//CopyOnWriteArrayList线程安全
public class NotSafeList {

    public static void main(String[] args) {
        NotSafeList notSafeList = new NotSafeList();
        notSafeList.arrayList();
        notSafeList.copyOnWriteArrayList();

    }

    public void arrayList(){
        System.out.println("--------ArrayList,线程不安全-----------");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                //使用synchronized-实现list对象线程安全
//                synchronized (list){
                list.add(Thread.currentThread().getName());
//                }

            },"" + i).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());//结果size<1000
    }

    public void copyOnWriteArrayList(){

        System.out.println("--------CopyOnWriteArrayList,线程安全-----------");
        List<String> list2 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                list2.add(Thread.currentThread().getName());
            },"" + i).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list2.size());//结果size<1000
    }
}
