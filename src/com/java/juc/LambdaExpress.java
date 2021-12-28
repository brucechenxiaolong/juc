package com.java.juc;

/**
 * 1 函数式编程
 * 2
 * lambdaExpress表达式
 * 1.语法：() -> {}  小括号，写死右箭头，实现方法大括号
 * 2.@FunctionalInterface - 函数式接口
 * 3.default
 */
public class LambdaExpress {

	//2.静态内部类
	static class IfaceImpl2 implements Iface{
		@Override
		public void run() {
			System.out.println("I like lambda2");
		}
	}

	public static void main(String[] args) {
		Iface face = new IfaceImpl();
		face.run();
		Iface.xx();

		face = new IfaceImpl2();
		face.run();

		//3.局部内部类
		class IfaceImpl3 implements Iface{
			@Override
			public void run() {
				System.out.println("I like lambda3");
			}
		}

		face = new IfaceImpl3();
		face.run();

		//4.匿名内部类,没有类的名称，必须借助接口或父类 new 接口()
		face = new Iface() {
			@Override
			public void run() {
				System.out.println("I like lambda4");
			}
		};
		face.run();

		//5.********lambda表达式***********
		face = () -> {
			System.out.println("I like lambda5");
		};
		face.run();


		//----------------华丽的分隔符-----------------------


		IfaceParam faceParam = (int a) -> {
			System.out.println("方式一，参数值：" + a);
		};
		faceParam.run(100);

		faceParam = a -> {
			System.out.println("方式二，参数值：" + a);
		};
		faceParam.run(200);

	}
}

//定义函数式接口的条件如下：
//1.只包含唯一一个抽象方法
//2.可以有静态方法static
//3.可以有默认方法default
interface Iface{
	public abstract void run();
	//定义静态方法
	public static void xx(){
		System.out.println("xxxxxxxxxx");
	}
	public default int yy(){
		return 0;
	}
}

//1.实现类
class IfaceImpl implements Iface{

	@Override
	public void run() {
		System.out.println("I like lambda1");
	}
}

//定义有参数的接口
interface IfaceParam{
	public abstract void run(int a);

	public static void xx(){
		System.out.println("xx");
	}
	public default int yy(){
		return 0;
	}

}