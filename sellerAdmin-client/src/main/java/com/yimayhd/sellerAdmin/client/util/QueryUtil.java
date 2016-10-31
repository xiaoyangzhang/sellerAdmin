package com.yimayhd.sellerAdmin.client.util;


public class QueryUtil {
	public static String getQueryLikeParam(String param){
		if( param == null ){
			return param ;
		}
		if( param.contains("%") || param.contains("_") ){
			param = param.replaceAll("%", "/%").replaceAll("_", "/_");
		}
		return param ;
	}
}
