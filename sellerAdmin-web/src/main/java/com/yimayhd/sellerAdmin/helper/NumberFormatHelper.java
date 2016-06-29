package com.yimayhd.sellerAdmin.helper;

import java.text.DecimalFormat;

public class NumberFormatHelper {

	public static String formatNumber(long d) {
		DecimalFormat df = new DecimalFormat("0.##");
		return df.format(d/100.00);
		
	}
}
