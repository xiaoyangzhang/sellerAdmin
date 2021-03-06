package com.yimayhd.sellerAdmin.util;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

public class TextUtil {
	/**
	 * 转为JSON字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toJSONString(Object obj) {
		return JSON.toJSONString(obj);
	}


	public static String getPicFirst(String picUrls){
		if(StringUtils.isNotBlank(picUrls)){
			String[] arr = picUrls.split("\\|");
			return arr[0];
		}
		return "";
	}
}
