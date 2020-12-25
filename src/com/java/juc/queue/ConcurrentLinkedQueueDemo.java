package com.java.juc.queue;

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

    private Queue<LogOptBean> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueueDemo xx = new ConcurrentLinkedQueueDemo();
        xx.append();//往并发线程中添加5条数据
        xx.execute();//启动线程
        sleep(20000);//休眠20秒

        xx.append2();//往并发线程中再添加5条数据
    }

    public void append() {
        for (int i = 1; i <= 5; i++) {
            LogOptBean logOptBean = new LogOptBean("1"+i,"username"+i,i);
            queue.offer(logOptBean);
        }
    }

    public void append2() {
        for (int i = 6; i <= 10; i++) {
            LogOptBean logOptBean = new LogOptBean("1"+i,"username"+i,i);
            queue.offer(logOptBean);
        }
    }

    class LogThread extends Thread{
        @Override
        public void run(){
            while (true){
                if(!queue.isEmpty()){
                    LogOptBean logOptBean = queue.poll();
                    if(null != logOptBean){
                        System.out.println(logOptBean.getUserName());
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

    private void execute(){
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
