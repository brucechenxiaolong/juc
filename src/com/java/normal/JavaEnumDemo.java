package com.java.normal;

/**
 *	它用于声明一组命名的常数，当一个变量有几种可能的取值时，可以将它定义为枚举类型。
 枚举可以根据Integer、Long、Short或Byte中的任意一种数据类型来创建一种新型变量。
 这种变量能设置为已经定义的一组之中的一个，有效地防止用户提供无效值。该变量可使代码更加清晰，因为它可以描述特定的值。
 */
public class JavaEnumDemo {

	public enum MissionExecutor{
		/**
		 * 侦查兵
		 */
		Scout,
		/**
		 * 步兵
		 */
		Infantry,
		/**
		 * 工兵
		 */
		Sapper,
		/**
		 * 指挥官
		 */
		Commander,
		/**
		 * 清洁工
		 */
		Cleaner
	}


	public static void main(String[] args) {

//		System.out.println(MissionExecutor.Cleaner);
//		//使用
		System.out.println(Color.BLUE.getName());//蓝色
		System.out.println(Color.BLUE.getIndex());//2
		System.out.println(Color.getColorName(1));//红色
		System.out.println(Color.getColorIndex("绿色"));//3
		Color.BLUE.print();//蓝色 : 2

		//枚举类接口实现判断
//		Mission mission = new ScoutMission();
//		switch(mission.getMissionExecutor()){
//			case Scout:
//				System.out.println("1");
//				break;
//			case Commander:
//				System.out.println("2");
//				break;
//			case Sapper:
//				System.out.println("3");
//				break;
//			case Infantry:
//				System.out.println("4");
//				break;
//			case Cleaner:
//				System.out.println("5");
//				break;
//			default:
//				System.out.println("6");
//				break;
//		}


	}
}

interface Mission {

	public enum MissionExecutor{
		/**
		 * 侦查兵
		 */
		Scout,
		/**
		 * 步兵
		 */
		Infantry,
		/**
		 * 工兵
		 */
		Sapper,
		/**
		 * 指挥官
		 */
		Commander,
		/**
		 * 清洁工
		 */
		Cleaner
	}

	/**
	 * 任务执行者
	 * @return
	 */
	public MissionExecutor getMissionExecutor();
}

class ScoutMission implements Mission{

	@Override
	public MissionExecutor getMissionExecutor() {
		return MissionExecutor.Scout;
	}

}

class InfantryMission implements Mission{

	@Override
	public MissionExecutor getMissionExecutor() {
		return MissionExecutor.Infantry;
	}

}

//枚举出颜色
interface FunRea{
	void print();
}

//枚举类可以被接口实现
enum Color implements FunRea{

	RED("红色",1),BLUE("蓝色",2),GREEN("绿色",3),WHITE("白色",4);

	private String name;
	private int index;

	//构造函数
	private Color(String name, int index) {
		this.name = name;
		this.index = index;
	}

	//枚举方法
	public static String getColorName(int index){
		for(Color c : Color.values()){
			if(c.getIndex()==index){
				return c.getName();
			}
		}
		return null;
	}

	//枚举方法
	public static int getColorIndex(String name){
		for(Color c : Color.values()){
			if(c.getName()==name){
				return c.getIndex();
			}
		}
		return -1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void print() {
		System.out.println(this.name+" : "+this.index);
	}
}
