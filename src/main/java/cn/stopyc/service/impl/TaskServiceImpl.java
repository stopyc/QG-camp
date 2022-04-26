package cn.stopyc.service.impl;

import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.TaskDao;
import cn.stopyc.dao.UserDao;
import cn.stopyc.dao.impl.TaskDaoImpl;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;
import cn.stopyc.service.NoticeService;
import cn.stopyc.service.TaskService;
import cn.stopyc.util.StringUtil;
import cn.stopyc.web.ws.WebSocket;

import java.util.ArrayList;
import java.util.Iterator;
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
            System.out.println("task = " + list);
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),list);
        }
    }

    @Override
    public Result getTaskCompleteByUserId(Integer userId) {

        //1.获取dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //通过id,获取子任务总数(总负责人要看最低层的,其他人就看他低一层的,并且所属的人的任务情况)

        //2.通过用户id,获取用户对象
        User user = userDao.getUserByUserId(userId);

        //3.(总负责人)获取总任务id
        int generalId = user.getTaskId();
        int taskId = user.getTaskId();

        List<Task> tasks = new ArrayList<>();
        String data;
        //3.是总负责人的话,那么要全部信息
        Integer position = user.getPosition();
        //count:总的子任务数,noCompleteCount:子任务未完成的数量
        int count = 0,completeCount = 0;
        if (position == 0) {
            //4.获取总任务的所有子任务
            List<Task> sonTasks= taskDao.getLittleTask(generalId);
            List<Integer> fatherTasksIds = new ArrayList<>();
            //5.获取子任务的任务id,和对应父级的id
            for (Task sonTask : sonTasks) {
                fatherTasksIds.add(sonTask.getParentTaskId());
            }

            //6.记录最底层任务的总数(可能是小工的,或者是包工头的,或者是上一级的)
            for (Task sonTask : sonTasks) {
                //最低层的任务,一定没有任务是她的子任务
                if(!fatherTasksIds.contains(sonTask.getTaskId())) {
                    count++;
                    if (sonTask.getStatus() == 1) {
                        completeCount++;
                    }
                }
            }
            //数据展示
            data = completeCount + " / " + count;

        }else{
            List<Task> sonTasks = taskDao.getSonTasks(taskId + "");

            count = sonTasks.size();
            if (count == 0) {

            }
            for (Task sonTask : sonTasks) {
                if (sonTask.getStatus() == 1) {
                    completeCount++;
                }
            }
            data = completeCount + " / " + count;
        }

        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),data);
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

            //5.2 其他人
            default:
                //5.2.1获取下级任务集合,对用户的任务id删除
                for (Task sonTask : sonTasks) {
                    userDao.deleteTask(sonTask.getTaskId());
                    //5.2.2区别于总负责人,它需要删除下级的任务,但是,其他人只需要更改任务状态,不删除
                    taskDao.updateStatus(sonTask.getTaskId());
                }
                //5.2.3最后把传进来的也设为完成
                taskDao.updateStatus(Integer.parseInt(taskId));
        }

        //6.发送消息
        sendNotice(user.getUserName() + "已完成任务", user);

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

        //如果是总负责人的话,需要设置总任务id
        if (task.getGeneralId() == null || task.getGeneralId() == 0) {
            //5.设置总任务id
            taskDao.setGeneralId(taskByUserId.getTaskId(),userId);
        }

        //6.为用户添加任务
        userDao.addTaskId(taskByUserId.getTaskId(),userId);

        //7.发送消息
        sendNotice(user.getUserName() + "获得了一个新任务", user);

        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    @Override
    public List<Task> getTasksByUsers(List<User> users) {
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        return taskDao.selectTasksByUsers(users);
    }

    @Override
    public void deleteTask(Integer taskId) {
        //删除任务,直接删除id对应的就行了,同时还要删除下级任务.

        //需要删除的任务id集合
        List<Integer> needDeleteTaskIds = new ArrayList<>();
        needDeleteTaskIds.add(taskId);
        //1.调用dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();

        Iterator<Integer> iterator = needDeleteTaskIds.iterator();
        while(iterator.hasNext()) {
            Integer id = iterator.next();
            //2.删除当前任务
            taskDao.deleteTask(id);

            //3.需要删除的集合中需要少一个id
            iterator.remove();

            //4.获取下级任务
            List<Task> sonTasks = taskDao.getSonTasks(id + "");

            //5.添加需要删除的任务id
            for (Task sonTask : sonTasks) {
                needDeleteTaskIds.add(sonTask.getTaskId());
            }
            //6.更新迭代器
            iterator = needDeleteTaskIds.iterator();
        }
    }

    @Override
    public Result<Object> modify(Task task) {

        //1.获取需要修改的任务的id
        Integer taskId = task.getTaskId();

        //2.获取dao
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        User userByTaskId = userDao.getUserByTaskId(taskId + "");

        //3.调用方法
        taskDao.modifyTask(task.getTaskName(),task.getLevel(),task.getDeadline(),taskId);

        //5.发送消息
        sendNotice(userByTaskId.getUserName() + "已上线", userByTaskId);

        //6.返回结果集
        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }


    /**
     * @Description: 发送实时和离线通知
     * @Param: [msg, user]
     * @return: void
     * @Author: stop.yc
     * @Date: 2022/4/26
     */
    private void sendNotice(String msg, User user) {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //1.发送实时消息给下级和上级,首先通过用户id获取上下级的用户id,
        List<String> usernames = new ArrayList<>();

        //2.获取上级
        User boss = userDao.getUserByUserId(user.getBossId());
        if (null != boss) {
            usernames.add(boss.getUserName());
        }

        //3.获取下级
        List<User> sonUser = userDao.getSonUser(user.getUserId());
        for (User u : sonUser) {
            usernames.add(u.getUserName());
        }

        //4.发送离线,现在usernames中的人就是需要收到消息的人,那么我们要写表了,参数,谁发的,发给谁,信息是什么
        if (!msg.contains("上线") || msg.contains("离线")) {
            NoticeService noticeService = SingletonFactory.getNoticeServiceSingleton();
            noticeService.sendNotice(user,usernames,msg);
        }

        //5.发送实时通知
        WebSocket.sendMessage(new Result<>(200, msg, usernames));
    }


}
