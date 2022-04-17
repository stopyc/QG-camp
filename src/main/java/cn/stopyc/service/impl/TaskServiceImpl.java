package cn.stopyc.service.impl;

import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.TaskDao;
import cn.stopyc.dao.UserDao;
import cn.stopyc.po.Task;
import cn.stopyc.service.TaskService;
import cn.stopyc.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: qg-engineering-management-system
 * @description: TaskService实现类
 * @author: stop.yc
 * @create: 2022-04-17 22:36
 **/
public class TaskServiceImpl implements TaskService {

    private TaskServiceImpl() {

    }


    @Override
    public Result<List<Task>> getTaskByUserId(Integer idByName) {
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        Task task = taskDao.getTaskByUserId(idByName);
        System.out.println(task.getDeadline());
        //找不到任务,那就没有任务
        if (task == null) {
            return new Result<>(ResultEnum.NO_TASK.getCode(),ResultEnum.NO_TASK.getMsg());
        }else {
            List<Task> list = new ArrayList<>();
            list.add(task);
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),list);
        }
    }
}
