package com.java.normal;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Calculate {

	public static void main(String[] args) {

		//-------------------------------------------------------------------------------
//		Jia jia = new Jia();
//		jia.jiafa();
//		Jian jian = new Jian();
//		jian.jianfa();
//		Cheng cheng = new Cheng();
//		cheng.chengfa();
//		Chu chu = new Chu();
//		chu.chufa();
		//以上代码运算结果：
		//加法运算结果是：99.98999694824218
		//减法运算结果是：99.990005
		//乘法运算结果是：99.99
		//除法运算结果是：Infinity

//		除了乘法之外，其他的运算都没有得到我想要的99.99这个结果，这是为什么呢？
//		经过讲解和查阅资料之后，原来浮点数是不适合精确计算的，原因是计算机里面数的计算都是二进制计算的，我们其实输入的十进制数，有些十进制数转换成二进制是可以精确转换，而有些转换则不是精确转换，得到的是一个最靠近它的数，所以这里面就存在一个误差。
//		另外，如果浮点数不进行计算时，在十进制里面浮点数能正确显示，如果浮点数参与了计算，那么浮点数二进制与十进制间的转换过程就会变得不可而知，变得不可逆。

		//要想使得到的结果精确，可以使用Java DecimalFormat 类，用于格式化十进制数字。
//		DecimalFormat df = new DecimalFormat("#.00");//#.00 表示两位小数 #.0000四位小数 以此类推
//		double pi = 3.1415926;
//		System.out.println(df.format(pi));

		//使用Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。
//		double a = 3.1516;
//		BigDecimal b = new BigDecimal(Double.toString(a));
//		System.out.println(b.doubleValue());


		//-------------------------------------------------------------------------------
		//使用Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。
//		int aa = 10;
//		long bb = 35;
//		float c = 36.95f;
//		double d = 18.04;
//		BigDecimal b1 = new BigDecimal(Double.toString(aa));
//		BigDecimal b2 = new BigDecimal(Double.toString(bb));
//		BigDecimal b3 = new BigDecimal(Double.toString(c));
//		BigDecimal b4 = new BigDecimal(Double.toString(d));
//		double e = b1.add(b2).add(b3).add(b4).doubleValue();
//		System.out.println(e);


//		Chu chu = new Chu();
//		chu.chufaTrue();

		//----------------------------------------------------------------
		Yunsuan ys = new Yunsuan();
		ys.jiafa();
		ys.jianfa();
		ys.chengfa();
		ys.chufa();
		//以上代码运算结果：
//		加法运算结果是：99.99
//		减法运算结果是：99.99
//		乘法运算结果是：99.99
//		除法运算结果是：99.99
	}

}

class Jia {// 加法
	int a = 10;
	long b = 35;
	float c = 36.95f;
	double d = 18.04;

	public void jiafa() {
		double jia = a + b + c + d;
		System.out.println("加法运算结果是：" + jia);
	}
}

class Jian {// 减法

	long a = 300;
	int b = 18;
	float c = 128.7f;
	double d = 53.31;

	public void jianfa() {
		float jian = (float) (a - b - c - (double) d);
		System.out.println("减法运算结果是：" + jian);
	}
}

class Cheng { // 乘法

	int a = 1;
	long b = 1000000;
	float c = 0.000001f;
	double d = 99.99;

	public void chengfa() {
		double cheng = a * b * c * d;
		System.out.println("乘法运算结果是：" + cheng);
	}
}

class Chu {// 除法

	int a = 1000;
	long b = 1000000;
	float c = 0.001f;
	double d = 99.99;

	//	除法得到的结果是Infinity，这是因为整型和整型相除，得到的仍然是整型；a = 100，b = 1000，a/b 应该等于0.001，实际上得到的是0.0，
//	由于0不能做除数，因此得到的值是无效的，因此就需要Java数值之间的强制转换，强制转换是将需要转换的数值转换为需要的类型。
	public void chufa() {
		float chu = (float) ((double) d / (a / b / c));
		System.out.println("除法运算结果是：" + chu);
	}

	public void chufaTrue() {
		float chu = (float)((double)d/((float)a/(float)b/c));
		System.out.println("除法运算结果是：" + chu);
	}
}


class Yunsuan {

	public void jiafa() {
		int a1 = 10;
		long b1 = 35;
		float c1 = 36.95f;
		double d1 = 18.04;

		DecimalFormat form = new DecimalFormat("#.00");
		//保留两位小数
		double jia = a1 + b1 + c1 + d1;
		System.out.println("加法运算结果是：" + form.format(jia));
	}

	public void jianfa() {
		long a2 = 300;
		int b2 = 18;
		float c2 = 128.7f;
		double d2 = 53.31;

		DecimalFormat form = new DecimalFormat("#.00");
		//保留两位小数
		float jian = (float) (a2 - b2 - c2 - (double) d2);
		System.out.println("减法运算结果是：" + form.format(jian));
	}

	public void chengfa() {
		int a3 = 1;
		long b3 = 1000000;
		float c3 = 0.000001f;
		double d3 = 99.99;
		double cheng = a3 * b3 * c3 * d3;
		System.out.println("乘法运算结果是：" + cheng);
	}

	public void chufa() {
		int a4 = 1000;
		long b4 = 1000000;
		float c4 = 0.001f;
		double d4 = 99.99;
		float chu = (float)((double)d4/((float)a4/(float)b4/c4));
		System.out.println("除法运算结果是：" + chu);
	}
}

