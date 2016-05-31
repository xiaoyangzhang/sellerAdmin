package com.yimayhd.sellerAdmin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by wangdi on 16/5/31.
 */
public class DateCommon {


    public static String toLastFriday(Calendar cl){
        cl.set(Calendar.HOUR, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MINUTE, 0);
        int week = cl.get(Calendar.DAY_OF_WEEK);
        //星期5为6
        int day = cl.get(Calendar.DAY_OF_MONTH);
        day =day - week-1;
        cl.set(Calendar.DAY_OF_MONTH, day);
        Date date = cl.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }
    public static String toThursday(Calendar cl){
        cl.set(Calendar.HOUR, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MINUTE, 0);
        int week = cl.get(Calendar.DAY_OF_WEEK);
        //星期5为6
        int day = cl.get(Calendar.DAY_OF_MONTH);
        day =day + (5-week);
        cl.set(Calendar.DAY_OF_MONTH, day);
        Date date = cl.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    /**
     * 返回毫秒数
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(java.util.Date date){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 日期相加，并按照要求日期格式输出 ,示例：20120305
     * @param date 被操作日期
     * @param days 被加天数
     * @return 标准化操作后日期
     */
    public static String addDate(java.util.Date date,int days){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        calendar.setTimeInMillis(getMillis(date) +  ((long)  days)  *  24  *  3600  *  1000);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 日期相减，并按照要求日期格式输出 ,示例：20120305
     * @param date 被操作日期
     * @param days 被加天数
     * @return 标准化操作后日期
     */
    public static String diffDate(java.util.Date date,int days){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        calendar.setTimeInMillis(getMillis(date) - ((long)  days)  *  24  *  3600  *  1000);
        return simpleDateFormat.format(calendar.getTime());
    }
    /**
     * 日期相减，并按照要求日期格式输出 ,示例：2012-03-05
     * @param date 被操作日期
     * @param days 被加天数
     * @return 标准化操作后日期
     */
    public static String diffDate1(java.util.Date date,int days){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTimeInMillis(getMillis(date) - ((long)  days)  *  24  *  3600  *  1000);
        return simpleDateFormat.format(calendar.getTime());
    }
    /**
     * 规范化日期，规范成yyyyMMdd
     * @param date
     * @return
     */
    public static String getFormatDateString(java.util.Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }
    /**
     * 规范化日期，规范成yyyy-MM-dd HH:mm:ss
     * @param timestamp
     * @return
     */
    public static String timestamp2Datetime(long timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(timestamp * 1000));
    }
    /**
     * 规范化日期，规范成yyyy-MM-dd
     * @param timestamp
     * @return
     */
    public static String timestamp2Date(long timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date(timestamp * 1000));
    }
    /**
     * 规范化日期，规范成yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String Date2DateString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    /**
     * 规范化日期，规范成yyyy-MM-dd
     * @param date
     * @return
     */
    public static String Date2String(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 规范化日期，规范成yyyy-MM
     * @param date
     * @return
     */
    public static String Month2String(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss转成long型数据
     * @param datestr
     * @return
     */
    public static long Date2Timestamp(String datestr){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long timestamp = date.getTime()/1000;
        return timestamp;
    }

    /**
     * yyyy-MM-dd HH:mm:ss转成int型数据
     * @param datestr
     * @return
     */
    public static int Date2TimestampInt(String datestr){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long timestamp = date.getTime()/1000;
        return Integer.parseInt( Long.toString(timestamp));
    }

    /**
     * yyyy-MM-dd 转成long型数据
     * @param datestr
     * @return
     */
    public static long Date3Timestamp(String datestr){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long timestamp = date.getTime()/1000;
        return timestamp;
    }
    /**
     *获得当前时间
     * @return
     */
    public static String getCurrentDatetime(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }


    /**
     * 获得当前日期
     * @return
     */
    public static String getCurrentFormatDate2(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    /**
     *
     * @param date
     * @return
     */
    public static String convertMysqlTimestampToDatetime(Date date) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    /**
     * 获得当前时间秒数
     * @return
     */
    public static int getCurrentTimeStamp(){
        String timeStamp = Long.toString(System.currentTimeMillis()/1000);
        return Integer.parseInt(timeStamp);
    }

    /**
     * 剩余时间秒数值，依据设定规则换算，如大于24小时，换算成天；如小于24小时，换算成小时；如小于1小时，换算成分钟；如小于1分钟，换算成秒。
     * @return
     */
    public static String getRemainDayOrHour(int second) {
        int oneDay = 24 * 60 * 60;
        int oneHour = 60 * 60;
        int oneMin = 60;
        int oneSec = 1;
        String remainDayOrHour = "";
        if(second >= oneDay){
            remainDayOrHour = (second/oneDay + 1) + "天";
        }else{
            if(second >= oneHour){
                remainDayOrHour = (second/oneHour + 1) + "小时";
            }else if(second >= oneMin){
                remainDayOrHour = (second/oneMin) + "分钟";
            }else if(second >= oneSec){
                remainDayOrHour = (second) + "秒";
            }
        }
        return remainDayOrHour;
    }

    public static boolean nowIsMiddleTimes(String startTime ,String endTime){
        int StartIntTime = Date2TimestampInt(startTime);
        int EndIntTime = Date2TimestampInt(endTime);
        int nowIntTime = getCurrentTimeStamp();
        return StartIntTime < nowIntTime && nowIntTime < EndIntTime;

    }

    /**
     * 规范化日期，规范成yyyy-MM-dd
     * @param timestamp
     * @return
     */
    public static String timestampLongDate(long timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date(timestamp));
    }

    public static void main(String[] args) {
        System.out.println("getCurrentTimeStamp();-----" + getCurrentTimeStamp());
    }

}
