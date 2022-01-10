package com.java.queue;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * 队列代码演示
 */
public class QueueDemo {

    //建议 maximumPoolSize=5;blockingQueue=3;加起来正好16 等于一般电脑的cpu处理器数
    private static ExecutorService threadPool = new ThreadPoolExecutor(2,
            13,
            2L,TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());//CallerRunsPolicy-策略方针是：保证所有线程都要执行完毕，不抛出异常。

    public static void main(String[] args) {

        //1.ArrayBlockingQueue
//        method01();

        //2.ConcurrentLinkedQueue
        method02();

        //3.多线程操作：ArrayBlockingQueue
//        method03();

        //4.使用并行流消费队列中的数据
//        method04();


    }

    /**
     * 4.使用并行流消费队列中的数据
     */
    private static void method04() {
        BlockingQueue<SxjcResultParmsMqDTO> blockingQueue2 = new ArrayBlockingQueue(6,true);
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                SxjcResultParmsMqDTO mq = new SxjcResultParmsMqDTO();
                mq.setXx("" + i);
                try {
                    blockingQueue2.put(mq);
                    System.out.println("put success ..." + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();


        //使用平行流获取阻塞队列中的数据
        blockingQueue2.parallelStream().forEach(o -> {
            System.out.println(Thread.currentThread().getName() + "消费了：" + o.getXx());
        });
    }

    /**
     * 3.多线程操作：ArrayBlockingQueue
     */
    private static void method03() {
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                SxjcResultParmsMqDTO mq = new SxjcResultParmsMqDTO();
                mq.setXx("" + i);
                try {
                    blockingQueue.put(mq);
                    System.out.println("put success ..." + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        boolean xx = true;
        while (xx){
            threadPool.execute(() ->{
                System.out.println("当前正在执行的线程是：" + Thread.currentThread().getName());
                if (blockingQueue.isEmpty()){
                    System.out.println("blockingQueue is empty!");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    SxjcResultParmsMqDTO dto = null;
                    try {
                        dto = blockingQueue.take();
                        if (dto!=null){
                            System.out.println(dto.getXx() + " 被消费。。");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "我终于执行完了。。。");
            });
        }

    }

    private static Queue<SxjcResultParmsMqDTO> rabbitmqPubQueue = new ConcurrentLinkedQueue<>();//非阻塞队列
    /**
     * 2.ConcurrentLinkedQueue-非阻塞队列，容易出现oom 错误。
     *
     * 2.1:插入元素
     * boolean add(E e):将元素e插入到队列末尾，插入成功，则返回true；插入失败（即队列已满），返回false；
     * boolean offer(E e)将元素e插入到队列末尾，插入成功，则返回true；插入失败（即队列已满），返回false；和add一样。
     *
     * 2.2：获取元素
     * E peek():    获取但不移除此队列的头；如果此队列为空，则返回 null；--会一直存在数据，开发中不建议使用。
     * E poll():获取队首元素并移除，若队列不为空，则返回队首元素；否则返回null。
     *
     * boolean remove(Object o):从队列中移除指定元素的单个实例（如果存在）。
     *
     */
    private static void method02() {
        //ConcurrentLinkedQueue -队列，操作会一直添加数据，不会阻塞。不建议使用
        //Exception in thread "C" java.lang.OutOfMemoryError: Java heap space
        //插入元素
        new Thread(() -> {
//            String xx = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
//            int i = 1;
//            while (true){
//                SxjcResultParmsMqDTO mq = new SxjcResultParmsMqDTO();
//                xx = xx + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
//                mq.setXx(xx);
//                System.out.println(i + "当前字符串大小是：" + (mq.getXx().getBytes().length/1024) + "KB");
//                rabbitmqPubQueue.offer(mq);
//                i++;
//            }

            for (int j = 1; j <= 10; j++) {
                SxjcResultParmsMqDTO mq = new SxjcResultParmsMqDTO();
                mq.setXx("name 的 值是：" + j);
//                System.out.println(j + "当前字符串大小是：" + (mq.getXx().getBytes().length/1024) + "KB");
                rabbitmqPubQueue.offer(mq);
                System.out.println("插入元素成功；元素值是：" + j);
            }


        }, "C").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //消费元素
        new Thread(() -> {
            while (true){
                if(rabbitmqPubQueue.isEmpty()){
                    System.out.println("队列为空！size=" + rabbitmqPubQueue.size());
                    return;
                }else{
                    SxjcResultParmsMqDTO mq = rabbitmqPubQueue.poll();
                    System.out.println("获取元素成功; 元素值是：" + mq.getXx());
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();




    }

    private static BlockingQueue<SxjcResultParmsMqDTO> blockingQueue = new ArrayBlockingQueue(4,true);//阻塞队列

    /**
     * 1.ArrayBlockingQueue
     */
    private static void method01() {
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                SxjcResultParmsMqDTO mq = new SxjcResultParmsMqDTO();
                mq.setXx("" + i);
                try {
                    blockingQueue.put(mq);//阻塞：也就是说：插入的数据条数>队列能容纳的最大数量后；多余的数据需要等待。
                    System.out.println("put success ..." + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true){
                if (blockingQueue.isEmpty()){
                    System.out.println("blockingQueue is empty!");
                    return;
                }else{
                    SxjcResultParmsMqDTO dto = null;
                    try {
                        dto = blockingQueue.take();//阻塞队列，消费队列
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (dto!=null){
                        System.out.println(dto.getXx() + " 被消费。。");
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
    }


}

class SxjcResultParmsMqDTO{
    private String xx;

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }
}
