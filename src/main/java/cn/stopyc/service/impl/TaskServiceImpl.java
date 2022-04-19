package cn.stopyc.service.impl;

import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.TaskDao;
import cn.stopyc.dao.UserDao;
import cn.stopyc.dao.impl.UserDaoImpl;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;
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
        //找不到任务,那就没有任务
        if (task == null) {
            return new Result<>(ResultEnum.NO_TASK.getCode(),ResultEnum.NO_TASK.getMsg());
        }else {
            List<Task> list = new ArrayList<>();
            list.add(task);
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),list);
        }
    }

    @Override
    public Result getTaskCompleteByUserId(Integer userId) {
        //1.获取dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //2.通过id,获取子任务总数(总负责人要看最低层的,其他人就看他低一层的,并且所属的人的任务情况)
        User user = userDao.getUserByUserId(userId);
        int generalId = user.getTaskId();
        List<Task> tasks = new ArrayList<>();
        String data;
        //3.是总负责人的话,那么要全部信息
        Integer position = user.getPosition();
        //总1:  经理:2,3: 包公:45,67 小工,45 45 45 45;
        if (position == 0) {
            //4.获取总任务的所有子任务总数
            List<Task> sonTasks= taskDao.getLittleTask(generalId);
            List<Integer> fatherTasksId = new ArrayList<>();
            //5.获取子任务的任务id,和对应父级的id
            for (Task sonTask : sonTasks) {
                fatherTasksId.add(sonTask.getParentTaskId());
            }
            int count = 0,noCompleteCount = 0;
            //6.记录最底层任务的总数(可能是小工的,或者是包工头的,或者是上一级的)
            for (Task sonTask : sonTasks) {
                if(!fatherTasksId.contains(sonTask.getTaskId())) {
                    count++;
                    if (sonTask.getStatus() == 1) {
                        noCompleteCount++;
                    }
                }
            }
            data = noCompleteCount + " / " + count;
            return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),data);
        }
        return null;
    }

    @Override
    public Result ok(String taskId) {

        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //1.你的子级任务是否全部完成?获取子级任务集合,遍历判断
        List<Task> sonTasks = taskDao.getSonTasks(taskId);
        for (Task sonTask : sonTasks) {
            if (sonTask.getStatus() == 0) {
                return new Result(ResultEnum.INCOMPLETE_TASK.getCode(),ResultEnum.INCOMPLETE_TASK.getMsg());
            }
        }
        //2.子级任务都完成了,或者没有子级任务,完成.应该分多种职位进行任务表和用户表的修改
        User user = userDao.getUserByTaskId(taskId);
        int position = user.getPosition();
        switch (position){
            //2.1总负责人,任务完成后,把数据库中任务的所有子任务全部删除,相关用户全部任务删除
            case 0:
                //2.1.1获取所有的子级任务,然后可以改用户的任务id
                List<Task> littleTask = taskDao.getLittleTask(Integer.parseInt(taskId));
                //2.1.2把用户的任务id删了,
                for (Task task : littleTask) {
                    userDao.deleteTask(task.getTaskId());
                }
                //2.1.3把总任务status设为1
                taskDao.updateStatus(Integer.valueOf(taskId));
                //2.1.4把任务表中总任务是这一个的全部删除
                taskDao.deleteTask(Integer.valueOf(taskId));
                break;
            default:
        }
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    @Override
    public Result add(Task task,int userId) {
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        User user = userDao.getUserByUserId(userId);
        //只有总负责人才能给自己添加任务
        taskDao.addTask(task, userId);
        System.out.println("userId == "+userId );
        Task taskByUserId = taskDao.getTaskByUserId(userId);
        System.out.println("taskByUserId  == " + taskByUserId);
        taskDao.setGeneralId(taskByUserId.getTaskId(),userId);
        userDao.addTaskId(taskByUserId.getTaskId(),userId);

        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

}
