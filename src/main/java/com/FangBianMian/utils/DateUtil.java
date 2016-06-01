package com.FangBianMian.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	 * 计算d1 到 d2 相差多少时间
	 * @param d1 未来的时间
	 * @param d2 现在的时间
	 * @return 数组下标 0 天 1 时 2 分 3 秒
	 */
	public static long[] dateDiff(Date d1, Date d2) throws ParseException {
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		//获得两个时间的毫秒时间差异
		long diff = d1.getTime() - d2.getTime();
		long day = diff/nd;//计算差多少天
		long hour = diff%nd/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		long sec = diff%nd%nh%nm/ns;//计算差多少秒
		return new long[]{day,hour,min,sec};
	}
	
	/**
	 * 判断 start 是否大于 end
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException 
	 */
	public static boolean startThanEnd(Date start, Date end) throws ParseException{
		long[] result = dateDiff(start, end);
		if(result[0]>0 || result[1]>0 || result[2]>0 || result[3]>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取指定分钟后的日期
	 * @param date
	 * @return
	 */
	public static long getMinute(Date date, Long minute) {
		minute = minute == null ? 0 : minute;
		long curren = date.getTime();
		curren += minute * 60 * 1000;
		return curren;
	}
	
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
	
	/**
	 * 获取指定日期指定天数后的日期
	 * @param date 指定的时间
	 * @param later 指定的天数
	 * @return
	 */
	public static Date getLaterDay(Date date, Long later){
		later = later == null ? 0 : later;
		long current = date.getTime();
		return new Date(current + later * 24 * 60 * 60 * 1000);
	}

	
	/**
	 * 获取指定日期指定天数前的日期
	 * @param date 指定的时间
	 * @param later 指定的天数
	 * @return
	 */
	public static Date getPreviouslyDay(Date date, Long later){
		later = later == null ? 0 : later;
		long current = date.getTime();
		return new Date(current - later * 24 * 60 * 60 * 1000);
	}

	/**
	 * 获取指定日期指定小时后的日期
	 * @param date 指定的时间
	 * @param later 指定的小时
	 * @return
	 */
	public static Date getLaterHour(Date date, Long later){
		later = later == null ? 0 : later;
		long current = date.getTime();
		return new Date(current + later * 60 * 60 * 1000);
	}

	/**
	 * 获取指定日期指定小时前的日期
	 * @param date 指定的时间
	 * @param later 指定的小时
	 * @return
	 */
	public static Date getPreviouslyHour(Date date, Long later){
		later = later == null ? 0 : later;
		long current = date.getTime();
		return new Date(current - later * 60 * 60 * 1000);
	}

}
