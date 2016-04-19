package com.yimayhd.sellerAdmin.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 钱工具
 * 
 * @author yebin
 *
 */
public class MoneyUtil {
	/**
	 * 货币格式化
	 * 
	 * @param money
	 * @return
	 */
	public static String moneyFormat(Object money) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(money);
	}

	/**
	 * 货币格式化
	 * 
	 * @param money
	 * @return
	 */
	public static String moneyFormatInInt(Object money) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(0);
		return numberFormat.format(money);
	}

	public static void main(String[] args) {
		System.out.println(moneyFormat(123123213.0163123));
		System.out.println(moneyFormatInInt(123123213.7163123));
	}

	public static String centToYuanMoneyFormat(long money) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(money * 0.01);
	}
}
