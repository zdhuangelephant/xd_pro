package com.xiaodou.mooccrawler.util;

public class NumberUtil {
	public static String getString(long num) {
		String[] digit = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] chArr = Long.toString(num).toCharArray();
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < chArr.length; i++) {
			String s = digit[Integer.parseInt(String.valueOf(chArr[i]))];
			sb.append(s);
		}
		String str = sb.toString();
		return str;
	}
}