package com.java.juc;


public class TestTransferValue {
	public void changeValue1(int age) {
		age = 30;
	}
	public void changeValue2(Person person) {
		person.setPersonName("xxx");
	}
	public void changeValue3(String str) {
		str = "xxx";
	}

	/**
	 * 栈-传递的复印件
	 * 堆传递的地址
	 *
	 * 值引用类型：值不变
	 * 对象类型：值变
	 *
	 * 字符串常量池：值有直接复用，没有就重新创建。
	 *
	 */
	public static void main(String[] args) {
		TestTransferValue x = new TestTransferValue();

		int age = 20;
		x.changeValue1(age);
		System.out.println("age=" + age);

		Person person = new Person("abc");
		x.changeValue2(person);
		System.out.println("personName=" + person.getPersonName());

		//字符串常量池：值有直接复用，没有就重新创建。
		String str = "abc";
		x.changeValue3(str);
		System.out.println("str=" + str);//结果：str=abc
	}
}

class Person{

	Person(String personName){
		this.personName = personName;
	}

	private String personName;

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}


}
