package com.common;

import java.util.*;
import java.text.*;

public class DateUtils {

	public DateUtils() {
	}

	public static String formatDateString1() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日   HH时mm分ss秒");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	public static String formatDateString2() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	public static String formatDateString3() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd   HH:mm:ss");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	public static String formatDateString4() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	public static String formatDateString5() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	public static String formatDateString6() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd   HH:mm");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	public static String formatDateString7() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	public static String formatDateString8() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	/*------------------------------------------------------------------------*/

	public static Date formatDate1() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日   HH时mm分ss秒");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	public static Date formatDate2() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	public static Date formatDate3() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd   HH:mm:ss");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	public static Date formatDate4() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	public static Date formatDate5() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	public static Date formatDate6() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd   HH:mm");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	public static Date formatDate7() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	public static Date formatDate8() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8); //设置时区   
		Date currentTime_2 = formatter.parse(strDate, pos);
		return currentTime_2;
	}

	/**   
	 *把时间字符串转换成Date变量.   
	 */
	public static Date strToDateTime(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**   
	 *把时间变量转换成String.   
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**   
	 *把时间变量转换成String.   
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**   
	 *得到当前的时间.   
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**   
	 *得到一个时间变量的秒数.   
	 */
	public static long getSecond(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate.getTime();
	}

	/**   
	 *设置一个时间，返回它的秒数.   
	 */
	public static long getDateLongTime(int year, int month, int day) {
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(year, month - 1, day);
		return myCalendar.getTime().getTime();
	}

	/**   
	 *返回距当天day天的date变量.   
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	public static void main(String args[]) {
		dateToStr(new Date());
		System.out.println(formatDateString8());
		System.out.println(getDateLongTime(2004, 12, 23));
		System.out.println(getLastDate(2));
		System.out.println(getNow());
	}
}
