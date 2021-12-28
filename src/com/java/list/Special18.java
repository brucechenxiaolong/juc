package com.java.list;

import com.java.tree.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.*;
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
        parallelStream();

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
