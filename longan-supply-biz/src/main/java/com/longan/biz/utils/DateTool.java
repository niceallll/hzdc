package com.longan.biz.utils;

import com.longan.biz.dataobject.BizOrderQuery;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
    // 默认转换出为2005-09-08类型的当前日
    public static String getToday() {
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	return sdf.format(dt);
    }

    public static int getTodayYearMonth() {
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	return Integer.parseInt(sdf.format(dt));
    }

    public static String parseDates(Date dt) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return sdf.format(dt);
    }

    public static String parseDates2(Date dt) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	return sdf.format(dt);
    }

    public static String parseDates3(Date dt) {
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	return sdf.format(dt);
    }

    public static String parseDate(Date dt) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return sdf.format(dt);
    }

    public static String parseDate8(Date dt) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	return sdf.format(dt);
    }

    public static Date strintToDate(String ds) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date d = sdf.parse(ds);
	return d;
    }

    public static Date strintToDate8(String ds) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	Date d = sdf.parse(ds);
	return d;
    }

    public static Date strintTotime() throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	Date d = null;
	d = sdf.parse(DateTool.getTime("HH:mm:ss"));
	return d;
    }

    public static Date strintToDatetime(String ds) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date d = sdf.parse(ds);
	return d;
    }

    public static Date strintToDatetime2(String ds) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	Date d = sdf.parse(ds);
	return d;
    }

    /**
     * 日期字符串重新格式化
     * 
     * @param ds
     * @param
     * @return
     * @throws ParseException
     */
    public static String strSwapFormat(String ds, String oldFormat, String newFormat) throws ParseException {
	SimpleDateFormat osdf = new SimpleDateFormat(oldFormat);
	Date d = osdf.parse(ds);
	SimpleDateFormat nsdf = new SimpleDateFormat(newFormat);
	return nsdf.format(d);
    }

    // 根据传入的type进行转换日期，type必须遵循Date转换的规则
    public static String getTime(String type) {
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat(type);
	return sdf.format(dt);
    }

    public static int getDay(String d) {
	int day = 1;
	if (d.length() >= 10) {
	    day = Integer.parseInt(d.substring(8, 10));
	}
	return day;
    }

    public static int getMonth(String d) {
	int m = 1;
	if (d.length() >= 10) {
	    m = Integer.parseInt(d.substring(5, 7));
	}
	return m;
    }

    public static int getYear(String d) {
	int y = 1;
	if (d.length() >= 10) {
	    y = Integer.parseInt(d.substring(0, 4));
	}
	return y;
    }

    // 根据传入的两个日期计算相差分钟
    public static int getMinuteBetween(Calendar c1, Calendar c2) {
	int iReturn = 0;
	if (c1 != null && c2 != null) {
	    iReturn = (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (60 * 1000));
	}
	return iReturn;
    }

    // 根据传入的两个日期计算相差天数
    public static int getDayBetween(Calendar c1, Calendar c2) {
	int iReturn = 0;
	if (c1 != null && c2 != null) {
	    iReturn = (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 60 * 60 * 1000));
	}
	return iReturn;
    }

    // 根据传入的两个日期计算相差年数
    public static int getYearBetween(Calendar c1, Calendar c2) {
	int iReturn = 0;
	if (c1 != null && c2 != null) {
	    iReturn = (int) (getDayBetween(c1, c2) / 365);
	}
	return iReturn;
    }

    /**
     * 把2005-02-5这样的日期格式转化为Calendar类型
     * 
     * @param stringTo
     * @return
     * @throws ParseException
     */
    public static Calendar stringToCalendar(String stringTo) throws ParseException {
	Calendar c = Calendar.getInstance();
	if (stringTo != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date d = sdf.parse(stringTo);
	    c.setTime(d);
	}
	return c;
    }

    public static String getWeekOfYear(String s) throws ParseException {
	Calendar c = DateTool.stringToCalendar(s);
	return String.valueOf(c.get(Calendar.WEEK_OF_YEAR));

    }

    public static String getNow() {
	return DateTool.getTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getNow14() {
	return DateTool.getTime("yyyyMMdd HH:mm:ss");
    }

    public static String getNowTime() {
	return DateTool.getTime("HH:mm:ss");
    }

    public static Date afterSecond(Date date, Integer second) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.SECOND, second);
	return c.getTime();
    }

    public static Date afterMinute(Date date, Integer minute) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.MINUTE, minute);
	return c.getTime();
    }

    public static Date afterMonth(Date date, Integer month) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.MONTH, month);
	return c.getTime();
    }

    public static Date afterDay(Date date, Integer day) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.DATE, +day);
	return c.getTime();

    }

    public static Date beforeMonth(Date date, Integer month) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.MONTH, -month);
	return c.getTime();
    }

    public static Date beforeDay(Date date, Integer day) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.DATE, -day);
	return c.getTime();
    }

    public static Date beforeSeconds(Date date, Integer seconds) {
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.SECOND, -seconds);
	return c.getTime();
    }

    private static final long CRITICAL_MIN = 60;
    private static final long CRITICAL_HOUR = 60 * 60;
    private static final long CRITICAL_DAY = 60 * 60 * 24;

    public static String secondToDate(Integer second) {
	String result = null;
	Integer min = second / 60;
	Integer sec = second % 60;
	if (second >= CRITICAL_DAY) {
	    Integer hour = min / 60;
	    Integer day = hour / 24;
	    hour = hour % 24;
	    result = day + "天" + hour + "小时";
	} else if (second >= CRITICAL_HOUR) {
	    Integer hour = min / 60;
	    min = min % 60;
	    result = hour + "小时" + min + "分";
	} else if (second >= CRITICAL_MIN) {
	    result = min + "分" + sec + "秒";
	} else {
	    result = sec + "秒";
	}
	return result;
    }

    public static Date formatStartDate(Date date) {
	if (date == null) {
	    return null;
	}
	//修改了逻辑
	try {
		String[] splitTime = parseDate(date).split(" ");
		if (!splitTime[1].equals("00:00:00")) {
			return date;
		}else{
			return strintToDatetime(parseDate(date) + " 00:00:00");//这个原代码的拼接可能又问题
		}

	} catch (Exception ex) {
	    return null;
	}
    }
    //重载时间拼接
	public static Date formatStartDate(Date date,String hour,String minute,String second) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			return simpleDateFormat.parse(simpleDateFormat1.format(date)+" "+hour+":"+minute+":"+second);
		} catch (Exception ex) {
			return null;
		}
	}

    public static Date formateEndDate(Date date) {
	if (date == null) {
	    return null;
	}
	try {
		String[] splitTime = parseDate(date).split(" ");
		if (splitTime[1].equals("00:00:00")) {
			return strintToDatetime(splitTime[0] + " 23:59:59");
		}else{
			System.out.println("--------------");
			return date;
		}

	} catch (Exception ex) {
	    return null;
	}
    }
    public static BizOrderQuery dateFilter(BizOrderQuery bizOrderQuery){
		if (StringUtils.hasText(bizOrderQuery.getStartHour())||StringUtils.hasText(bizOrderQuery.getEndHour())||StringUtils.hasText(bizOrderQuery.getStartMinute())||StringUtils.hasText(bizOrderQuery.getEndMinute())||StringUtils.hasText(bizOrderQuery.getStartSecond())||StringUtils.hasText(bizOrderQuery.getEndSecond())) {
			if (!StringUtils.hasText(bizOrderQuery.getStartHour())) {
				bizOrderQuery.setStartHour(Constants.constTime);
			}
			if (!StringUtils.hasText(bizOrderQuery.getEndHour())) {
				bizOrderQuery.setEndHour(Constants.constTime);
			}
			if (!StringUtils.hasText(bizOrderQuery.getStartMinute())) {
				bizOrderQuery.setStartMinute(Constants.constTime);
			}
			if (!StringUtils.hasText(bizOrderQuery.getEndMinute())) {
				bizOrderQuery.setEndMinute(Constants.constTime);
			}
			if (!StringUtils.hasText(bizOrderQuery.getStartSecond())) {
				bizOrderQuery.setStartSecond(Constants.constTime);
			}
			if (!StringUtils.hasText(bizOrderQuery.getEndSecond())) {
				bizOrderQuery.setEndSecond(Constants.constTime);
			}
			//存新值
			bizOrderQuery.setStartGmtCreate(DateTool.formatStartDate(bizOrderQuery.getStartGmtCreate(),bizOrderQuery.getStartHour(),bizOrderQuery.getStartMinute(),bizOrderQuery.getStartSecond()));
			bizOrderQuery.setEndGmtCreate(DateTool.formatStartDate(bizOrderQuery.getEndGmtCreate(),bizOrderQuery.getEndHour(),bizOrderQuery.getEndMinute(),bizOrderQuery.getEndSecond()));
		}
		return bizOrderQuery;
	}
}