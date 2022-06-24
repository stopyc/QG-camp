package cn.stopyc.util;


import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @program: Dream
 * @description: md5密码加密
 * @author: stop.yc
 * @create: 2022-03-18 14:40
 **/
public class Md5Utils {

    /**
    * @Description: md5加密
    * @Param: [str]
    * @return: java.lang.String
    * @Author: stop.yc
    * @Date: 2022/4/16
    */
    public static String getMD5(String str) {

        String md5String = null;

        try {
            //mds算法加密摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //加密
            md.update(str.getBytes());
            //转换为16位哈希值
            md5String = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5String;
    }
}
