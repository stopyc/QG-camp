package cn.stopyc.web.servlet;

import cn.stopyc.bean.SingletonFactory;

import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.po.User;
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

        resp.setContentType("text/json;charset=utf-8");

        //1.获取前端数据(post)请求体
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);

        System.out.println("user"+user);
        //2.删掉用户可能输入的两端空格,以及用户信息加密
        user.setUserName(StringUtil.getTrimStr(user.getUserName()));
        user.setPassword(Md5Utils.getMD5(user.getPassword()));

        //3.获取service对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //4.调用service层的登录功能,获取处理结果集(通过名字和密码进行登录)
        Result<User> userResult = userService.login(user.getUserName(), user.getPassword());


        //5.封装结果集成json对象,返回前端,前端通过code,判断登录情况,重定向
        try {
            JsonUtil.toJson(userResult,resp);
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath+"/user/*");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        //获取用户名,密码,验证码
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String checkCode = request.getParameter("checkCode");
//
//        UserServiceImpl userService = SingletonFactory.getUserServiceSingleton();
//        Result<User> userResult = userService.register(username, password);
//
//        //获取session中的验证码信息
//        HttpSession session = request.getSession();
//        String  checkCodeGen = (String) session.getAttribute("checkCodeGen");
//
//        response.setContentType("text/html;charset=utf-8");
//        //进行比对,比对失败
//        if (!checkCodeGen.equalsIgnoreCase(checkCode)) {
////            System.out.println("验证码输入错误");
//            request.getRequestDispatcher("/register.html").forward(request,response);
//            return;
//        }
//
//        //5.写
//        PrintWriter writer = response.getWriter();
//
////        System.out.println("验证码输入正确");
//        if (userResult.getCode().equals(ResultEnum.SUCCESS.getCode())) {
//            //注册成功~
//
//            System.out.println("注册成功~");
//            request.setAttribute("user",userResult.getData());
//            request.getRequestDispatcher("/login.html").forward(request,response);
//        }else {
//            writer.write(userResult.getMsg());
//        }
    }

    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //字节输出流,响应到页面
        ServletOutputStream os = response.getOutputStream();
//        OutputStream fos = new FileOutputStream("d://a.jpg");
        //生成验证码图片和信息
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);
        System.out.println(checkCode);
        //获取session对象,进行保存验证码
        session.setAttribute("checkCodeGen",checkCode);
    }



    /**
    * @Description: 用户输入姓名后失焦查询
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/16
    */
    public void select(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json;charset=utf-8");
        //1.获取前端数据(post)
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);

        System.out.println("onblur  "+user);
        //2.获取service对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //3.获取处理结果集
        Result<User> userResult = userService.select(StringUtil.getTrimStr(user.getUserName()));

        System.out.println("code  "+userResult.getCode());
        try {
            JsonUtil.toJson(userResult,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}











