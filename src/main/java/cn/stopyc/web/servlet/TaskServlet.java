package cn.stopyc.web.servlet;

import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.constant.SessionConstant;
import cn.stopyc.po.Task;
import cn.stopyc.service.TaskService;
import cn.stopyc.service.UserService;
import cn.stopyc.service.impl.TaskServiceImpl;
import cn.stopyc.util.JsonUtil;
import cn.stopyc.util.StringUtil;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
* @Description: taskServlet
* @Param:
* @return:
* @Author: stop.yc
* @Date: 2022/4/18
*/
@WebServlet("/task/*")
public class TaskServlet extends BaseServlet {
    /**
     * @Description: 获取当前用户的任务
     * @Param: [req, resp]
     * @return: void
     * @Author: stop.yc
     * @Date: 2022/4/17
     */
    public void selectMyTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.登录后别的地方需要获取登录用户.
        HttpSession session = req.getSession();
        String  username = (String) session.getAttribute("username");
        System.out.println("mytask"+username);

        //2.获取userService对象
        UserService userService = SingletonFactory.getUserServiceSingleton();

        //3.获取当前用户的id
        Integer idByName = userService.getIdByName(username);

        //5.获取taskService对象
        TaskService taskService = SingletonFactory.getTaskServiceSingleton();

        //6.获取用户下的任务,并封装成对象返回来(可能不存在)
        Result<List<Task>> taskResult = taskService.getTaskByUserId(idByName);

        //7.返回结果集
        JsonUtil.toJson(taskResult,resp);
    }

    /**
     * @Description: 更新总任务数和完成情况
     * @Param: [req, resp]
     * @return: void
     * @Author: stop.yc
     * @Date: 2022/4/18
     */
    public void selectAllTaskCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.接受数据
        BufferedReader reader = req.getReader();
        String userId = reader.readLine();

        //2.获取taskService对象
        TaskService taskService = SingletonFactory.getTaskServiceSingleton();

        //3.调用方法,获取任务完成情况
        Result taskResult = taskService. getTaskCompleteByUserId(Integer.parseInt(userId));

        //4.返回结果集
        JsonUtil.toJson(taskResult,resp);
    }

    /**
    * @Description: 点击完成按钮
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/19
    */
    public void ok(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.接受数据
        BufferedReader reader = req.getReader();
        String taskId = reader.readLine();

        //2.获取taskService对象
        TaskService taskService = SingletonFactory.getTaskServiceSingleton();

        //3.调用完成方法
        Result taskResult = taskService.ok(taskId);

        //4.返回结果集
        JsonUtil.toJson(taskResult,resp);
    }

    /**
    * @Description: 点击添加按钮
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/19
    */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.接受数据,并转换成java对象
        BufferedReader reader = req.getReader();
        String addTask = reader.readLine();
        //你要添加的任务对象
        Task task = JSON.parseObject(addTask, Task.class);

        //2.获取添加任务给谁,总负责人给自己
        Integer userId = task.getUserId();

        System.out.println(userId);
        String username;
        Integer idByName;
        if (userId == 0) {
            //是总负责人给自己
            HttpSession session = req.getSession();
            username = (String) session.getAttribute("username");

            //3.获取userService对象
            UserService userService = SingletonFactory.getUserServiceSingleton();

            //4.获取当前用户的id
            idByName = userService.getIdByName(username);
        }else {
            idByName = userId;
        }


        //5.获取taskService对象
        TaskService taskService = SingletonFactory.getTaskServiceSingleton();

        //6.调用添加方法
        Result taskResult = taskService.add(task, idByName);

        //7.返回结果集
        JsonUtil.toJson(taskResult,resp);
    }

    /**
    * @Description: 点击修改
    * @Param: [req, resp]
    * @return: void
    * @Author: stop.yc
    * @Date: 2022/4/24
    */
    public void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.接受数据,并转换成java对象
        BufferedReader reader = req.getReader();
        String addTask = reader.readLine();

        //2.转换成你要修改的任务对象;
        Task task = JSON.parseObject(addTask, Task.class);

        //3.获取service对象
        TaskService taskService = SingletonFactory.getTaskServiceSingleton();

        //4.调用方法,获取结果集
        Result<Object> result = taskService.modify(task);

        //5.返回结果集
        JsonUtil.toJson(result,resp);

    }


}
