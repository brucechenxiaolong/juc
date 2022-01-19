package com.java.thread;

/**
 * 每一个ThreadLocal能够放一个线程级别的变量，可是它本身能够被多个线程共享使用，并且又能够达到线程安全的目的，且绝对线程安全。
 *
 */
public class ThreadLocalDemo {

    //也可以传递类参数
    //代表一个能够存放String类型的ThreadLocal对象。此时不论什么一个线程能够并发访问这个变量，对它进行写入、读取操作，都是线程安全的。
    protected static final ThreadLocal<String> LOCAL_PAGE = new ThreadLocal<String>();

    /**
     * 设置 Page 参数
     *
     * @param page
     */
    protected static void setLocalPage(String page) {
        LOCAL_PAGE.set(page);
    }

    /**
     * 获取 Page 参数
     *
     * @return
     */
    public static <T> String getLocalPage() {
        return LOCAL_PAGE.get();
    }

    /**
     * 移除本地变量
     */
    public static void clearPage() {
        LOCAL_PAGE.remove();
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            final int ii = i;
            new Thread(()->{
                //TODO please coding

                try {
                    ThreadLocalDemo.setLocalPage("" + ii);
                    System.out.println(Thread.currentThread().getName() + "输出结果是：" + ThreadLocalDemo.getLocalPage());
                }finally {
                    ThreadLocalDemo.clearPage();
                }

            }, "t" + i).start();
        }
    }
}
