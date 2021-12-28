package com.java.dynamic;

/**
 * enum只能有一个";"
 * enum中也能定义方法
 */
public enum EnumDemo {
    PURPLE,GRAY,BLUE;

    public Double operate(double num1, double num2){
        switch (this){
            case PURPLE:
                return num1 + num2;
            case BLUE:
                return num1 - num2;
            case GRAY:
                return num1 * num2;
            default:
                return num1/num2;
        }
    }
}
