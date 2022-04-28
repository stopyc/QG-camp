package cn.stopyc.web.servlet;

import cn.stopyc.bean.MyTeam;
import cn.stopyc.bean.QueryUser;
import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.po.User;
import cn.stopyc.service.TaskService;
import cn.stopyc.service.UserService;
import cn.stopyc.util.CheckUtil;
import cn.stopyc.util.JsonUtil;
import cn.stopyc.util.Md5Utils;
import cn.stopyc.util.StringUtil;
import com.alibaba.fastjson.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @program: qg-engineering-management-system
 * @description:
 * @author: stop.yc
 * @create: 2022-04-13 15:42
 **/

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{

//    private static final String sessionUserName = SessionConstant.SESSION_USERNAME;


    /**
    * @Description: 登录servlet
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/13
    */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {



        //1.获取前端数据(post)请求体

        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);

        //补充校验
        if (!(CheckUtil.checkName(user.getUserName()) && CheckUtil.checkPassword(user.getPassword()))) {
            JsonUtil.toJson(new Result<>(ResultEnum.PARAMETER_NOT_VALID.getCode(), ResultEnum.PARAMETER_NOT_VALID.getMsg()),resp);
            return;
        }

        //2.删掉用户可能输入的两端空格,以及用户信息加密
        user.setUserName(StringUtil.getTrimStr(user.getUserName()));
        user.setPassword(Md5Utils.getMD5(user.getPassword()));

        //3.获取service对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //4.调用service层的登录功能,获取处理结果集(通过名字和密码进行登录)
        Result<Integer> userResult = userService.login(CheckUtil.XssAndHtml(user.getUserName()), user.getPassword());

        //5.封装结果集成json对象,返回前端,前端通过code,判断登录情况,重定向
        HttpSession session = req.getSession();

        //6.设置sessionUserName
        session.setAttribute("username",CheckUtil.XssAndHtml(user.getUserName()));
        JsonUtil.toJson(userResult,resp);
    }



    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
         //获取用户名,密码,邮箱

        //1.获取前端数据(post)请求体
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();

        User user = JSON.parseObject(userStr, User.class);

        //补充校验
        if (!(CheckUtil.checkName(user.getUserName()) && CheckUtil.checkPassword(user.getPassword()) && CheckUtil.checkEmail(user.getEmail()))) {
            JsonUtil.toJson(new Result<>(ResultEnum.PARAMETER_NOT_VALID.getCode(), ResultEnum.PARAMETER_NOT_VALID.getMsg()),resp);
            return;
        }

        UserService userService = SingletonFactory.getUserServiceSingleton();
        Result<User> registerResult = userService.register(CheckUtil.XssAndHtml(StringUtil.getTrimStr(user.getUserName())), Md5Utils.getMD5(user.getPassword()),user.getEmail(),user.getPosition());

        //5.封装结果集成json对象,返回前端,前端通过code,判断注册情况
        JsonUtil.toJson(registerResult,resp);
    }


    /**
    * @Description: 用户输入姓名后失焦查询
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/16
    */
    public void select(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取前端数据(post)
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);

        //2.获取service对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //3.获取处理结果集
        Result<User> userResult = userService.select(StringUtil.getTrimStr(user.getUserName()));

        JsonUtil.toJson(userResult,resp);
    }

    /**
     * @Description: 查找我的队伍信息
     * @Param: [req, resp]
     * @return: void
     * @Author: stop.yc
     * @Date: 2022/4/19
     */
    public void selectMyTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        //1.获取前端数据
        BufferedReader reader = req.getReader();
        String username = reader.readLine();

        username = new String(username.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);

        //2.获取userService对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //3.获取当前用户的id
        Integer bossId = userService.getIdByName(username);

        Result<List<MyTeam>> myTeamResult = userService.selectMyTeam(bossId);

        JsonUtil.toJson(myTeamResult,resp);
    }

    /**
    * @Description: 踢出成员
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/21
    */
    public void kickMember(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //1.获取前端数据
        BufferedReader reader = req.getReader();
        String userIdAndTaskId = reader.readLine();
        String[] split = userIdAndTaskId.split("&");
        for (String s : split) {
            System.out.println(s);
        }
        //第一个用户id,第二个是任务id

        //2.获取userService对象
        UserService userService = SingletonFactory.getUserServiceSingleton();
        TaskService taskService = SingletonFactory.getTaskServiceSingleton();

        userService.kickMember(Integer.parseInt(split[0]));
        if (!(StringUtil.isEmpty(split[1]) || "0".equals(split[1]))){
            taskService.deleteTask(Integer.parseInt(split[1]));
        }

        Result<Object> result = new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());

        JsonUtil.toJson(result,resp);
    }


    /**
    * @Description: 判断是否存在该下级用户
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/21
    */
    public void selectSon(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //1.获取前端数据(第一个是用户姓名,第二个是职位标志)
        BufferedReader reader = req.getReader();
        String queryUserStr = reader.readLine();
        String[] split = queryUserStr.split("&");

        split[0] = new String(split[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
        User queryUser = JSON.parseObject(split[0], User.class);

        //2.获取查询的用户信息

        //3.获取userService对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        Result<List<QueryUser>> result = userService.queryUser(queryUser,Integer.parseInt(split[1]));


        JsonUtil.toJson(result,resp);

    }

    /**
    * @Description: 指定成员进队
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/22
    */
    public void inTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //1.获取前端数据(要进队的用户id)
        BufferedReader reader = req.getReader();
        String inTeamUserId = reader.readLine();

        //2.获取谁在操作这个用户
        HttpSession session = req.getSession();
        String  bossName = (String) session.getAttribute("username");

        //3.获取userService对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //4.获取结果集
        Result<Object> result = userService.inTeam(Integer.parseInt(inTeamUserId),bossName);

        //5.返回前端
        JsonUtil.toJson(result,resp);
    }

    /**
    * @Description: 获取session
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/23
    */
    public void getSession(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        UserService userService = SingletonFactory.getUserServiceSingleton();
        Result<User> select = userService.select(username);
        select.getData().setPassword("");

        Result<Object> result = new Result<>(200,username,select.getData());

        JsonUtil.toJson(result,resp);
    }

    /**
    * @Description: 退出登录
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/23
    */
    public void loginOut(HttpServletRequest req, HttpServletResponse resp) {

        //1.获取谁退出登录
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        //2.获取service
        UserService userService = SingletonFactory.getUserServiceSingleton();
        userService.loginOut(username);
    }


    /**
    * @Description: 修改密码
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/24
    */
    public void changePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取前端数据(第一个是老密码,第二个是新密码),因为不存在中文,所以不需要转码
        BufferedReader reader = req.getReader();
        String queryUserStr = reader.readLine();
        String[] split = queryUserStr.split("&");

        //补充校验
        if (!(CheckUtil.checkPassword(split[0]) && CheckUtil.checkPassword(split[1]) )) {
            JsonUtil.toJson(new Result<>(ResultEnum.PARAMETER_NOT_VALID.getCode(), ResultEnum.PARAMETER_NOT_VALID.getMsg()),resp);return;
        }

        //2.获取修改的用户的名字
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        //3.获取service
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //4.调用业务改变密码,获取结果集
        Result<Object> result = userService.changePassword(split[0],split[1],username);

        //5.返回结果集
        JsonUtil.toJson(result,resp);
    }

    /**
    * @Description:
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/24
    */
    public void modifyInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //1.获取前端数据(post),第一个是修改后的条件对象,第二个是原来的用户
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        userStr = new String(userStr.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
        String[] split = userStr.split("&");


        User user = JSON.parseObject(split[0], User.class);

        //补充校验
        if (!(CheckUtil.checkName(user.getUserName()) && CheckUtil.checkEmail(user.getEmail()))) {
            JsonUtil.toJson(new Result<>(ResultEnum.PARAMETER_NOT_VALID.getCode(), ResultEnum.PARAMETER_NOT_VALID.getMsg()),resp);
            return;
        }

        user.setUserName(CheckUtil.XssAndHtml(user.getUserName()));

        //2.获取service
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //3.调用方法,获取结果集
        Result<Object> result = userService.modifyInfo(user,split[1]);

        //4.成功的话,需要更新session
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.setAttribute("username",user.getUserName());

        //5.返回结果集
        JsonUtil.toJson(result,resp);
    }

}











