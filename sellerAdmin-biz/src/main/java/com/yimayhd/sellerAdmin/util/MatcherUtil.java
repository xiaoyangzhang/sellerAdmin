package com.yimayhd.sellerAdmin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangdi on 16/9/9.
 */
public class MatcherUtil {

    public static  boolean isRegExpStr(String reg,String matchStr){
        Pattern pattern= Pattern.compile(reg);//中文,大写字母,大写字母+中文
        Matcher matcher = pattern.matcher(matchStr);
        return matcher.matches();
    }
}
