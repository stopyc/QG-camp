package cn.stopyc.util;

import cn.stopyc.constant.SystemConstant;

/**
 * @program: qg-engineering-management-system
 * @description: 表单值校验
 * @author: stop.yc
 * @create: 2022-04-28 13:11
 **/
public class CheckUtil {

    /**
    * @Description: 检查名称
    * @Param: [name]
    * @return: boolean
    * @Author: stop.yc
    * @Date: 2022/4/28
    */
    public static boolean checkName(String name) {

        //不为空
        if (StringUtil.isEmpty(name)){
            return false;
        }

        //超过长度,(保护数据库)
        if (name.length() < SystemConstant.MIN_NAME_LENGTH || name.length() > SystemConstant.MAX_NAME_LENGTH) {
            return false;
        }

        return true;
    }

    /**
     * @Description: 检查名称
     * @Param: [name]
     * @return: boolean
     * @Author: stop.yc
     * @Date: 2022/4/28
     */
    public static boolean checkPassword(String password) {

        //不为空
        if (StringUtil.isEmpty(password)){
            return false;
        }

        //超过长度,(保护数据库)
        if (password.length() < SystemConstant.MIN_PASSWORD_LENGTH || password.length() > SystemConstant.MAX_PASSWORD_LENGTH) {
            return false;
        }

        return true;
    }

    /**
     * @Description: 检查名称
     * @Param: [name]
     * @return: boolean
     * @Author: stop.yc
     * @Date: 2022/4/28
     */
    public static boolean checkEmail(String email) {

        //不为空
        if (StringUtil.isEmpty(email)){
            return false;
        }

        //正则校验邮箱
        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }


    /**
    * @Description: 过滤xss
    * @Param: [msg]
    * @return: java.lang.String
    * @Author: stop.yc
    * @Date: 2022/4/28
    */
    public static String XssAndHtml(String msg){
        StringBuilder sb = new StringBuilder(msg.length());
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            switch (c) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("<br />");
                    break;
                case '\r':
                    // ignore
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\'':
                    sb.append("\\'");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString().trim();
    }

}
