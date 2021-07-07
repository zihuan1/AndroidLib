package com.zihuan.app.u;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期转换工具类
 */
public class DateUtil {


    //时间换算 年月日
    public static String GetTimeString(String argTime) {

        if (argTime != null && argTime.equals("")) {
            return "";
        }
        Long time = Long.parseLong(argTime);
        Date mData = new Date(time * 1000);
        Calendar cal = Calendar.getInstance();   //年月日格式
        cal.setTime(mData);

        String strTime = null;
        long interval = (System.currentTimeMillis() - mData.getTime()) / 1000;
        if (interval < 0)
            interval = 0;
        int iMinute = 60;
        int iHour = 60 * iMinute;
        int iDay = 24 * iHour;
        int iMonth = 30 * iDay;
        int iYear = 365 * iMonth;

        Log.e("second", "second" + interval);

        if ((int) (interval) < 60 && ((int) (interval) > 0)) {
            strTime = "" + (int) (interval) + "秒前";
        } else if ((int) (interval / iMinute) < 60 && (int) (interval / iMinute) > 0) {
            strTime = "" + (int) (interval / iMinute) + "分钟前";
        } else if ((int) (interval / iHour) < 24 && (int) (interval / iHour) > 0) {
            // strTime = "" + (int) (interval / iHour) + "小时前";
            // strTime = m_sdf.format(mData);
            // 此处改了
            strTime = "" + (int) (interval / iHour) + "小时前";
        } else if ((int) (interval / iDay) <= 1 && (int) (interval / iDay) > 0) {
            strTime = "" + (int) (interval / iDay) + "天前";
            // strTime = m_sdf.format(mData);
//            strTime = cal.get(java.util.Calendar.YEAR)+"年"+(cal.get(java.util.Calendar.MONTH)+1)+"月"+
//                    cal.get(java.util.Calendar.DAY_OF_MONTH)+"日";
        }
//        else if ((int) (interval / iMonth) != 0) {
//            //  strTime = "" + (int) (interval / iMonth) + "个月前";
//            //strTime = m_sdf.format(mData);
//            strTime = cal.get(java.util.Calendar.YEAR) + "年" + (cal.get(java.util.Calendar.MONTH) + 1) + "月" +
//                    cal.get(java.util.Calendar.DAY_OF_MONTH) + "日";
//        }
        else {
            // strTime = "" + (int) (interval / iYear) + "年前";
//            strTime = cal.get(java.util.Calendar.YEAR)+"年"+cal.get(java.util.Calendar.MONTH)+"月"+
//                    cal.get(java.util.Calendar.DAY_OF_MONTH)+"日";
            strTime = new SimpleDateFormat("MM月dd日 HH:mm").format(new Date(time));
//            strTime = new java.text.SimpleDateFormat(20+"yy年MM月dd日 HH:mm:ss").format(new java.util.Date(argTime));

        }
        return strTime;
    }

    //时间换算 时分
    public static String GetTime(long argTime) {
        Date mData = new Date(argTime);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(argTime);
        Calendar cal = Calendar.getInstance();   //年月日格式
        cal.setTime(mData);

        String strTime;
        long interval = (System.currentTimeMillis() - mData.getTime()) / 1000;
        if (interval < 0)
            interval = 0;
        int iMinute = 60;
        int iHour = 60 * iMinute;
        int iDay = 24 * iHour;
        int iMonth = 30 * iDay;
        int iYear = 365 * iMonth;
        if ((int) (interval / iYear) != 0) {
            // strTime = "" + (int) (interval / iYear) + "年前";

            strTime = cal.get(Calendar.YEAR) + "年" + cal.get(Calendar.MONTH) + "月" +
                    cal.get(Calendar.DAY_OF_MONTH) + "日" + dateString;
        } else if ((int) (interval / iMonth) != 0)
            //  strTime = "" + (int) (interval / iMonth) + "个月前";
            //strTime = m_sdf.format(mData);
            strTime = (cal.get(Calendar.MONTH) + 1) + "月" +
                    cal.get(Calendar.DAY_OF_MONTH) + "日" + dateString;
        else if ((int) (interval / iDay) != 0)
            // strTime = "" + (int) (interval / iDay) + "天前";
            // strTime = m_sdf.format(mData);
            strTime = (cal.get(Calendar.MONTH) + 1) + "月" +
                    cal.get(Calendar.DAY_OF_MONTH) + "日" + dateString;
        else if ((int) (interval / iHour) != 0)
            // strTime = "" + (int) (interval / iHour) + "小时前";
            // strTime = m_sdf.format(mData);
            // strTime =  "" + (int) (interval / iHour) + "小时前";
            if ((interval / iHour) >= 2) {
                strTime = (cal.get(Calendar.MONTH) + 1) + "月" +
                        cal.get(Calendar.DAY_OF_MONTH) + "日" + dateString;
            } else {
                strTime = "" + (int) (interval / iHour) + "小时前";
            }
        else if ((int) (interval / iMinute) != 0)
            strTime = "" + (int) (interval / iMinute) + "分钟前";
        else
            strTime = "" + (int) (interval) + "秒前";

        return strTime;
    }

    // 获得系统时间
    public static String getSysDate(String type) {
        if (TextUtils.isEmpty(type)) {
            type = "yyyyMMddHHmmss";
        }
        String str = getSysTimeType(type);
        return str;
    }


    // 获得系统时间自定义类型
    public static String getSysTimeType(String type) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取向后推迟的时间
     *
     * @param type "yyyy-MM-dd"
     * @param day  后几天
     * @return
     */
    public static String getAfterDay(String type, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
//     Calendar类有一个方法add方法可以使用，例如calendar.add(Calendar.WEEK_OF_YEAR, -1);表示把时间向上推一周，
//     calendar.add(Calendar.YEAR, -1);表示把时间向上推一年。
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.DAY_OF_MONTH, +day);
        date = calendar.getTime();
        String str = formatter.format(date);
        Logger.e("向后" + day + "天", "" + date);
        return str + "";
    }

    //    时间戳转换为时间
    public static String getDate(String time, String type) {
        if (TextUtils.isEmpty(type)) {
            type = "yyyy-MM-dd";
        }

        if (TextUtils.isEmpty(time)) {
            return "";
        }
        long d = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String date = sdf.format(new Date(d * 1000));
//        Logger.tag("data"+ date);
        return date + "";
    }

    //    时间戳转换为时间
    public static String getDateAmPm(String time, String type) {
        if (TextUtils.isEmpty(type)) {
            type = "yyyy-MM-dd";
        }

        if (TextUtils.isEmpty(time)) {
            return "";
        }
        long d = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date data = new Date(d * 1000);
        String date = sdf.format(data);
        if (data.getHours() < 12) {
            date += "am";
        } else {
            date += "pm";
        }
//        Logger.tag("data"+ date);

        return date + "";
    }


    //    将当前时间变为字符串
    public static String getCurrentStrDate(String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String date = sdf.format(new Date());
        return date + "";
    }


    //    时间转换为时间戳
//    public static String convert(long mill) {
//        Date date = new Date(mill);
//        String strs = "";
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            strs = sdf.format(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return strs;
//    }


    //    转换为时间戳
    public static String conversion(String string, String type) {
        if (TextUtils.isEmpty(type)) {
            type = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date date = null;
        int time = 0;
        try {
            date = sdf.parse(string);
            time = (int) date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Logger.e("时间戳", " " + time);
        return time + "";
    }

    //获取 yyyy-MM-dd 的 时间戳
    public static String GetTimeStamp(String argTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        long targetTime = (Long) (DateUtil.getStringToDate(argTime + ":00", "yyyy-MM-dd HH:mm:ss") * 1000);
        Date date = new Date(targetTime);
        calendar.setTime(date);
        date = calendar.getTime();
        String str = formatter.format(date);
        long time = DateUtil.getStringToDate(str, "yyyy-MM-dd HH:mm:ss");
        return time + "";
    }

    List<String> topWeek = new ArrayList<String>();

    //获得上一周的数据 c 负数时向上一周 正数时间向下一周 0本周
    public List getTopWeek(int c) {
        SimpleDateFormat sf = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        System.out.println("=========LastWeek Days=========");
        cal.add(Calendar.WEEK_OF_MONTH, c);
        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DATE, -1 * cal.get(Calendar.DAY_OF_WEEK) + 2 + i);
            Logger.e("上一周", "" + sf.format(cal.getTime()));
            topWeek.add(sf.format(cal.getTime()) + "");
        }
        return topWeek;
    }

    //    计算两个时间戳相隔多少天
    public static int getApartDay(String a, String b) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day1 = sdf.format(new Date(Long.parseLong(a) * 1000));
        String day2 = sdf.format(new Date(Long.parseLong(b) * 1000));
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(day1);
            d2 = sdf.parse(day2);
        } catch (ParseException e) {
            Logger.tag("异常" + e.toString());
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(d2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);


        Log.e("between_days", between_days + "天");

//        if(between_days == 0) {   //半天
//            return "半";
//        }

//        return (Long.parseLong(b) - Long.parseLong(a)) / (86400000) + "";
        return Integer.parseInt(String.valueOf(between_days));
    }

    //两个时间戳相差多少时分秒
    public static String time(String time1, String time2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(time1);//当前时间

            Date d2 = df.parse(time2);//过去时间

            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

            long miao = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

            Logger.tag("" + days + "天" + hours + "小时" + minutes + "分" + miao + "秒");

            Logger.tag("diff " + diff);
            if (hours == 0) {
                return +minutes + ":" + miao;
            } else {
                return hours + ":" + minutes + ":" + miao;
            }
        } catch (Exception e) {

        }
        return "0";
    }

    //两个时间戳相差多少秒
    public static String timeSS(String time1, String time2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(time1);//当前时间

            Date d2 = df.parse(time2);//过去时间
            long diff;

            diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别

//            long year = diff / (1000 * 60 * 60 * 24 *365);

            Logger.tag("" + diff);

            return diff + "";

        } catch (Exception e) {
            Logger.tag("Exception" + e.toString());
        }
        return "0";
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time    2016-1-25
     * @param argType
     * @return
     */
    public static long getStringToDate(String time, String argType) {
        String strType = argType;
        if (argType == null) {
            strType = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(strType);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            Logger.tag("时间装换成时间戳错误");
        }
        Logger.tag("时间装换成时间戳" + (date.getTime()));
        return date.getTime() / 1000;
    }

    /**
     * 判断是否今天过生日
     *
     * @param time
     * @return
     */
    public static boolean judgeTodayBirthday(Long time) {
        String today = getCurrentStrDate("MM-dd");
        String[] todaies = today.split("-");
        Log.e("todya", today);
        String strDate = getDate(time + "", "MM-dd");
        String[] strDates = strDate.split("-");
        Log.e("strDate", strDate);

        if (todaies[0].equals(strDates[0]) && todaies[1].equals(strDates[1])) {
            return true;
        }
        return false;
    }

    /**
     * 获取今天开始的 0点 0 分
     *
     * @return
     */
    public static long GetToday() {
        String strToday = DateUtil.getSysTimeType("yyyy年MM月dd日");
        long today = DateUtil.getStringToDate(strToday + " 00:00:00", null);
        return today / 1000;
    }


    /**
     * 获取今天 结束的 0点 0 分
     *
     * @return
     */
    public static long GetTomorrow() {
        long today = GetToday();
        return today + 24 * 60 * 60;
    }

    public static String getAge(String old) {
        int i = Integer.parseInt(DateUtil.getDate(old, "yyyy"));
        int j = Integer.parseInt(DateUtil.getSysTimeType("yyyy"));
        return j - i + "";
    }


    //  获取系统时间
    public String getSysTime(String type) {
        if (TextUtils.isEmpty(type)) {
            type = "yyyy/MM/dd/HH:mm";
        }
        return DateUtil.getSysDate(type);
    }

}
