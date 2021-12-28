package com.java.dynamic;

/**
 * 用法三：向枚举中添加新方法
 * 枚举定义方法
 */
public enum EnumMyDay {
    MONDAY(1,"星期一"),TUESDAY(2,"星期二"),WEDNESDAY(3,"星期三");

    private int code;
    private String name;
    //enum的构造方法只能是private修饰
    private EnumMyDay(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
