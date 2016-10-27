package com.FangBianMian.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtil {
	
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
	
	/**
	  * 得到本周周一
	  * @return 
	  */
	 public static Date getMondayOfWeek() {
		 Calendar c = Calendar.getInstance();
		 int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		 if (day_of_week == 0){
			 day_of_week = 7;
		 }
		 c.add(Calendar.DATE, -day_of_week + 1);
		 return c.getTime();
	 }

	 /**
	  * 得到本周周日
	  * @return 
	  */
	 public static Date getSundayOfWeek() {
		 Calendar c = Calendar.getInstance();
		 int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		 if (day_of_week == 0){
			 day_of_week = 7;
	 	}
		 c.add(Calendar.DATE, -day_of_week + 7);
		 return c.getTime();
	 }
	 
	 /**
	 * 获取当前月的第一天
	 * @return
	 */
	public static Date getFirstDayOfMonth(){
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return c.getTime();
	}

	/**
	 * 获取当前月的最后一天
	 * @return
	 */
	public static Date getLastDayOfMonth(){
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return ca.getTime();
	}
	
	/**
	 * 获取指定日期是星期几
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  //一周第一天是否为星期天
		  boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
		  //获取周几
		  int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		  //若一周第一天为星期天，则-1
		  if (isFirstSunday) {
		  		weekDay = weekDay - 1;
			  	if (weekDay == 0) {
			  		weekDay = 7;
			  	}
		  }
		  return weekDay;
	}
}
