package cn.stopyc.web.servlet;

import cn.stopyc.bean.SingletonFactory;

import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.TaskDao;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;
import cn.stopyc.service.TaskService;
import cn.stopyc.service.UserService;
import cn.stopyc.util.CheckCodeUtil;
import cn.stopyc.util.JsonUtil;
import cn.stopyc.util.Md5Utils;
import cn.stopyc.util.StringUtil;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @program: qg-baseservlet-demo
 * @description:
 * @author: stop.yc
 * @create: 2022-04-13 15:42
 **/

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{




    /**
    * @Description: 登录servlet
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/13
    */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        System.out.println("我是login");

//        resp.setContentType("text/json;charset=utf-8");

        //1.获取前端数据(post)请求体
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);

        //2.删掉用户可能输入的两端空格,以及用户信息加密
        user.setUserName(StringUtil.getTrimStr(user.getUserName()));
        user.setPassword(Md5Utils.getMD5(user.getPassword()));

        //3.获取service对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //4.调用service层的登录功能,获取处理结果集(通过名字和密码进行登录)
        Result<User> userResult = userService.login(user.getUserName(), user.getPassword());


        //5.封装结果集成json对象,返回前端,前端通过code,判断登录情况,重定向
        try {
            HttpSession session = req.getSession();
            session.setAttribute("username",user.getUserName());
            JsonUtil.toJson(userResult,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        //获取用户名,密码,验证码

        //1.获取前端数据(post)请求体
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);
//
        UserService userService = SingletonFactory.getUserServiceSingleton();
        Result<User> registerResult = userService.register(StringUtil.getTrimStr(user.getUserName()), Md5Utils.getMD5(user.getPassword()),user.getEmail(),user.getPosition());

        //5.封装结果集成json对象,返回前端,前端通过code,判断注册情况
        try {
            JsonUtil.toJson(registerResult,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        response.setContentType("text/json;charset=utf-8");

        //1.获取前端数据(post)请求体
        BufferedReader reader = request.getReader();
        String checkCode = reader.readLine();

        //字节输出流,响应到页面
        ServletOutputStream os = response.getOutputStream();

        //生成验证码图片和信息
        String checkCodeGen = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);

//        //3.获取service对象
//        UserService userService = SingletonFactory.getUserServiceSingleton();
//
//        //4.调用service层的登录功能,获取处理结果集(通过名字和密码进行登录)
//        Result checkResult = userService.checkCheckCode(checkCode,checkCodeGen);
//
//        //5.封装结果集成json对象,返回前端,前端通过code,判断登录情况,重定向
//        try {
//            JsonUtil.toJson(checkResult,response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }



    /**
    * @Description: 用户输入姓名后失焦查询
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/16
    */
    public void select(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/json;charset=utf-8");
        //1.获取前端数据(post)
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);

        //2.获取service对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //3.获取处理结果集
        Result<User> userResult = userService.select(StringUtil.getTrimStr(user.getUserName()));

        try {
            JsonUtil.toJson(userResult,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}











