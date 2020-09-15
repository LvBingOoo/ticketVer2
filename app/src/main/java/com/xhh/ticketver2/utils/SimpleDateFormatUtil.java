package com.xhh.ticketver2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cx on 2017/5/17.
 */

public class SimpleDateFormatUtil {
    public static String timestamp2String(long time, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }
    public static long getTimestamp(String time, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse((timestamp2String(System.currentTimeMillis(),"yyyy-MM-dd "))+time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static boolean isNowDate(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long oldtime=0;

        try {
            Date date = simpleDateFormat.parse(time);
            oldtime = date.getTime();
            if (oldtime> System.currentTimeMillis()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
