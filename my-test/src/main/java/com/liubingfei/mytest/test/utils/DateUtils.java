package com.sitech.smartcity.util.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * 时间处理函数
 */
public class DateUtils {
	

	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String HHMMSS = "HH:mm:ss";
	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	public static final String YYYYMMDD24HHMISS = "yyyyMMddHHmmss";
	public static final String YYYYMMDDTHHMMSSSZ = "yyyy-MM-dd'T'HH:mm:SSS'Z'";
	
	public static final String YYYY_MM_DD_PATTERN = "yyyy-MM-dd";

	public static String getDate(int interval, Date starttime, String pattern) {
		Calendar temp = Calendar.getInstance(TimeZone.getDefault());
		temp.setTime(starttime);
		temp.add(Calendar.MONTH, interval);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(temp.getTime());
	}

	/**
	 * 将当前日期date - days 进行计算，返回计算后日期
	 * 
	 * @return
	 */
	public static String getBeforeDate(Date date, int days) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
		return df.format(calendar.getTime());
	}

	/**
	 * 将当前日期date + days 进行计算，返回计算后日期
	 * 
	 * @return
	 */
	public static String getAfterDate(Date date, int days) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
		return df.format(calendar.getTime());
	}

	/**
	 * 将字符串类型转换为时间类型
	 * 
	 * @return
	 */
	public static Date str2Date(String str) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		try {
			d = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
			d = null;
			return d;
		}
		return d;
	}

	public static Date str2Date(String str, String pattern) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			d = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	public static String date2Str(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		return sdf.format(date);
	}

	public static String date2Str(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取昨天
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDate(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);

		calendar.add(Calendar.DATE, -1);

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 获取上周第一天（周一）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastWeekStart(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int startnum = 0;
		if (i == 0) {
			startnum = 7 + 6;
		} else {
			startnum = 7 + i - 1;
		}
		calendar.add(Calendar.DATE, -startnum);

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 获取上周最后一天（周末）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int endnum = 0;
		if (i == 0) {
			endnum = 7;
		} else {
			endnum = i;
		}
		calendar.add(Calendar.DATE, -(endnum - 1));

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 对时间的加减，注意：返回改变后的Date对象
	 * 
	 * @param date
	 * @param timeUnit
	 * @param value
	 * @return Date
	 */
	public static Date modifyDate(Date date, TimeUnit timeUnit, int value) {
		if (date == null || timeUnit == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (timeUnit) {
		case SECONDS:
			calendar.add(Calendar.SECOND, value);
			break;
		case MINUTES:
			calendar.add(Calendar.MINUTE, value);
			break;
		case HOURS:
			calendar.add(Calendar.HOUR_OF_DAY, value);
			break;
		case DAYS:
			calendar.add(Calendar.DAY_OF_YEAR, value);
			break;
		case MILLISECONDS:
			calendar.add(Calendar.MILLISECOND, value);
			break;
		default:
			break;
		}
		return calendar.getTime();
	}

	/**
	 * 当前时间（字符串格式：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return
	 */
	public static String currentTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}

	public static String currentSimpleTime() {
		SimpleDateFormat sf = new SimpleDateFormat(YYYYMMDDHHMMSSSSS);
		return sf.format(new Date());
	}
	
	public static void main(String args[]) {
		System.out.println(currentSimpleTime());
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static long secondsBetween(Date smdate, Date bdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_secs = (time2 - time1) / 1000;

		return between_secs;
	}

	public static long milisecBetween(Date smdate, Date bdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		return cal.getTimeInMillis() - time1;
	}

	/**
	 * Return a yyyyMMddHHmmssSSS format string of a date with time stamp of
	 * {@code miliSeconds}
	 * 
	 * @param miliSeconds
	 * @return
	 */
	public static String formatDate(long miliSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(miliSeconds);
		return date2Str(cal.getTime(), YYYYMMDDHHMMSSSSS);
	}

	/**
	 * @param miliSeconds
	 * @param format
	 * @return
	 */
	public static String formatDate(long miliSeconds, String format) {
		if (StringUtils.isEmpty(format))
			return formatDate(miliSeconds);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(miliSeconds);
		return date2Str(cal.getTime(), format);
	}

	public static int getMonth(Date start, Date end) {
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}

	/**
	 * 将1s、1m、1h、1d这样的字符串转换成秒单位，转换失败返回0
	 * 
	 * @param timeString
	 * @return long
	 */
	public static int parseToSeconds(String timeString) {
		if (timeString == null) {
			return NumberUtils.INTEGER_ZERO;
		}
		String time = timeString.trim().toUpperCase();
		if (StringUtils.endsWith(time, "S")) {
			time = StringUtils.removeEnd(time, "S");
			return NumberUtils.toInt(time);
		}
		if (StringUtils.endsWith(timeString, "M")) {
			time = StringUtils.removeEnd(time, "M");
			return NumberUtils.toInt(time) * 60;
		}
		if (StringUtils.endsWith(timeString, "H")) {
			time = StringUtils.removeEnd(time, "H");
			return NumberUtils.toInt(time) * 60 * 60;
		}
		if (StringUtils.endsWith(timeString, "D")) {
			time = StringUtils.removeEnd(time, "D");
			return NumberUtils.toInt(time) * 60 * 60 * 24;
		}
		return NumberUtils.INTEGER_ZERO;
	}

	/**
	 * 获取当天的开始时间，精确到毫秒
	 */
	public static Date getStartOfDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");
		String str = format.format(date);
		return str2Date(str, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取当天的结束时间，精确到毫秒
	 */
	public static Date getEndOfDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59.999");
		String str = format.format(date);
		return str2Date(str, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取当月的开始时间，精确到毫秒
	 */
	public static Date getStartOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当月的结束时间，精确到毫秒
	 */
	public static Date getEndOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, days);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 判断时间是否为当天
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取当前时间 到半夜十二点（也就是第二天凌晨）的秒数
	 */
	public static long getTimeToSendDay(){
		Date currentTime = new Date();
		LocalDateTime localDateTime=LocalDateTime.ofInstant(currentTime.toInstant(), ZoneId.systemDefault());
		//获取第第二天零点时刻的实例
		LocalDateTime toromorrowTime=LocalDateTime.ofInstant(currentTime.toInstant(), ZoneId.systemDefault())
				.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		//ChronoUnit日期枚举类,between方法计算两个时间对象之间的时间量
		long seconds = ChronoUnit.SECONDS.between(localDateTime, toromorrowTime);
		return seconds;
	}
}
