package com.yimayhd.sellerAdmin.util;

import com.yimayhd.sellerAdmin.constant.Constant;

/**
 * Created by Administrator on 2015/12/9.
 */
public class PhoneUtil {
	
	public static String getMobile(String mobile){
		if( mobile == null ){
			return null;
		}
		boolean contain = mobile.contains(Constant.MOBILE_PRE);
		if( contain ){
			mobile = mobile.substring(Constant.MOBILE_PRE.length());
		}
		return mobile ;
	}
	
    /**
     * 电话去掉+86
     * @param phone
     * @return
     */
    public static String phoneFormat(String phone){
        if(null == phone){
            return phone;
        }
        if(-1 != phone.indexOf("+86")){
            return phone.substring(3);
        }
        return phone;
    }
    //手机号5-8位加星号
    public static String mask(String mobile){
        if(mobile==null ||  ! (mobile.trim().length()>0 )){
            return mobile;
        }
        String prefix=mobile.substring(0,3);
        String suffix =mobile.substring(mobile.length()-4,mobile.length());
        return prefix+"****"+ suffix;
    }
}
