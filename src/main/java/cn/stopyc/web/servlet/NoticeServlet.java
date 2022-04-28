package cn.stopyc.web.servlet;

import cn.stopyc.bean.MyNotice;
import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.service.NoticeService;
import cn.stopyc.util.JsonUtil;
import com.alibaba.fastjson.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @program: qg-engineering-management-system
 * @description:
 * @author: stop.yc
 * @create: 2022-04-13 15:42
 **/

@WebServlet("/notice/*")
public class NoticeServlet extends BaseServlet{


    /**
    * @Description: 获取所有通知
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/13
    */
    public void getNotice(HttpServletRequest req, HttpServletResponse resp) {

        //1.获取请求对象的名称
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        //2.获取service对象
        NoticeService noticeServiceSingleton = SingletonFactory.getNoticeServiceSingleton();

        //3.调用对象
        Result<List<MyNotice>> result = noticeServiceSingleton.getNotice(username);

        //4.返回前端
        JsonUtil.toJson(result,resp);
    }


    /**
    * @Description: 删除所选通知
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/27
    */
    public void deleteNotice(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        //1.获取前端数据(需要删除的通知ids)
        BufferedReader reader = req.getReader();
        String s = reader.readLine();

        //2.转换成java对象
        int[] deleteIds = JSON.parseObject(s,int[].class);

        //3.获取service对象
        NoticeService noticeService = SingletonFactory.getNoticeServiceSingleton();

        //4.调用方法
        Result<Object> result = noticeService.deleteNoticeByIds(deleteIds);

        //5.返回结果
        JsonUtil.toJson(result,resp);


    }
}











