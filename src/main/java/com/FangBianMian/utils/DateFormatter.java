package com.FangBianMian.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter
{
	
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(DATE);
	public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(DATE_TIME);
	
	/**
	 * 通过传入的日期格式  解析 字符串
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseByStr(String date, String format) throws ParseException{
		return new SimpleDateFormat(format).parse(date);
	}
	
	/**
	 * 通过传入的日期格式  格式化 日期
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatByStr(Date date, String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 将传入日期格式化为  yyyy-MM-dd 的字符串
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return FORMAT_DATE.format(date);
	}

	/**
	 * 将传入日期格式化为  yyyy-MM-dd HH:mm:ss 的字符串
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date){
		return FORMAT_DATE_TIME.format(date);
	}
	
	/**
	 * 将传入的字符串解析为 yyyy-MM-dd 的日期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(String date) throws ParseException{
		return FORMAT_DATE.parse(date);
	}

	/**
	 * 将传入的字符串解析为 yyyy-MM-dd HH:mm:ss 的日期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDateTime(String date) throws ParseException{
		return FORMAT_DATE_TIME.parse(date);
	}
}
