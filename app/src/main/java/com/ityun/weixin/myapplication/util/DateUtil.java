package com.ityun.weixin.myapplication.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class DateUtil {

    public static String timeToText(long sendTime) {

        if (IsToday(sendTime)) {
            Date date = new Date(sendTime);
            SimpleDateFormat format = new SimpleDateFormat("mm:ss");
            String time = format.format(date);
            return time;
        } else if (IsYesterday(sendTime)) {

            return "昨天";

        } else {
            Date date = new Date(sendTime);
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
            String time = format.format(date);
            return  time;

        }

    }


    public static String timeToHHText(long sendTime) {

        if (IsToday(sendTime)) {
            Date date = new Date(sendTime);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String time = format.format(date);
            return time;
        } else if (IsYesterday(sendTime)) {

            return "昨天";

        } else {
            Date date = new Date(sendTime);
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
            String time = format.format(date);
            return  time;

        }

    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(long day) {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(long day) {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }


}
