package com.FangBianMian.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtil {
	
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(DATE);
	public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(DATE_TIME);

	public static String fromNowToDate(Date date) {
		Date now = new Date();
		Long time = now.getTime() - date.getTime();
		time = time/1000;
		Long m = time/60;
		Long h = m/60;
		Long d = h/24;
		if(d==0){
			if(h==0){
				return m+"分钟以前发布";
			} else {
				return h+"小时以前发布";
			}
		} else if(d==1){
			return "昨天发布";
		} else {
			return new DateTime(date).toString("yyyy-MM-dd");
		}
	}
	
	/**
	 * 计算d1 到 d2 相差多少天
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int mistiming(Date d1, Date d2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);
		
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		
		if(year1!=year2){
			int timeDistance = 0;
			for(int i=year1; i < year2; i++){
				if(i%4==0 && i%100!=0 || i%400==0){
					timeDistance += 366;
				}else{
					timeDistance += 365;
				}
			}
			return (timeDistance+(day2-day1));
		}else{
			return (day2-day1);
		}
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
	
	/**
	 * 获取指定日期指定天数后的日期
	 * @param date 指定的时间
	 * @param later 指定的天数
	 * @return
	 * 1460088845588
		1458299478292
	 */
	public static Date getLaterDay(Date date, long later){
		long current = date.getTime();
		return new Date(current + later * 24 * 60 * 60 * 1000);
	}

	/**
	 * 获取指定日期指定天数前的日期
	 * @param date 指定的时间
	 * @param later 指定的天数
	 * @return
	 */
	public static Date getPreviouslyDay(Date date, long later){
		long current = date.getTime();
		return new Date(current - later * 24 * 60 * 60 * 1000);
	}
	
	/**
	 * 获取指定日期指定小时后的日期
	 * @param date 指定的时间
	 * @param later 指定的小时
	 * @return
	 */
	public static Date getLaterHour(Date date, long later){
		long current = date.getTime();
		return new Date(current + later * 60 * 60 * 1000);
	}

	/**
	 * 获取指定日期指定小时前的日期
	 * @param date 指定的时间
	 * @param later 指定的小时
	 * @return
	 */
	public static Date getPreviouslyHour(Date date, long later){
		long current = date.getTime();
		return new Date(current - later * 60 * 60 * 1000);
	}
}
