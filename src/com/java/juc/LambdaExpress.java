package com.java.juc;

/**
 *
 * 前提：java8
 * 1.接口式函数定义规则：增加注解 @FunctionalInterface (只要接口类有注解：@FunctionalInterface 进行修饰就能用Lambda表达式)
 * 2.接口式函数的方法只能有一个
 * 3.可以定义多个默认方法有方法体
 * 4.可以定义多个静态方法并且有方法体
 *
 */
@FunctionalInterface
interface Operation{
	//	public void sayHello();
	public int calculatexx(int x, int y);
	/**
	 * java8 接口也有方法体
	 * 可以定义多个方法有方法体
	 */
	public default int mul(int x, int y) {
		return x*y;
	}

	public default int mul2(int x, int y) {
		return x*y;
	}

	/**
	 * java8 接口也有静态方法体
	 * 可以定义多个静态方法并且有方法体
	 */
	public static int div(int x, int y) {
		return x/y;
	}

	public static int div2(int x, int y) {
		return x/y;
	}
}

/**
 * 实现接口Operation
 */
class Xxx implements Operation{

	@Override
	public int calculatexx(int x, int y) {
		return x+y+x;
	}

}

//普通接口，可以定义多个无方法体的方法
interface Operation2{
	public void sayHello();
	public int calculatexx(int x, int y);

	public default int mul(int x, int y) {
		return x*y;
	}

	public static int div2(int x, int y) {
		return x/y;
	}
}

/**
 *
 * @author bruce
 * 1 函数式编程
 * 2
 * lambdaExpress表达式
 * 1.语法：() -> {}  小括号，写死右箭头，实现方法大括号
 * 2.@FunctionalInterface - 函数式接口
 * 3.default
 *
 */
public class LambdaExpress {
	public static void main(String[] args) {

		/*
		 *
		 * java8之前的调用方式：
		 * Operation ope = new Operation() {
		 *
		 * @Override public void sayHello() { // TODO Auto-generated method stub
		 *
		 * }
		 *
		 * @Override public int calculate(int x, int y) { // TODO Auto-generated method
		 * stub return 0; }
		 *
		 * };
		 */

		//Lambda表达式调用方式：

//		Operation ope2 = () -> {
//			System.out.println("hello");
//		};
//		ope2.sayHello();


		Operation ope3 = (int x, int y) -> {
			return x+y;
		};
		System.out.println(ope3.calculatexx(3, 8));

		//调用默认方法
		ope3.mul(1, 2);
		ope3.mul2(3, 4);

		//调用静态方法
		Operation.div(10, 2);
		Operation.div2(10, 2);

		Xxx xx = new Xxx();
		System.out.println(xx.calculatexx(1, 2));

	}
}
