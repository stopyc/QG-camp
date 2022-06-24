package cn.stopyc.constant;

import cn.stopyc.pool.MyDataSource;

import java.io.InputStream;
import java.util.Properties;

/**
 * @program: qg-engineering-management-system
 * @description: 系统配置常量
 * @author: stop.yc
 * @create: 2022-04-28 13:14
 **/
public class SystemConstant {


    public static int MAX_NAME_LENGTH;

    public static int MIN_NAME_LENGTH;

    public static int MAX_PASSWORD_LENGTH;

    public static int MIN_PASSWORD_LENGTH;

    public static String  UP = "上线";

    public static String  DOWN = "下线";

    static {
        try {
            InputStream in = MyDataSource.class.getClassLoader().getResourceAsStream("system.properties");
            Properties properties = new Properties();
            properties.load(in);
            MAX_NAME_LENGTH = new Integer(properties.getProperty("maxNameLen")) ;
            MIN_NAME_LENGTH = new Integer(properties.getProperty("minNameLen")) ;
            MAX_PASSWORD_LENGTH = new Integer(properties.getProperty("maxPasswordLen")) ;
            MIN_PASSWORD_LENGTH = new Integer(properties.getProperty("minPasswordLen")) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
