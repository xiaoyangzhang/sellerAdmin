package com.yimayhd.sellerAdmin.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static List<Long> stringTOList(String str){
		if( StringUtils.isBlank(str) ){
			return null;
		}
		String[] split = str.split(",");
		List<Long> list = new ArrayList<Long>();
		int length = split.length;
		for(int i = 0; i < length; i++){
			list.add(Long.parseLong(split[i]));
		}
		return list;
	}
	
	public static void main(String args[]){
		List<Long> toList = StringUtil.stringTOList("1234564897,454812021,");
	}
}
