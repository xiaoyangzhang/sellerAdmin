package com.yimayhd.sellerAdmin.util;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.sellerAdmin.constant.Constant;

/**
 * 
 * @author wzf
 *
 */
public class ImageUtil {
	public static String getImgUrl(String url){
		return getImgUrl(url, 0) ;
	}
	public static String getImgUrl(String url, int width){
		if( StringUtils.isBlank(url) ){
			return null;
		}
		int index = url.lastIndexOf(Constant.DOT) ;
		StringBuilder sb = new StringBuilder() ;
		if( index > 0 && width > 0){
			sb.append(url.substring(0, index))
			.append("_").append(width).append("x").append(width)
			.append(url.substring(index));
			return sb.toString() ;
		}
		return url ;
	}
	
}
