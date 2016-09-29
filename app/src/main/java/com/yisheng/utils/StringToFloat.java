package com.yisheng.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class StringToFloat {
	public static String StrFloat(String str){
		float s=(float)Double.parseDouble(str);
		DecimalFormat df=new DecimalFormat("0.00");
		String sdf=df.format(s);
		return sdf;
	}
	public static String StrFloat(double s){
		DecimalFormat df=new DecimalFormat("0.00");
		String sdf=df.format(s);
		return sdf;
	}
	//格式化金额,万元
	public static int parseDoubleMoney(String str){
		int s=Integer.parseInt(str);
		s=s/1000;
		return s;
	}
	//格式化千分位金额（带小数）
	public static String parseMoney(String str){
		double s=Double.parseDouble(str);
		DecimalFormat myformat1 = new DecimalFormat("###,##0.00");//使用系统默认的格式
		return myformat1.format(s);
	}
	//格式化千分位金额
	public static String parseFloatMoney(String str){
		double s=Double.parseDouble(str);
		DecimalFormat myformat1 = new DecimalFormat("###,###");//使用系统默认的格式
		return myformat1.format(s);
	}
	//使用BigDecimal处理精确的Double类型的的运算，减法运算
	 public static double sub(double v1, double v2) {
	       BigDecimal b1 = new BigDecimal(Double.toString(v1));
	       BigDecimal b2 = new BigDecimal(Double.toString(v2));
	       return b1.subtract(b2).doubleValue();
	 }
	 public static double sub(double v1, double v2,double v3) {
		 BigDecimal b1 = new BigDecimal(Double.toString(v1));
		 BigDecimal b2 = new BigDecimal(Double.toString(v2));
		 BigDecimal b3 = new BigDecimal(Double.toString(v3));
		 return b1.subtract(b2).subtract(b3).doubleValue();
	 }
}
