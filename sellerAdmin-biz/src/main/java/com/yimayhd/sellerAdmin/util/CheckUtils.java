package com.yimayhd.sellerAdmin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式检查工具
 * 
 * @author yebin
 *
 */
public class CheckUtils {
	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			return matcher.matches();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobileNO(String mobiles) {
		try {
			Pattern p = Pattern.compile("^1[0-9]{10}$");
			// Pattern p =
			// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			return m.matches();
		} catch (Exception e) {
			return false;
		}
	}
}
