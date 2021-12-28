package com.java.thread.synchronize;

public class NotSafeBuyTicket {
    public static void main(String[] args) {

        Ticket ticket = new Ticket("电影票",10);

        BuyTicket buy = new BuyTicket(ticket);

        //启动3个线程同时买票
        new Thread(buy,"小亮").start();
        new Thread(buy,"小明").start();
        new Thread(buy,"黄牛").start();
    }
}

//售票厅类
class Ticket{
    String typeName;//售票类型
    int ticketNums;//票数

    public Ticket(String typeName, int ticketNums){
        this.typeName = typeName;
        this.ticketNums = ticketNums;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTicketNums() {
        return ticketNums;
    }

    public void setTicketNums(int ticketNums) {
        this.ticketNums = ticketNums;
    }
}

//定义买票类
class BuyTicket implements Runnable{
//    private int ticketNums = 10;
    boolean flag = true;

    Ticket ticket;

    public BuyTicket(Ticket ticket){
        this.ticket = ticket;
    }

    @Override
    public void run() {

        //synchronized 默认锁的是当前类对象this
        //实现多线程访问时，做到数据对象不被多次请求造成数据紊乱。达到多线程排队访问同一资源。
        synchronized (ticket){
            while (flag) {

                System.out.println(Thread.currentThread().getName() + "线程，当前获取的了第：" + ticket.getTicketNums() + "张票。");
                int ticketNums = ticket.getTicketNums()-1;

                if(ticketNums <= 0){
                    flag = false;
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ticket.setTicketNums(ticketNums);

            }
        }

    }
}
