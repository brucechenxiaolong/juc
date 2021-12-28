package com.java.dynamic;

public class EnumMainDemo {
    public static void main(String[] args) {

        //枚举test
        EnumDemo color = EnumDemo.PURPLE;
        System.out.println(color.toString());
        switch (color){
            case GRAY:
                System.out.println("灰色");
                break;
            case BLUE:
                System.out.println("蓝色");
                break;
            case PURPLE:
                System.out.println("紫色");
                break;
            default:
                break;
        }

        //枚举中也能定义方法
        System.out.println(color.operate(1,2));

        //用法三：向枚举中添加新方法
        System.out.println(EnumMyDay.MONDAY.getCode() + "=" + EnumMyDay.MONDAY.getName());
        System.out.println(EnumMyDay.TUESDAY.getCode() + "=" + EnumMyDay.TUESDAY.getName());
        System.out.println(EnumMyDay.WEDNESDAY);
    }
}
