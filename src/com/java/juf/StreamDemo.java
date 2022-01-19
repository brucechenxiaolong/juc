package com.java.juf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

/**
 * 链式编程 + 流式计算
 * 链式编程：对象添加注解：@Accessors(chain = true)
 */
public class StreamDemo {
    public static void main(String[] args) {

        listStreamFilter();

//        collectorsToList();
//        collectorsToSet();
//        collectorsToSetOneSelf();
//        collectorsJoining();
//        collectorsMap();
//        convertIndexName();
//        streamMatch();
//        streamSord();
//        streamCollect();

//四大函数式接口实例，为了满足java.util.stream
//        fourFunctionInterface();
    }

    private static void listStreamFilter() {
        //链式编程
        User user1 = new User(11,"zhangsan1",23, new Date(), "2021-01-01");
        User user2 = new User(12,"zhangsan2",24, new Date(), "2021-01-01");
        User user3 = new User(13,"lisi1",22, new Date(), "2021-01-01");
        User user4 = new User(14,"lisi2",28, new Date(), "2021-01-01");
        User user5 = new User(16,"wangwu",26, new Date(), "2021-01-01");
        List<User> list = Arrays.asList(user1,user2,user3,user4,user5);//一次性添加对象

        //流式计算，实现如下内容
        //流计算实现：找出以上满足条件的用户：偶数ID并且年龄大于24并且用户名转为大写并且用户名字母倒排序，最后只输出一个用户名。
//        list.stream().filter(t -> {
//            return t.getId() % 2 == 0;
//        }).filter(t -> {
//            return t.getAge() > 24;
//        }).map(t -> {
//            t.setUserName(t.getUserName().toUpperCase());
//            return t;
//        }).sorted((t1, t2) -> {
//            return (t2.getUserName()).compareTo(t1.getUserName());
//        }).limit(1).forEach(System.out::println);

        //模糊查询
        list.stream().filter(t -> t.getUserName().contains("zhangsan")).forEach(System.out::println);

    }

    /**
     * 将流中的数据转成集合类型：
     * 一、将数据收集进一个列表(Stream 转换为 List，允许重复值，有顺序)
     *
     */
    private static void collectorsToList() {
        Stream<String> language = Stream.of("java", "python","php",".net", "java", "php");
        List<String> list = language.collect(Collectors.toList());//Stream转List
        list.forEach(xx -> {
            System.out.println(xx);
        });
        System.out.println("---------------------------------------------");
        List<String> list2 = Arrays.asList("java", "python","php",".net", "java", "php");
        List<String> listResult = list2.stream().filter(xx -> xx.equals("java") || xx.equals("php")).collect(Collectors.toList());
        listResult.forEach(bb -> {
            System.out.println(bb);
        });

        System.out.println("---------------------------------------------");

        String xx2 = list2.stream().filter(xx -> xx.equals("java") || xx.equals("php")).findFirst().orElse(null);//值返回第一个，如果list为空，则返回null
        System.out.println(xx2);

    }

    /**
     * 二：将数据收集进一个集合(Stream 转换为 Set，不允许重复值，没有顺序)
     */
    private static void collectorsToSet() {
        Stream<String> language = Stream.of("java", "python","php",".net", "java", "php");
        Set<String> setResult = language.collect(Collectors.toSet());
        setResult.forEach(xx -> {
            System.out.println(xx);
        });
    }

    /**
     * 三、用自定义的实现Collection的数据结构收集
     */
    private static void collectorsToSetOneSelf() {
        List<String> list = Arrays.asList("java", "python","php",".net", "java", "php");
        List<String> linkedListResult = list.stream().collect(Collectors.toCollection(LinkedList::new));
        linkedListResult.forEach(xx -> {
            System.out.println(xx);
        });
        System.out.println("---------------------------------------------");
        List<String> copyOnWriteListResult = list.stream().collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        copyOnWriteListResult.forEach(xx -> {
            System.out.println(xx);
        });
        System.out.println("---------------------------------------------");
        TreeSet<String> treeSetResult = list.stream().collect(Collectors.toCollection(TreeSet::new));
        treeSetResult.forEach(xx -> {
            System.out.println(xx);
        });

    }

    /**
     * 四、对Stream的字符串拼接
     */
    private static void collectorsJoining() {
        List<String> list = Arrays.asList("java", "python","php",".net", "java", "php");
        System.out.println(list.stream().collect(Collectors.joining("|","begin-- "," --end")));
    }

    /**
     * stream.map 获取对象中某一个字段的集合值
     * toSet-去除重复值
     * toList-包括重复值
     */
    private static void collectorsMap() {

        List<User> list = new ArrayList<>();
        User u1 = new User();
        u1.setId(1);
        u1.setUserName("zhangsan");
        u1.setAge(10);
        list.add(u1);

        u1 = new User();
        u1.setId(2);
        u1.setUserName("lisi");
        u1.setAge(11);
        list.add(u1);

        u1 = new User();
        u1.setId(3);
        u1.setUserName("wangwu");
        u1.setAge(12);
        list.add(u1);

        Set<String> xx = list.stream().map(User::getUserName).collect(Collectors.toSet());
        xx.forEach(bb -> System.out.println(bb));
    }

    /**
     * 装换索引名称，名称转小写，加_archive后缀
     * 注：有重复值
     */
    public static void convertIndexName() {
        List<String> indices = Arrays.asList("T_100", "t_101","T_102","T_103","T_100");
        String[] xx = indices.stream().map(String::toLowerCase).map(s -> s + "_archive").toArray(String[]::new);
        for (int i = 0; i < xx.length; i++) {
            System.out.println(xx[i]);
        }
    }

    /**
     * java8 stream接口终端操作 anyMatch，allMatch，noneMatch
     *
     */
    public static void streamMatch(){
        List<User> list = new ArrayList<>();
        User u1 = new User();
        u1.setId(1);
        u1.setUserName("zhangsan");
        u1.setAge(10);
        list.add(u1);

        u1 = new User();
        u1.setId(2);
        u1.setUserName("lisi");
        u1.setAge(11);
        list.add(u1);

        u1 = new User();
        u1.setId(3);
        u1.setUserName("wangwu");
        u1.setAge(12);
        list.add(u1);

        u1 = new User();
        u1.setId(3);
        u1.setUserName("zhangsan");
        u1.setAge(12);
        list.add(u1);

        boolean haveUserName = list.stream().anyMatch(u -> "lisixx".equals(u.getUserName()));//如果输入的账号上面列表中没有，则返回false
        System.out.println("haveUserName=" + haveUserName);
        if (!haveUserName) {
            System.out.println("账号不在列表中！");
        }
        System.out.println("---------------------------------------------------------------");

        haveUserName = list.stream().allMatch(u -> "zhangsan".equals(u.getUserName()));//如果list中所有的username都是:zhangsan 则返回true
        System.out.println("haveUserName=" + haveUserName);
        if (haveUserName) {
            System.out.println("列表中所有的账号，都是：zhangsan；");
        }
        System.out.println("---------------------------------------------------------------");

        haveUserName = list.stream().noneMatch(u -> "chenxiaolong".equals(u.getUserName()));//
        System.out.println("haveUserName=" + haveUserName);
        if (haveUserName) {
            System.out.println("列表中没有：chenxiaolong；");
        }
        System.out.println("---------------------------------------------------------------");

        long count = list.stream().filter(u -> "zhangsan".equals(u.getUserName())).count();//如果list中所有的username都是:zhangsan 则返回true
        System.out.println("count=" + count);
        System.out.println("列表中一共有"+ count +"个zhangsan；");

    }

    /**
     * 排序
     */
    public static void streamSord(){
        List<User> list = new ArrayList<>();
        User u1 = new User();
        u1.setId(1);
        u1.setUserName("zhangsan");
        u1.setAge(10);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-29");
        list.add(u1);

        u1 = new User();
        u1.setId(2);
        u1.setUserName("lisi");
        u1.setAge(11);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-12");
        list.add(u1);

        u1 = new User();
        u1.setId(3);
        u1.setUserName("wangwu");
        u1.setAge(12);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-24");
        list.add(u1);

        u1 = new User();
        u1.setId(4);
        u1.setUserName("zhangsan");
        u1.setAge(12);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-04");
        list.add(u1);

        List<User> listSord = list.stream().sorted(Comparator.comparing(User::getDateTime).reversed()).collect(Collectors.toList());//倒叙：reversed() 最大的值在第一位
        listSord.forEach(xx -> {
            System.out.println(xx.getId());
        });

    }

    /**
     * list条件分组
     */
    public static void streamCollect(){
        List<User> list = new ArrayList<>();
        User u1 = new User();
        u1.setId(1);
        u1.setUserName("zhangsan");
        u1.setAge(10);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-01");
        list.add(u1);

        u1 = new User();
        u1.setId(2);
        u1.setUserName("lisi");
        u1.setAge(11);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-12");
        list.add(u1);

        u1 = new User();
        u1.setId(3);
        u1.setUserName("wangwu");
        u1.setAge(12);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-24");
        list.add(u1);

        u1 = new User();
        u1.setId(4);
        u1.setUserName("zhangsan");
        u1.setAge(12);
        u1.setBirthday(new Date());
        u1.setDateTime("2021-01-04");
        list.add(u1);


        Map<Integer, List<User>> groups = list.stream().collect(Collectors.groupingBy(User::getAge, TreeMap::new, Collectors.toList()));//age-值相同合并到一组
        //循环分组后的数据并输入
        for (Map.Entry<Integer, List<User>> entry : groups.entrySet()) {
            System.out.println(entry.getKey());
            List<User> users = entry.getValue();
            users.forEach(xx -> System.out.println(xx.getUserName()));
            System.out.println("----------------------------------------");
        }

    }


    private static void fourFunctionInterface() {
        //----------------------------四大函数式接口-------------------------------
        //s是参数类型
        Function<String, Integer> function = s -> {return s.length();};
        System.out.println(function.apply("abc"));

        //s是参数类型
        Consumer<String> consumer = s -> {
            System.out.println(s.length());
        };
        consumer.accept("aaaa");

        //s是参数类型
        Predicate<String> predicate = s -> {
            boolean returnIs = true;
            if("cccc".equals(s)){
                returnIs = true;
            }else {
                returnIs = false;
            }
            return  returnIs;
        };
        System.out.println(predicate.test("cccc1"));

        //没有输入参数用：()
        Supplier<String> supplier = () -> {
            return "xxx";
        };
        System.out.println(supplier.get());
    }
}
