package cn.stopyc.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: Dream01
 * @description: 处理时间的工具类
 * @author: stop.yc
 * @create: 2022-03-22 09:59
 **/
public class TimeUtils {
//    /**
//    * @Description: 把数据拿到的时间戳转换为date类型
//    * @Param: [timestamp]
//    * @return: java.util.Date
//    * @Author: stop.yc
//    * @Date: 2022/3/25
//    */
//    public static Date getDateFromStamp(Timestamp timestamp) {
//        //
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str = sdf.format(timestamp);
//        try {
//            return sdf.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    /**
//    * @Description: 把date类型的时间格式化
//    * @Param: [signInDate]
//    * @return: java.lang.String
//    * @Author: stop.yc
//    * @Date: 2022/3/25
//    */
//    public static String getStrFromDate(Date signInDate) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return sdf.format(signInDate);
//    }

    /**
    * @Description: 把当前时间格式化
    * @Param: [currentTime]
    * @return: java.lang.String
    * @Author: stop.yc
    * @Date: 2022/3/25
    */
    public static  String getStrFromCurrentTime(long currentTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(currentTime);
    }


//    /**
//    * @Description: 处理部落创建时间工具,把date转换为只有日期的格式
//    * @Param: [signInDate]
//    * @return: java.lang.String
//    * @Author: stop.yc
//    * @Date: 2022/3/25
//    */
//    public static String getStrFromDateOfTiber(Date signInDate) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(signInDate);
//    }
//
//    /**
//    * @Description: 把通知时间时间格式化为距离现在多长时间
//    * @Param: [notifiedTime]
//    * @return: java.lang.String
//    * @Author: stop.yc
//    * @Date: 2022/3/25
//    */
//    public static  String getDiscrepancyTime (Date notifiedDate){
//        //当前时间
//        StringBuffer sb = new StringBuffer("");
//        long currentTime = System.currentTimeMillis();
//        long notifiedTime = notifiedDate.getTime();
//        long disTime = currentTime - notifiedTime;
//        disTime /= 1000;
//        if (disTime < SystemConstants.JUST_NOW_TIME) {
//            sb.append("刚刚.");
//            return sb.toString();
//        }
//
//        long days = disTime / 24 / 60 / 60;
//        disTime %= 24 * 60 * 60;
//        long hour = disTime / 60 / 60;
//        disTime %= 60 * 60;
//        long minute = disTime / 60;
//        disTime %= 60;
//
//        if (days > 0) {
//            sb.append(days+"天");
//        }
//        if (hour > 0) {
//            sb.append(hour+"小时");
//        }
//        sb.append(minute+"分钟前.");
//
//        return sb.toString();
//
//    }

}
