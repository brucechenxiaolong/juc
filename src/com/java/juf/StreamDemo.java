package com.java.juf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
class User{
    private Integer id;
    private String userName;
    private int age;
}

/**
 * 链式编程 + 流式计算
 * 链式编程：对象添加注解：@Accessors(chain = true)
 */
public class StreamDemo {
    public static void main(String[] args) {

        //链式编程
        User user1 = new User(11,"a",23);
        User user2 = new User(12,"b",24);
        User user3 = new User(13,"c",22);
        User user4 = new User(14,"d",28);
        User user5 = new User(16,"e",26);
        List<User> list = Arrays.asList(user1,user2,user3,user4,user5);//一次性添加对象

        //流式计算，实现如下内容
        //流计算实现：找出以上满足条件的用户：偶数ID并且年龄大于24并且用户名转为大写并且用户名字母倒排序，最后只输出一个用户名。
        list.stream().filter(t -> {
            return t.getId() % 2 == 0;
        }).filter(t -> {
            return t.getAge() > 24;
        }).map(t -> {
            t.setUserName(t.getUserName().toUpperCase());
            return t;
        }).sorted((t1, t2) -> {
            return (t2.getUserName()).compareTo(t1.getUserName());
        }).limit(1).forEach(System.out::println);


//四大函数式接口实例，为了满足java.util.stream
//        fourFunctionInterface();
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
