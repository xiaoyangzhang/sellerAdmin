package com.yimayhd.sellerAdmin.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class DateUtil {

	// protected static Logger logger =LogManager.getLogger();
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_TIME = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DAY_FORMAT = "yyyy-MM-dd";
	public static final String DAY_HORU_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DAY_DATE_EN_FORMAT = "yyyy年MM月dd日";
	public static final String DEFAULT_HOUR_MINUTE = "HH:mm";
	public static final String DAY_BEGIN = " 00:00:00";
	public static final String DAY_END = " 23:59:59";

	/** the Year field for date math functions of this class */
	@Deprecated
	public static final CalendarField YEAR = CalendarField.YEAR;
	/** the Month field for date math functions of this class */
	@Deprecated
	public static final CalendarField MONTH = CalendarField.MONTH;
	/** the Day field for date math functions of this class */
	@Deprecated
	public static final CalendarField DAY = CalendarField.DAY;
	/** the hour field for date math functions of this class */
	@Deprecated
	public static final CalendarField HOUR = CalendarField.HOUR;
	/** the Minute field for date math functions of this class */
	@Deprecated
	public static final CalendarField MINUTE = CalendarField.MINUTE;
	/** the Second field for date math functions of this class */
	@Deprecated
	public static final CalendarField SECOND = CalendarField.SECOND;
	/** the Millisecond field for date math functions of this class */
	@Deprecated
	public static final CalendarField MILLISECOND = CalendarField.MILLISECOND;

	private static final TimeZone TIME_ZONE_LOCAL = TimeZone.getDefault();

	private static final String[] WEEK = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
	private static final String[] STAR_WEEK = new String[] { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/**
	 * time=0 过滤时间为1970-01-01 情况
	 * 
	 * @param time
	 * @return
	 */
	public static Date longToDate(long time) {
		if (time != 0) {
			Date date = new Date(time);
			return date;
		}
		return null;
	}

	/**
	 * 日期格式化成字符串 输入：日期对象 输出：如：2012-10-11 12:11:13
	 *
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		StringBuffer result = new StringBuffer();
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
			result.append(format.format(date));
		}
		return result.toString();
	}

	/**
	 * 日期格式化成字符串 输入：日期对象 输出：如：2012-10-11
	 *
	 * @param date
	 * @return
	 */
	public static String date2StringByDay(Date date) {
		StringBuffer result = new StringBuffer();
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_TIME);
			result.append(format.format(date));
		}
		return result.toString();
	}

	private static ThreadLocal<Calendar> CAL = new ThreadLocal<Calendar>() {
		@Override
		protected synchronized Calendar initialValue() {
			return Calendar.getInstance();
		}
	};

	private static ThreadLocal<Calendar> CAL2 = new ThreadLocal<Calendar>() {
		@Override
		protected synchronized Calendar initialValue() {
			return Calendar.getInstance();
		}
	};

	// TIME_INT_FORMAT
	private static ThreadLocal<SimpleDateFormat> TIME_INT_FORMAT = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HHmmssSSS");
		}
	};

	// DATE_INT_FORMAT
	private static ThreadLocal<SimpleDateFormat> DATE_INT_FORMAT = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		}
	};

	// SIMPLE_DATE_FORMAT
	private static ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	// ISO_DATE_FORMAT
	private static ThreadLocal<SimpleDateFormat> ISO_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		}
	};

	private static final long START_OF_DAY;
	private static final long END_OF_DAY;

	static {
		START_OF_DAY = clearTime(clearDate(0));
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeZone(TIME_ZONE_LOCAL);
		cal.setTimeInMillis(START_OF_DAY);
		cal.add(Calendar.DATE, 1);
		cal.add(Calendar.SECOND, -1);
		END_OF_DAY = cal.getTimeInMillis();
	}

	/**
	 * All date creation within the application should take advantage of one of
	 * the <code>getNewDate</code> maker methods. This is responsible for
	 * creating a date compatible with timezone differences.
	 *
	 * @param argMillis
	 *            millisecond representation of a time
	 * @return a new Date
	 */
	public static final Date getNewDate(long argMillis) {
		return new Date(argMillis);
	}

	/**
	 * Given a year, and the day of that year, returns a Date
	 *
	 * @param argYear
	 *            the year
	 * @param argDayOfYear
	 *            the day of the year
	 * @return the requested date (time will be midnight local time)
	 */
	public static final Date getJulianDate(int argYear, int argDayOfYear) {

		Calendar cal = CAL.get();

		cal.clear();
		cal.setTimeZone(TIME_ZONE_LOCAL);
		cal.set(Calendar.YEAR, argYear);
		cal.set(Calendar.DAY_OF_YEAR, argDayOfYear);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Changes the time zone to local and removes the time portion of a date.
	 *
	 * @param argDate
	 *            the date to be changed
	 * @return a new date with only the date portion (time will be midnight
	 *         local time)
	 * @throws NullPointerException
	 *             if <tt>argDate</tt> is null
	 */
	public static final Date clearTime(Date argDate) {
		return new Date(clearTime(argDate.getTime()));
	}

	/**
	 * Changes the time zone to local and removes the time portion of a date.
	 *
	 * @param argDate
	 *            the date to be changed
	 * @return a new date with only the date portion (time will be midnight
	 *         local time)
	 */
	public static final long clearTime(long argDate) {

		Calendar cal = CAL.get();
		cal.clear();
		cal.setTimeInMillis(argDate);
		cal.setTimeZone(TIME_ZONE_LOCAL);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * Changes the time zone to local and removes the time portion of a date.
	 *
	 * @param argDate
	 *            the date to be changed
	 * @return a new date with only the date portion (time will be midnight
	 *         local time)
	 */
	public static final Date clearTimeNearest(Date argDate) {
		return new Date(clearTimeNearest(argDate.getTime()));
	}

	/**
	 * Changes the time zone to local and removes the time portion of a date.
	 *
	 * @param argDate
	 *            the date to be changed
	 * @return a new date with only the date portion (time will be midnight
	 *         local time)
	 */
	public static final long clearTimeNearest(long argDate) {

		Calendar cal = CAL.get();
		cal.clear();
		cal.setTimeInMillis(argDate);
		cal.setTimeZone(TIME_ZONE_LOCAL);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (hour >= 12) {
			int newDay = cal.get(Calendar.DAY_OF_YEAR) + 1;
			cal.set(Calendar.DAY_OF_YEAR, newDay);
		}
		return cal.getTimeInMillis();
	}

	/**
	 * Removes the time portion of a date. (Sets the date to January 1, 1970.)
	 *
	 * @param argDate
	 *            the date to be changed
	 * @return a new date with no date component (date will be January 1, 1970.)
	 * @throws NullPointerException
	 *             if <tt>argDate</tt> is null
	 */
	public static final Date clearDate(Date argDate) {
		return new Date(clearDate(argDate.getTime()));
	}

	/**
	 * Removes the time portion of a date. (Sets the date to January 1, 1970.)
	 *
	 * @param argDate
	 *            the date to be changed
	 * @return a new date with no date component (date will be January 1, 1970.)
	 */
	public static final long clearDate(long argDate) {

		Calendar cal = CAL.get();

		cal.clear();
		cal.setTimeZone(TIME_ZONE_LOCAL);
		cal.setTimeInMillis(argDate);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.YEAR, 1970);
		return cal.getTimeInMillis();
	}

	/**
	 * Converts a string into a date.
	 *
	 * @param argDateString
	 *            a date string in the format "yyyy-mm-dd"
	 * @return the date represented by the string
	 */
	public static final Date parseDate(String argDateString) {
		try {
			return SIMPLE_DATE_FORMAT.get().parse(argDateString);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Formats a date in "yyyy-MM-dd" format.
	 *
	 * @param argDate
	 *            a date to format
	 * @return the formatted date string
	 */
	public static final String format(Date argDate) {
		return SIMPLE_DATE_FORMAT.get().format(argDate);
	}

	/**
	 * Converts a string in the format used for expiration dates found in credit
	 * card track data (YYMM) into a java date.
	 *
	 * @param argMsrDateString
	 *            the date string to parse
	 * @return null if there is any problem parsing the date
	 */
	public static final Date parseMsrDate(String argMsrDateString) {
		try {
			int year = Integer.parseInt(argMsrDateString.substring(0, 2));
			int month = Integer.parseInt(argMsrDateString.substring(2, 4));
			return makeExpirationDate(year, month);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Converts a string in the format used for messages to other Datavantage
	 * processes and stored in the database (MMYY) into a java date.
	 *
	 * @param argDateString
	 *            the date string to parse
	 * @return null if there is any problem parsing the date
	 */
	public static final Date parseDtvExpirationDate(String argDateString) {
		try {
			int month = Integer.parseInt(argDateString.substring(0, 2));
			int year = Integer.parseInt(argDateString.substring(2, 4));
			return makeExpirationDate(year, month);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Creates an expiration date for the specified year and month.
	 *
	 * @param argYear
	 *            year of expiration date
	 * @param argMonth
	 *            month of expiration date
	 * @return the date/time correspoding to the last millisecond of the
	 *         specified month and year, or <code>null</code> if
	 *         <code>argMonth</code> is &gt; 12
	 */
	public static final Date makeExpirationDate(int argYear, int argMonth) {
		if (argMonth > 12) {
			return null;
		}

		Calendar cal = CAL.get();

		cal.clear();
		cal.setTimeZone(TIME_ZONE_LOCAL);
		cal.set(Calendar.YEAR, argYear + 2000);
		cal.set(Calendar.MONTH, argMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * Calculates the number of the specified time units between now and
	 * <code>argDate</code>.
	 *
	 * @param argField
	 *            time unit to calculate (one of {@link CalendarField#WEEK},
	 *            {@link CalendarField#DAY}, {@link CalendarField#HOUR},
	 *            {@link CalendarField#MINUTE}, {@link CalendarField#SECOND}, or
	 *            {@link CalendarField#MILLISECOND})
	 * @param argDate
	 *            the date to calculate from now
	 * @return the number of time units
	 */
	public static final long dateDiff(CalendarField argField, Date argDate) {
		return dateDiff(argField, System.currentTimeMillis(), argDate.getTime());
	}

	/**
	 * Calculates the number of the specified time units between now and
	 * <code>argDate</code>.
	 *
	 * @param argField
	 *            time unit to calculate (one of {@link CalendarField#WEEK},
	 *            {@link CalendarField#DAY}, {@link CalendarField#HOUR},
	 *            {@link CalendarField#MINUTE}, {@link CalendarField#SECOND}, or
	 *            {@link CalendarField#MILLISECOND})
	 * @param argDate
	 *            the date to calculate from now
	 * @return the number of time units
	 */
	public static final long dateDiff(CalendarField argField, long argDate) {
		return dateDiff(argField, System.currentTimeMillis(), argDate);
	}

	/**
	 * Calculates the number of the specified time units between
	 * <code>argDate1</code> and <code>argDate2</code>.
	 *
	 * @param argField
	 *            time unit to calculate (one of {@link CalendarField#WEEK},
	 *            {@link CalendarField#DAY}, {@link CalendarField#HOUR},
	 *            {@link CalendarField#MINUTE}, {@link CalendarField#SECOND}, or
	 *            {@link CalendarField#MILLISECOND})
	 * @param argDate1
	 *            the first date
	 * @param argDate2
	 *            the second date
	 * @return the number of time units
	 */
	public static final long dateDiff(CalendarField argField, Date argDate1, Date argDate2) {
		return dateDiff(argField, argDate1.getTime(), argDate2.getTime());
	}

	/**
	 * Calculates the number of the specified time units between
	 * <code>argDate1</code> and <code>argDate2</code>.
	 *
	 * @param argField
	 *            time unit to calculate (one of {@link CalendarField#WEEK},
	 *            {@link CalendarField#DAY}, {@link CalendarField#HOUR},
	 *            {@link CalendarField#MINUTE}, {@link CalendarField#SECOND}, or
	 *            {@link CalendarField#MILLISECOND})
	 * @param argDate1
	 *            the first date
	 * @param argDate2
	 *            the second date
	 * @return the number of time units
	 */
	public static final long dateDiff(CalendarField argField, long argDate1, long argDate2) {
		long diff = argDate1 - argDate2;
		long divisor = 1;
		switch (argField) {
		// unable to reliably support year and month because of
		// variable year and month length ... not needed at this time, so not
		// implementing

		case YEAR:

			/** @todo IMPLEMENT!! */
			throw new UnsupportedOperationException("not yet implemented");

		case MONTH:

			/** @todo IMPLEMENT!! */
			throw new UnsupportedOperationException("not yet implemented");

		case WEEK:

			// 7 days in a week
			divisor *= 7;

		case DAY:

			// 24 hours per day
			divisor *= 24;

		case HOUR:

			// 60 minutes per hour
			divisor *= 60;

		case MINUTE:

			// 60 seconds per minute
			divisor *= 60;

		case SECOND:

			// 1000 milliseconds per second
			divisor *= 1000;

		case MILLISECOND:
			break;

		default:
			throw new IllegalArgumentException();
		}
		BigDecimal d = BigDecimal.valueOf(diff).divide(BigDecimal.valueOf(divisor), 0, BigDecimal.ROUND_HALF_UP);
		return d.longValue();
		// diff /= divisor;
		// return diff;
	}

	/**
	 * Adds the date from <code>argDate</code> to the time from
	 * <code>argTime</code>.
	 *
	 * @param argDate
	 *            the date to add
	 * @param argTime
	 *            the time to add
	 * @return the resultant date/time
	 */
	public static final Date add(Date argDate, Date argTime) {

		Calendar cal = CAL.get();
		Calendar cal2 = CAL2.get();

		cal.clear();
		cal.setTime(argDate);
		cal2.clear();
		cal2.setTime(argTime);
		cal2.setTimeZone(TIME_ZONE_LOCAL);

		matchField(Calendar.HOUR, cal, cal2);
		matchField(Calendar.AM_PM, cal, cal2);
		matchField(Calendar.MINUTE, cal, cal2);
		matchField(Calendar.SECOND, cal, cal2);
		matchField(Calendar.MILLISECOND, cal, cal2);

		return cal.getTime();
	}

	private static final void matchField(int argField, Calendar toCalendar, Calendar fromCalendar) {
		toCalendar.set(argField, fromCalendar.get(argField));
	}

	/**
	 * Adds <code>argAmount</code> time units as specified by
	 * <code>argField</code> to <code>argDate</code>.
	 *
	 * @param argField
	 *            the type of time unit to add (one of
	 *            {@link CalendarField#YEAR}, {@link CalendarField#MONTH},
	 *            {@link CalendarField#DAY}, {@link CalendarField#HOUR},
	 *            {@link CalendarField#MINUTE}, {@link CalendarField#SECOND}, or
	 *            {@link CalendarField#MILLISECOND})
	 * @param argAmount
	 *            the number of time units to add
	 * @param argDate
	 *            the date to add to
	 * @return the resultant date/time
	 */
	public static final Date dateAdd(CalendarField argField, int argAmount, Date argDate) {
		return new Date(dateAdd(argField, argAmount, argDate.getTime()));
	}

	/**
	 * Adds <code>argAmount</code> time units as specified by
	 * <code>argField</code> to <code>argDate</code>.
	 *
	 * @param argField
	 *            the type of time unit to add (one of
	 *            {@link CalendarField#YEAR}, {@link CalendarField#MONTH},
	 *            {@link CalendarField#DAY}, {@link CalendarField#HOUR},
	 *            {@link CalendarField#MINUTE}, {@link CalendarField#SECOND}, or
	 *            {@link CalendarField#MILLISECOND})
	 * @param argAmount
	 *            the number of time units to add
	 * @param argDate
	 *            the date to add to
	 * @return the resultant date/time
	 */
	public static final long dateAdd(CalendarField argField, int argAmount, long argDate) {

		Calendar cal = CAL.get();

		cal.clear();
		cal.setTimeZone(TIME_ZONE_LOCAL);
		cal.setTimeInMillis(argDate);
		cal.add(argField.id, argAmount);
		return cal.getTimeInMillis();
	}

	/**
	 * Adds <code>argAmount</code> time units as specified by
	 * <code>argField</code> to <code>argDate</code>.
	 *
	 * @param argField
	 *            the type of time unit to add (one of {@link Calendar#YEAR},
	 *            {@link Calendar#MONTH}, {@link Calendar#DATE},
	 *            {@link Calendar#HOUR}, {@link Calendar#MINUTE},
	 *            {@link Calendar#SECOND}, or {@link Calendar#MILLISECOND})
	 * @param argAmount
	 *            the number of time units to add
	 * @param argDate
	 *            the date to add to
	 * @return the resultant date/time
	 */
	@Deprecated
	public static final Date dateAdd(int argField, int argAmount, Date argDate) {
		return new Date(dateAdd(argField, argAmount, argDate.getTime()));
	}

	/**
	 * Adds <code>argAmount</code> time units as specified by
	 * <code>argField</code> to <code>argDate</code>.
	 *
	 * @param argField
	 *            the type of time unit to add (one of {@link Calendar#YEAR},
	 *            {@link Calendar#MONTH}, {@link Calendar#DATE},
	 *            {@link Calendar#HOUR}, {@link Calendar#MINUTE},
	 *            {@link Calendar#SECOND}, or {@link Calendar#MILLISECOND})
	 * @param argAmount
	 *            the number of time units to add
	 * @param argDate
	 *            the date to add to
	 * @return the resultant date/time
	 */
	@Deprecated
	public static final long dateAdd(int argField, int argAmount, long argDate) {

		Calendar cal = CAL.get();

		cal.clear();
		cal.setTimeZone(TIME_ZONE_LOCAL);
		cal.setTimeInMillis(argDate);
		cal.add(argField, argAmount);
		return cal.getTimeInMillis();
	}

	/**
	 * Determines if two date times are in the same day.
	 *
	 * @param argDateTime1
	 *            first date
	 * @param argDateTime2
	 *            second date
	 * @return true if the date times are in the same day
	 */
	public static final boolean isSameDay(Date argDateTime1, Date argDateTime2) {
		Date d1 = clearTime(argDateTime1);
		Date d2 = clearTime(argDateTime2);
		return 0 == d1.compareTo(d2);
	}

	private static Calendar getCurrentDateCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal;
	}

	/**
	 * 得到当前时间
	 *
	 * @return
	 */
	public static Date getCurrentDate() {
		Calendar cal = getCurrentDateCalendar();
		return cal.getTime();
	}

	/**
	 * 获取时间HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		if (date == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hh = calendar.get(Calendar.HOUR_OF_DAY);
		int mm = calendar.get(Calendar.MINUTE);
		int ss = calendar.get(Calendar.SECOND);
		return hh + ":" + mm + ":" + ss;
	}

	public static Date getCurrentDate(final String dateFormats) throws Exception {
		Calendar cal = getCurrentDateCalendar();
		Date date = cal.getTime();
		SimpleDateFormat df = null;
		String value = "";
		if (date != null) {
			df = new SimpleDateFormat(dateFormats);
			value = df.format(date);
		}
		return convertStringToDate(value);
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param strDate
	 *            (格式 yyyy/MM)
	 * @return
	 *
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			/*
			 * if (logger.isDebugEnabled()) { logger.debug(
			 * "converting date with pattern: " + DEFAULT_DATE_FORMAT); }
			 */

			aDate = convertStringToDate(DATE_FORMAT_TIME, strDate);
		} catch (ParseException pe) {
			/*
			 * logger.error("Could not convert '" + strDate +
			 * "' to a date, throwing exception");
			 */
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}
		return aDate;
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param aMask
	 *            输入字符串的格式
	 * @param strDate
	 *            一个按aMask格式排列的日期的字符串描述
	 * @return Date 对象
	 * @see SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		/*
		 * if (logger.isDebugEnabled()) { logger.debug("converting '" + strDate
		 * + "' to date with mask '" + aMask + "'"); }
		 */

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * 将字符串用指定的日期格式格式化为日期格式
	 * 
	 * @param dateFormats
	 * @return
	 * @throws Exception
	 */
	public static Date convertStringToDateUseringFormats(final String date, final String dateFormats) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormats);
		Date parsedDate = null;
		try {
			parsedDate = formatter.parse(date);
		} catch (ParseException e) {
			throw new Exception("Cannot format given Object as a Date");
		}
		return parsedDate;
	}

	public static String getDatePattern() {
		return DEFAULT_DATE_FORMAT;
	}

	public static String convertDateToString(Date date) throws Exception {
		String dateStr;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			dateStr = formatter.format(date);
		} catch (Exception e) {
			throw new Exception("date to string error", e);
		}
		return dateStr;
	}

	/**
	 * 将Date类型时间 转换成String 格式的时间
	 * 
	 * @param date
	 * @param fmat
	 * @return
	 */
	public static String dateToString(Date date, String fmat) {
		if (date == null || fmat == null || "".equals(fmat)) {
			return null;
		}
		String result = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(fmat);
			result = format.format(date);
		} catch (Exception e) {
			new Exception("date to string error . format=" + fmat, e);
		}
		return result;
	}

	public static boolean isOverYear() {
		Calendar now = Calendar.getInstance();
		int nowMonth = now.get(Calendar.MONTH) + 1;
		if (nowMonth < 6) {
			return true;
		}
		return false;
	}

	public static Date getEndDate(int startYearInv) { // 小端
		Calendar now = Calendar.getInstance();
		int nowYear = now.get(Calendar.YEAR);
		int nowMonth = now.get(Calendar.MONTH) + 1;
		int startYear;
		int startMonth;
		if (isOverYear()) {
			startYear = (nowYear - startYearInv);
			startMonth = (nowMonth + 12) - 5;
		} else {
			startYear = (nowYear - startYearInv) + 1;
			startMonth = nowMonth - 5;
		}
		DateFormat df = new SimpleDateFormat(DAY_FORMAT);
		try {
			System.out.println(startYear + "-" + startMonth + "-01");
			return df.parse(startYear + "-" + startMonth + "-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getStartDate(int endYearInv) { // 大端
		Calendar now = Calendar.getInstance();
		int nowYear = now.get(Calendar.YEAR);
		int nowMonth = now.get(Calendar.MONTH) + 1;
		int startYear;
		int startMonth;
		if (isOverYear()) {
			startYear = (nowYear - endYearInv) - 1;
			startMonth = (nowMonth + 12) - 5;
		} else {
			startYear = (nowYear - endYearInv);
			startMonth = nowMonth - 5;
		}
		DateFormat df = new SimpleDateFormat(DAY_FORMAT);
		try {
			System.out.println(startYear + "-" + startMonth + "-01");
			return df.parse(startYear + "-" + startMonth + "-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将long 类型 时间 转换成String 类型
	 * 
	 * @param time
	 * @return
	 */
	public static String longToString(long time,String format) {
		if (time >= 0) {
			Date date = new Date(time);
			return dateToString(date, format);
		}
		return null;
	}

	public static String formatDate(Date time) {
		if (time == null) {
			return null;
		}
		return dateToString(time, DATE_TIME_FORMAT);
	}

	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(long birthdayTime) {
		if (birthdayTime <= 0) {
			return 0;
		}
		Date birthday = new Date(birthdayTime);
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	public static int getAgeByBirthday(Date birthdayTime) {
		if (birthdayTime == null) {
			return 0;
		}
		return getAgeByBirthday(birthdayTime.getTime());
	}

	/**
	 * 获取一天的最大时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatMaxTimeForDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date formatMaxTimeForDate(String dataStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
		return format.parse(dataStr + DAY_END);
	}

	/**
	 * 获取一天的最小时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatMinTimeForDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date formatMinTimeForDate(String dataStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
		return format.parse(dataStr + DAY_BEGIN);
	}

	/**
	 * 周*
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String formatWeekOnly(Date date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		return WEEK[c.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 星期*
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String formatStarWeekOnly(Date date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		return STAR_WEEK[c.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 日期加天
	 * @param date 增加天数
	 * @param addDay
	 * @return
	 */
	public static String getDayAgo(Date date,int addDay)throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, addDay);
		return dateToString(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 日期加月
	 * @param date
	 * @param addMonth 增加月数
	 * @return
	 */
	public static String getMonthAgo(Date date,int addMonth)throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,addMonth);
		return dateToString(calendar.getTime(), "yyyy-MM-dd");
	}

	public static Date add23Hours(Date date) {
		if (date == null) {
			return null;
		}
		return new Date(date.getTime() + 24L * 3600L * 1000L - 1);
	}

	/**
	 * 计算日期差
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public static long daySubtraction(String beginDate,String endDate)throws Exception{
		Date begin = DateUtil.formatMinTimeForDate(beginDate);
		Date end = DateUtil.formatMaxTimeForDate(endDate);
		return (end.getTime() - begin.getTime())/(24*60*60*1000);
	}
	public static void main(String[] args) {
		System.out.println(dateToString(new Date(), "yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		System.out.println(calendar.getTime());
		System.out.println(calendar2.getTime());
		System.out.println(calendar.compareTo(calendar2));
	}
}
