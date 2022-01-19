package com.java.dynamic;

/**
 * 1.普通类只能单继承类或抽象类；
 * 2.可以实现多个接口
 * 默认方法和静态方法不用在实现类中体现出来
 *
 */
public class ClassDemo extends AbstractDemo implements InterfaceDemo,InterfaceDemo2 {

    public String ii = "";
    private String jj = "";
    protected String kk = "";

    //private 只能类内部使用
    private ClassDemo(String i, String j){
        this.ii = i;
        this.jj = j;
    }

    //public 可以new使用
    public ClassDemo(String i, String j, String k){
        this.ii = i;
        this.jj = j;
        this.kk = k;
    }

    @Override
    public void method1() {
        ClassDemo xx = new ClassDemo("1","2");
        ClassDemo yy = new ClassDemo("1","2","3");
    }

    //InterfaceDemo.m1
    @Override
    public void m1() {

    }

    //InterfaceDemo2.m2
    @Override
    public void m2() {

    }
}
