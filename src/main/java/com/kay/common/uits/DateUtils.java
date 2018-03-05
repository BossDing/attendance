package com.kay.common.uits;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kay on 2018/3/5.
 */
public class DateUtils {


    private static Calendar calendar = Calendar.getInstance();

    /**
     * 获取星期
     * @return
     */
    public static int getWeekOfToday(){
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK) -1;
        if(week<0) week = 7;
        return week;
    }

    /**
     * 获取相差分钟
     */
    public static int getMinute(Date startDate,Date endDate){
        long start = startDate.getTime();
        long end = endDate.getTime();
        int minute = (int) ((end - start) / (1000 * 60));
        return minute;
    }

    /**
     * 根据时间获取当天的date对象
     * @param hour
     * @param minute
     * @return
     */
    public static Date getDate(int hour,int minute){
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date time = calendar.getTime();
        return time;
    }

}
