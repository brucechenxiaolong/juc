package com.java.jvm;

public class StackOverflowErrorDemo {

    public static void m1(int x, int y){
        m1(1,2);
    }
    public static void main(String[] args) {
        m1(1,2);
    }
}
