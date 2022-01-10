package com.java.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * 多线程处理并发链式队列
 */
public class ConcurrentLinkedQueueDemo {

    public static Queue<LogOptBean> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws InterruptedException {
        append();//往并发线程中添加5条数据

        execute();//启动线程
        sleep(20000);//休眠20秒

        append2();//往并发线程中再添加5条数据
    }

    public static void append() {
        for (int i = 1; i <= 5; i++) {
            LogOptBean logOptBean = new LogOptBean("1"+i,"username"+i,i);
            queue.offer(logOptBean);
        }
    }

    public static void append2() {
        for (int i = 6; i <= 10; i++) {
            LogOptBean logOptBean = new LogOptBean("1"+i,"username"+i,i);
            queue.offer(logOptBean);
        }
    }

    static class LogThread extends Thread{
        @Override
        public void run(){
            while (true){
                //消费队列
                if(!queue.isEmpty()){
                    LogOptBean logOptBean = queue.poll();//消费
                    if(null != logOptBean){
                        System.out.println("打印队列：" + logOptBean.getUserName());
                    }
                }else{
                    try {
                        System.out.println("--休眠中，休眠时间2秒--");
                        TimeUnit.SECONDS.sleep(2);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void execute(){
        new LogThread().start();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
class LogOptBean{

    private String userId;
    private String userName;
    private Integer optLevel;

}


