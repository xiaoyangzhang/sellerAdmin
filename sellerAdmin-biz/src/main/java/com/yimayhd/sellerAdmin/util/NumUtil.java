package com.yimayhd.sellerAdmin.util;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/10/27.
 */
public class NumUtil {

    /**
     * 金额转换（分转换为元:#0.00）
     * @param money
     * @return
     */
    public static String moneyTransform(long money){
        double dn = money;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(dn/100);
    }
    /**
     * 金额转换（分转换为元#0）
     * @param money
     * @return
     */
    public static long moneyTrans(long money){
        return money/100;
    }

    /**
     * 金额转换（分转换为元:#0.00）
     * @param money
     * @return
     */
    public static double moneyTransformDouble(long money){
        double dn = money;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return Double.parseDouble(decimalFormat.format(dn / 100));
    }

    /**
     * 金额转换（元转分）
     * @param d
     * @return
     */

    public static long round(double d){
        return Math.round(d * 100);
    }
    public static long doubleToLong(double d){
       return Math.round(d * 100);
    }

}
