package cn.stopyc.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: Dream01
 * @description: 处理时间的工具类
 * @author: stop.yc
 * @create: 2022-03-22 09:59
 **/
public class TimeUtils {

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

    /**
    * @Description: 把date转换为只有日期的格式
    * @Param: [signInDate]
    * @return: java.lang.String
    * @Author: stop.yc
    * @Date: 2022/3/25
    */
    public static String getStrFromDate(Date signInDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(signInDate);
    }
}
