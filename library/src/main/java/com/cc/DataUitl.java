/**
 * 
 */
package com.cc;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DataUitl {

    /**
     * 
     * @longStr 1256006105375
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String longToStrng(String longStr) {
		if (TextUtils.isEmpty(longStr)){
			return "";
		}
        Date date = new Date(Long.parseLong(longStr.trim()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * @timeStr yyyy-MM-dd HH:mm:ss
     * @return Wed Oct 21 10:35:05 CST 2009
     */
    public static Date stringToDate(String timeStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date s = null;
        try {
            s = formatter.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("TIME:::" + s);
        return s;
    }

    /**
     * 获取现在时间
     * 
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     * 
     * @return HH:mm:ss
     */
    public static String getStringTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 
     * @longStr 20090402103531
     * @return yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String strToDatestr(String longStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sss = null;
        try {
            sss = sdf2.format(sdf.parse(longStr));
            System.out.println("result" + sss);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sss;
    }

	public static String strTostr(String str) {
		String sss = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date strtodate = formatter.parse(str);
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
			sss = sdf2.format(strtodate);
			System.out.println("result" + sss);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sss;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static long strToLong(String str) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long sss = 0;
		try {
			Date data = sdf2.parse(str);
			sss = data.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sss;
	}
	public static long strToLongJava(String str) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long sss = 0;
		try {
			Date data = sdf2.parse(str);
			sss = data.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sss;
	}
	public static long strToLongDate(String str) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		long sss = 0;
		try {
			Date data = sdf2.parse(str);
			sss = data.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sss;
	}
	public static long strToLongTime(String str) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		long sss = 0;
		try {
			Date data = sdf2.parse(str);
			sss = data.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sss;
	}
	public static String surplusLongToStrng(long between) {
		// long between = 90179;
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;
		// System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
		StringBuffer sb = new StringBuffer();
		if (0 != day1) {
			sb.append(day1);
			sb.append("天");
		}
		if (hour1 < 10) {
			sb.append("0");
		}
		sb.append(hour1);
		sb.append("小时");
		if (minute1 < 10) {
			sb.append("0");
		}
		sb.append(minute1);
		sb.append("分");
		if (second1 < 10) {
			sb.append("0");
		}
		sb.append(second1);
		sb.append("秒");

		return sb.toString();
    }
	public static String[] surplusLongToStrngArr(long between) {
		// long between = 90179;
		String[] times = new String[4];
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;
        //把day 放在hour1
		hour1 = hour1 + day1 * 24;
		if (hour1<10){
			times[1] = "0" +hour1;
		}else{
			times[1] = "" +hour1;
		}
		if (minute1<10){
			times[2] = "0" + minute1;
		}else{
			times[2] = "" +minute1;
		}
		if (second1<10){
			times[3] = "0" + second1;
		}else{
			times[3] = "" +second1;
		}

		return times;
    }

	/**
	 *  多少秒以前 多少分以前  多少小时 以前
	 * @param end_time
	 * @return
	 */
	public static String surplusLongToStrngShort(String end_time) {
		long between = (System.currentTimeMillis() - DataUitl.strToLongJava(end_time))/1000;
		// long between = 90179;
		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60;
		//把day 放在hour1
		hour1 = hour1 + day1 * 24;
		if (day1 >0){
			return day1 + "天以前";
		}else if (hour1 > 0){
			return hour1 + "小时以前";
		}else if (minute1 > 0){
			return minute1 + "分钟以前";
		}else if (second1 > 0){
			return second1 + "秒以前";
		}
		return "";
	}
    
    /**
     * Java将Unix时间戳转换成指定格式日期
     * @param timestampString
     * @return
     */
    public static String timeStamp2Date(String timestampString){
		try {
			Long timestamp = Long.parseLong(timestampString) * 1000;
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
  	}
    public static long timeStamp2Date2(long timestampString){
		try {
			Long timestamp = timestampString * 1000;
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
			long  tim = strToLong(date);
			return tim;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
  	}

	public static boolean isBeforTodayData(String newDataStr){
		long currentData = strToLongDate(getStringDate());
		long newData = strToLongDate(newDataStr);
		if (currentData > newData){
			return true;
		}
		return false;
	}
    public static boolean isBefor21TimeData(String newDataStr){
        long currentData = strToLongTime(getStringTime());
        long newData = strToLongTime(newDataStr);
        if (currentData > newData){
            return true;
        }
        return false;
    }
}
