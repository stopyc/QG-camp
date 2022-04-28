package cn.stopyc.util;

import cn.stopyc.constant.Result;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: qg-engineering-management-system
 * @description:
 * @author: stop.yc
 * @create: 2022-04-15 16:05
 **/
public class JsonUtil {
    /**
     * 抛出json数据格式
     */

    public static void toJson(Result result, HttpServletResponse response)  {
        //返回json格式
        response.setContentType("text/json");

        //浏览器不缓存动态页面,保持每次打开页面都是一个最新的请求
        response.setHeader("Cache-Control", "no-cache");

        //设置编码
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            //输出null字段,数字null变为0,集合null变为[],字符串null变为""布尔null变为false
            String json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.DisableCircularReferenceDetect);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
