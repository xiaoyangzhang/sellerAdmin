package com.yimayhd.sellerAdmin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/27.
 */
public class DateFormat {
    /**
     * 开始日期格式化为"yyyy-MM-dd 00-00-00"
     * @param date
     * @return
     * @throws ParseException
     */
    public Date begindDateFormat(String date) throws ParseException {
        //java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.text.DateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return dfNew.parse(date + " 00-00-00");
    }
    /**
     * 结束格式化为"yyyy-MM-dd 23-59-59"
     * @param date
     * @return
     * @throws ParseException
     */
    public Date endDateFormat(Date date) throws ParseException {
        //java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.text.DateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return dfNew.parse(date + " 23-59-59");
    }
}
