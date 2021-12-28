package com.java.thread.synchronize;

public class NotSafeGetMoney {
    public static void main(String[] args) {
        //账户
        Acount acount = new Acount("结婚基金", 100);

        //多线程去访问同一个账户
        Drawing you = new Drawing(acount, 20, "你");
        Drawing wife = new Drawing(acount, 40, "妻子");
        Drawing xiaoSan = new Drawing(acount, 50, "小三");

        you.start();
        wife.start();
        xiaoSan.start();
    }
}

//银行账户
class Acount{
    String acountName;
    int money;
    public Acount(String acountName, int money){
        this.acountName = acountName;
        this.money = money;
    }
}

//取钱操作
class Drawing extends Thread{
    Acount acount;//账户
    int drawingMoney;//取多少钱
    int nowMoney;//手上有多少钱

    public Drawing(Acount acount, int drawingMoney, String name){
        super(name);
        this.acount = acount;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {

        //synchronized-同步块，synchronized(obj) 中的obj可以是任何对象，推荐使用共享资源为作为同步监视器。
        //使用synchronized-锁住，银行账户信息。
        synchronized (acount){
            if(acount.money-drawingMoney<0){
                System.out.println(Thread.currentThread().getName() + "，钱不够，取不了。");
                return;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.getName() + "取走了，" + drawingMoney + "元。");

            acount.money = acount.money - drawingMoney;//开内余额
            nowMoney = nowMoney+drawingMoney;//手上的钱
            System.out.println(acount.acountName + "账户，余额为：" + acount.money + "元。");
            System.out.println(this.getName() + "手里的钱是：" + nowMoney + "元。");

            System.out.println("----------------------------执行完毕！------------------------------");
        }
    }
}


