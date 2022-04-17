package cn.stopyc.util;

import cn.stopyc.constant.Result;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @program: qg-baseservlet-demo
 * @description:
 * @author: stop.yc
 * @create: 2022-04-15 16:05
 **/
public class JsonUtil {
    /**
     * 抛出json数据格式
     */

    public static void toJson(Result result, HttpServletResponse response) throws Exception {
        response.setContentType("text/json");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.DisableCircularReferenceDetect);

        writer.write(json);
        System.out.println("传入的"+json);
        writer.close();
    }
}
