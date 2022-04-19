package cn.stopyc.service.impl;

import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.TaskDao;
import cn.stopyc.dao.UserDao;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;
import cn.stopyc.service.TaskService;
import cn.stopyc.util.StringUtil;

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

        //1.获取dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();

        //2.通过用户id获取任务对象
        Task task = taskDao.getTaskByUserId(idByName);

        //3.找不到任务,那就没有任务
        if (task == null) {
            return new Result<>(ResultEnum.NO_TASK.getCode(),ResultEnum.NO_TASK.getMsg());
        }else {
        //4.有的话就以集合的形式返回,(前端接受表格)
            List<Task> list = new ArrayList<>();
            list.add(task);
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),list);
        }
    }

    @Override
    public Result getTaskCompleteByUserId(Integer userId) {
        //以后必改,应该是递归算法

        //1.获取dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //2.通过id,获取子任务总数(总负责人要看最低层的,其他人就看他低一层的,并且所属的人的任务情况)

        //2.通过用户id,获取用户对象
        User user = userDao.getUserByUserId(userId);

        //3.(总负责人)获取总任务id
        int generalId = user.getTaskId();

        List<Task> tasks = new ArrayList<>();
        String data;
        //3.是总负责人的话,那么要全部信息
        Integer position = user.getPosition();
        if (position == 0) {
            //4.获取总任务的所有子任务
            List<Task> sonTasks= taskDao.getLittleTask(generalId);
            List<Integer> fatherTasksIds = new ArrayList<>();
            //5.获取子任务的任务id,和对应父级的id
            for (Task sonTask : sonTasks) {
                fatherTasksIds.add(sonTask.getParentTaskId());
            }
            //count:总的子任务数,noCompleteCount:子任务未完成的数量
            int count = 0,noCompleteCount = 0;
            //6.记录最底层任务的总数(可能是小工的,或者是包工头的,或者是上一级的)
            for (Task sonTask : sonTasks) {
                //最低层的任务,一定没有任务是她的子任务
                if(!fatherTasksIds.contains(sonTask.getTaskId())) {
                    count++;
                    if (sonTask.getStatus() == 1) {
                        noCompleteCount++;
                    }
                }
            }
            //数据展示
            data = noCompleteCount + " / " + count;
            return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),data);
        }
        return null;
    }

    @Override
    public Result ok(String taskId) {

        //1.没有任务
        if (StringUtil.isEmpty(taskId)) {
            return new Result(ResultEnum.NO_TASK.getCode(),ResultEnum.NO_TASK.getMsg());
        }

        //2.获取dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //3.你的子级任务是否全部完成?获取子级任务集合,遍历判断
        List<Task> sonTasks = taskDao.getSonTasks(taskId);
        for (Task sonTask : sonTasks) {
            //存在未完成的子任务
            if (sonTask.getStatus() == 0) {
                return new Result(ResultEnum.INCOMPLETE_TASK.getCode(),ResultEnum.INCOMPLETE_TASK.getMsg());
            }
        }
        //子级任务都完成了,或者没有子级任务,完成.应该分多种职位进行任务表和用户表的修改
        //4.获取user对象
        User user = userDao.getUserByTaskId(taskId);
        //5.职位判断,(如果是总负责人,任务完成后,应该是相关的全部任务结束,并删除,其他的只是下级结束,但不删除,可以给总负责人看详细情况)
        int position = user.getPosition();
        switch (position){
            //5.1总负责人,任务完成后,把数据库中任务的所有子任务全部删除,相关用户全部任务删除
            case 0:
                //5.1.1获取所有的子级任务,然后可以改用户的任务id
                List<Task> littleTask = taskDao.getLittleTask(Integer.parseInt(taskId));
                //5.1.2把用户的任务id删了,
                for (Task task : littleTask) {
                    userDao.deleteTask(task.getTaskId());
                }
                //5.1.3把总任务status设为1
                taskDao.updateStatus(Integer.valueOf(taskId));
                //5.1.4把任务表中总任务是这一个的全部删除
                taskDao.deleteTask(Integer.valueOf(taskId));
                break;
            default:
        }
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    @Override
    public Result add(Task task,int userId) {
        //1.获取dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //2.获取用户对象
        User user = userDao.getUserByUserId(userId);

        //3.只有总负责人才能给自己添加任务其他都是给别人加任务
        taskDao.addTask(task, userId);

        //4.获取新增的任务id(自增)
        Task taskByUserId = taskDao.getTaskByUserId(userId);

        //5.设置总任务id
        taskDao.setGeneralId(taskByUserId.getTaskId(),userId);

        //6.为用户添加任务
        userDao.addTaskId(taskByUserId.getTaskId(),userId);

        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    @Override
    public List<Task> getTasksByUsers(List<User> users) {
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        return taskDao.selectTasksByUsers(users);
    }


}
