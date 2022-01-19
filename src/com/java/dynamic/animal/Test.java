package com.java.dynamic.animal;

public class Test {
    public static void main(String[] args) {
        //普通类继承
        Dog1 dog1 = new Dog1("哈巴狗","白色");
        dog1.run();
        dog1.run2();
        Fish1 fish1 = new Fish1("锦鲤","红色");
        fish1.run();
        /**
         * 输出结果：
         * 哈巴狗四条腿跑的很快！！！
         * 锦鲤四条腿跑的很快！！！
         *
         * 以上方法问题：现在发现了问题，锦鲤怎么可以跑呢？因为他放在了父类的方法中。现在由俩种解决方案：1.父类不用写run方法，子类自己写 2.父类中定义抽象方法，强制子类重写run方法
         */

        System.out.println();

        //抽象类继承
        System.out.println("-----抽象方法的使用精髓：子类重写方法，实现自己的功能。----");
        Dog2 dog2 = new Dog2("哈巴狗","白色");
        dog2.run();
        dog2.eat();

        Fish2 fish2 = new Fish2("锦鲤","红色");
        fish2.run();
        fish2.eat();

    }
}
