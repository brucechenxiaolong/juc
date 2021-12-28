package com.java.tree;

public class Person {
    private int id;
    private String name;
    private String sex;
    private float  height;

    public Person(int id, String name, String sex, float height) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
