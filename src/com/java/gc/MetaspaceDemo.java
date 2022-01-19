package com.java.gc;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * java.lang.OutOfMemoryError:Metaspace（元空间）
 * VM options: -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 * 默认大小：20MB
 *
 * 模拟：java.lang.OutOfMemoryError: Metaspace 空间溢出
 * 我们不断生成类往元空间灌入，类占据的空间总是会超过Metaspace指定的空间大小。
 *
 */
public class MetaspaceDemo {
    public static void main(String[] args) {
        int i = 0;
        try {
            //不断生成类往元空间灌入
            while (true){
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);//不断生成类往元空间灌入
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();//不断生成类往元空间灌入
            }
        }catch (Throwable e){
            System.out.println("***************异常发生在：i=" + i + "次");
            e.printStackTrace();
        }
    }

    /**
     * 静态内部类
     */
    static class OOMTest{

    }
}
