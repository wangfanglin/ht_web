package com.jeesite.modules.common;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @功能 日期工具类
 */
public class DateUtil {

	public static final String DATA_PATTERN = "yyyy-MM-dd";

	public static final String TIME_PATTERN ="yyyy-MM-dd HH:mm:ss";

	private static final ThreadLocal<DateFormat> TIME_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(TIME_PATTERN));

	private static final ThreadLocal<DateFormat> DATA_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(DATA_PATTERN));

	public static String nowTime() {
		try {
			return TIME_LOCAL.get().format(new Date());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static String nowDate() {
		try {
			return DATA_LOCAL.get().format(new Date());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

    /**
     *
     * @功能 将字符串转化为时间 yyyy-MM-dd HH:mm:ss
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String strDate) {
        try {
            return TIME_LOCAL.get().parse(strDate);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @功能 将字符串转化为日期 yyyy-MM-dd
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        try {
            return DATA_LOCAL.get().parse(strDate);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     * 比较两个日期
     *
     * @throws ParseException
     * @see # d1小于d2, 返回-1
     * @see # d1等于d2, 返回0
     * @see # d1大于d2, 返回1
     */
    @SuppressWarnings("unchecked")
    public static int compareDate(String d1, String d2){
        if (d1 == d2) {
            return 0;
        }
        if (null == d1) {
            return -1;
        }
        if (null == d2) {
            return 1;
        }
        if (d1.equals(d2)) {
            return 0;
        }
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        c1.setTime(parseTime(d1));
        c2.setTime(parseTime(d2));
        int result = ((Comparable) c1).compareTo(c2);
        if (result > 0) {
            return 1;
        } else if (result < 0) {
            return -1;
        }
        return 0;
    }

    public static boolean isThisTime(Date time,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

}
