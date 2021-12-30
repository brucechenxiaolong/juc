package com.java.list;

import com.java.tree.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * Stream 和 parallelStream
 * Stream 是在 Java8 新增的特性，普遍称其为流；它不是数据结构也不存放任何数据，其主要用于集合的逻辑处理。
 * parallelStream提供了流的并行处理，它是Stream的另一重要特性，其底层使用Fork/Join框架实现。简单理解就是多线程异步任务的一种实现。
 */
public class Special18 {

    public static void main(String[] args) {

//        listParallelStream();
//        try {
//            collectionUseType();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        parallelStream();
//        parallelStream2();

        try {
            testAdd();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Collections.synchronizedList & CopyOnWriteArrayList在读写操作上的差距
     * 写操作用：Collections.synchronizedList(new ArrayList<>());
     * 读操作用：CopyOnWriteArrayList
     * @throws Exception
     */
    private static void testAdd() throws Exception {

        int THREAD_COUNT = 16;

        List<Integer> list1 = new CopyOnWriteArrayList<Integer>();
        List<Integer> list2 = Collections.synchronizedList(new ArrayList<Integer>());
        Vector<Integer> v  = new Vector<Integer>();

        CountDownLatch add_countDownLatch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);//线程池

        int add_copyCostTime = 0;
        int add_synchCostTime = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            add_copyCostTime += executor.submit(new AddTestTask(list1, add_countDownLatch)).get();
        }
        System.out.println("CopyOnWriteArrayList add method cost time is " + add_copyCostTime);

        for (int i = 0; i < THREAD_COUNT; i++) {
            add_synchCostTime += executor.submit(new AddTestTask(list2, add_countDownLatch)).get();
        }
        System.out.println("Collections.synchronizedList add method cost time is " + add_synchCostTime);

    }

    private static void parallelStream2() {
        //parallelStream-多线程操作资源类
        List<Integer> integers = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++){
            //插入100个数据
            integers.add(i);
        }
        System.out.println("初始：" + integers.parallelStream().count());
        //多管道遍历
        List<Integer> integerList = new ArrayList<Integer>();//list不安全
//        List<Integer> integerList = new CopyOnWriteArrayList<Integer>();//list线程安全

        integers.parallelStream().forEach(e -> {
            //添加list的方法
            integerList.add(e);
            try {
                //休眠100ms，假装执行某些任务
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        System.out.println("最后输出：" + integerList.parallelStream().count());//97
    }

    private static void listParallelStream() {
        //------------------------------1.Arrays.asList() 用法-----------------------------
        String resource_id = "A-ME-ML";
        String resource_id2 = "xxx";
        String resource_id3 = "yyy";

        List<String> list = Arrays.asList(resource_id, resource_id2, resource_id3);
        for (String xx:list) {
            System.out.println(xx);
        }

        //-----------------------------2.Collections.synchronizedList(list) - 线程安全的list-----------------------------
        //parallelStream()-并行流操作
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 5; i++) {
            Person p = new Person(i, "name" + i, "sex" + i, i);
            persons.add(p);
        }
        List<Person> syncList = Collections.synchronizedList(persons);
        //并行流操作，输出是无序的
        syncList.parallelStream().forEach(xx ->{
            System.out.println(Thread.currentThread().getName() + ">>" + xx.getName());
        });

        //-----------------------------3.???-----------------------------
    }

    /**
     * rt.jar Collections 的用法
     * @throws InterruptedException
     */
    private static void collectionUseType() throws InterruptedException {
        List<Date> fmtList = new ArrayList<Date>();
        fmtList.add(new Date());

        Thread.sleep(2000);
        fmtList.add(new Date());

        Thread.sleep(2000);
        fmtList.add(new Date());

        Thread.sleep(2000);
        fmtList.add(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String xx = sdf.format(Collections.min(fmtList)) + "-------" + sdf.format(Collections.max(fmtList));
        System.out.println(xx);

    }

    /**
     * 并行流中 map put 的使用方法
     * map在接收数据时：要用现程安全的map
     */
    private static void parallelStream(){
        //链式编程
        User user1 = new User(11,"a",23, new Date(), "2021-01-01");
        User user2 = new User(12,"b",24, new Date(), "2021-01-02");
        User user3 = new User(13,"c",22, new Date(), "2021-01-03");
        User user4 = new User(14,"d",28, new Date(), "2021-01-04");
        User user5 = new User(16,"e",26, new Date(), "2021-01-05");
        List<User> list = Arrays.asList(user1,user2,user3,user4,user5);//一次性添加对象


        Map<String,User> userMap = Collections.synchronizedMap(new HashMap<>());//
        list.parallelStream().forEach(user -> {
            System.out.println(Thread.currentThread().getName());//打印当前线程名称
            userMap.put("" + user.getId(),user);
        });

        userMap.forEach(new BiConsumer<String, User>() {
            @Override
            public void accept(String s, User user) {
                System.out.println("key=" + s + "; user.getName=" + user.getUserName());
            }
        });

    }


}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
class User{
    private Integer id;
    private String userName;
    private int age;
    private Date birthday;
    private String dateTime;
}

class AddTestTask implements Callable<Integer> {

    private int NUM = 10000;

    List<Integer> list;
    CountDownLatch countDownLatch;

    AddTestTask(List<Integer> list, CountDownLatch countDownLatch) {
        this.list = list;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public Integer call() throws Exception {
        int num = new Random().nextInt(1000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            list.add(num);
        }
        long end = System.currentTimeMillis();
        countDownLatch.countDown();
        return (int) (end - start);
    }
}
