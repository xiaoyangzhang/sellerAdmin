package com.yimayhd.sellerAdmin.helper;

import org.apache.commons.lang3.StringUtils;

public class UrlHelper {
	
	public static String getUrl(boolean redirect, String root, String url){
		if( StringUtils.isBlank(root) || StringUtils.isBlank(url) ){
			return null;
		}
		if( redirect ){
			return "redirect:" + root+url ;
		}else{
			return root+url ;
		}
	}
	
	
	public static String getUrl(String root, String url){
		return getUrl(false, root, url) ;
	}
}
