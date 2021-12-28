package com.java.dynamic;

/**
 * 1.普通类只能单继承类或抽象类；
 * 2.可以实现多个接口
 */
public class ClassDemo extends AbstractDemo implements InterfaceDemo,InterfaceDemo2 {

    private String jj = "";
    public String ii = "";

    //private 只能类内部使用
    private ClassDemo(String p1, String p2){
        this.jj = p1;
        this.ii = p2;
    }

    //public 可以new使用
    public ClassDemo(String p1, String p2, String p3){
        this.jj = p1;
        this.ii = p2;
    }

    @Override
    public void xxx() {
        ClassDemo xx = new ClassDemo("1","2");
        ClassDemo yy = new ClassDemo("1","2","3");
    }

    @Override
    public void eee() {

    }

    @Override
    public void fff() {

    }

    @Override
    public void ggg() {

    }
}
