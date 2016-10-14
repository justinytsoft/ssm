package com.FangBianMian.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateFormatter
{
	
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(DATE);
	public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(DATE_TIME);
	
	/**
	 * @param dt
	 * @return
	 */
	public static Date stringToDate(String dt) {
		return stringToDate(dt, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date stringToDate(String dt, String format) {
		if (!StringUtils.isBlank(dt) && !dt.equals("null")) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			try {
				return formatter.parse(dt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param dt
	 * @param formater
	 * @return
	 */
	public static String dateToString(Date dt, String formater) {
		if (StringUtils.isBlank(formater))
			formater = "yyyy-MM-dd HH:mm:ss";
		if (dt != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(formater);
			return formatter.format(dt);
		}
		return null;
	}
	
	/**
	 * 获得一天的开始时间
	 * 
	 * @param date 指定日期
	 * @param cursor 指定日期的前后日期，如cursor为-1，则为date的前一天；为1，则为后一天
	 * @return
	 */
	public static long getStartTimestampInOneday(Date date, int cursor) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cursor != 0)
			cal.add(Calendar.DATE, cursor);
		String dateStr = DateFormatter.dateToString(cal.getTime(), "yyyy-MM-dd");
		return DateFormatter.stringToDate(dateStr + " 0:0:0").getTime();
	}
	
	/**
	 * 获得一天的结束时间
	 * 
	 * @param date 指定日期
	 * @param cursor 指定日期的前后日期，如cursor为-1，则为date的前一天；为1，则为后一天
	 * @return
	 */
	public static long getEndTimestampInOneday(Date date, int cursor) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cursor != 0)
			cal.add(Calendar.DATE, cursor);
		String dateStr = DateFormatter.dateToString(cal.getTime(), "yyyy-MM-dd");
		return DateFormatter.stringToDate(dateStr + " 23:59:59").getTime();
	}
	
	/**
	 * 时间显示格式转换201212151235转为20121215
	 * @param updateTime2
	 * @return
	 */
	public static long updateTime(long updateTime2) {
		
		return updateTime2/10000;
	}
	/**
	 * 二级分类List组合String
	 * @param categorys
	 * @return
	 */
	public static String resourceList(List<String> resource) {
		String categorysAll = "";
		for(int i=0;i<6;i++){
			if(resource.size()>i){
				categorysAll+=	resource.get(i)+"/";
			}
			
		}
		if(categorysAll.length()>0){
			categorysAll=categorysAll.substring(0, categorysAll.length()-1);
		}
		if(resource.size()>5){
			categorysAll+="......";
		}
		return  categorysAll ;
	}

	
	public static String timestampToString(String timestamp, String formater) {
		if (StringUtils.isBlank(timestamp) || timestamp.equals("null"))
			return "";
		if (StringUtils.isBlank(formater))
			formater = "yyyy-MM-dd HH:mm:ss";
		Long tsl = Long.valueOf(timestamp);
		Timestamp ts = new Timestamp(tsl);
		SimpleDateFormat formatter = new SimpleDateFormat(formater);
		return formatter.format(ts);
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
	 * 获取指定分钟前的日期
	 * @param date
	 * @return
	 */
	public static long getPreMinute(Date date, Long minute) {
		minute = minute == null ? 0 : minute;
		long curren = date.getTime();
		curren -= minute * 60 * 1000;
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
	
	/**
	 * 获取指定日期是星期几
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
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
	
	/**
	 * 获取指定日期的本周第一天
	 * @param date
	 * @return
	 */
	public static Date getNowWeekMonday(Date date) {    
		Calendar cal = Calendar.getInstance();    
        cal.setTime(date);    
        cal.add(Calendar.DAY_OF_MONTH, -1); //解决周日会出现 并到下一周的情况    
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);        
        return cal.getTime();    
    }
}
