package cn.stopyc.service.impl;

import cn.stopyc.bean.MyTeam;
import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.UserDao;
import cn.stopyc.dao.impl.UserDaoImpl;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;
import cn.stopyc.service.TaskService;
import cn.stopyc.service.UserService;
import cn.stopyc.util.StringUtil;
import cn.stopyc.util.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: qg-engineering-management-system
 * @description: UserService实现类
 * @author: stop.yc
 * @create: 2022-04-16 16:20
 **/
public class UserServiceImpl implements UserService {


    /**
     * 私有构造器
     */
    private UserServiceImpl(){

    }


    @Override
    public Result<User> login(String userName, String password) {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //1.dao类进行查询登录的用户信息
        User user = userDao.selectByNameAndPassword(userName, password);

        if (user == null) {
            //2.如果找不到这个用户,那么就是你密码输入错误了,用户名已经通过异步查询进行筛了一次了
            return new Result<>(ResultEnum.PASSWORD_FAILED.getCode(),ResultEnum.PASSWORD_FAILED.getMsg());
        } else {
            //3.如果对象不为null,那么就表示找到了这个对象,表示成功了
            //4.登录成功,应该返回对象,但是不应该包含用户的敏感信息
            user.setPassword("");
            return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
        }
    }

    @Override
    public Result<User> select(String userName) {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //1.根据用户姓名找人
        User user = userDao.selectByName(userName);

        if (user == null) {
            //2.如果找不到这个用户,提示用户名不存在
            return new Result<>(ResultEnum.FIND_USER_FAILED.getCode(),ResultEnum.FIND_USER_FAILED.getMsg());
        } else {
            //3.如果对象不为null,那么就表示找到了这个对象,表示有重复名
            //4.登录成功,应该返回对象,但是不应该包含用户的敏感信息
            //5.注册失焦,重复名
            return new Result<>(ResultEnum.REPEAT_NAME.getCode(), ResultEnum.REPEAT_NAME.getMsg());
        }
    }

    @Override
    public Result checkCheckCode(String checkCode, String checkCodeGen) {
        //验证码对了
        if (StringUtil.isEmpty(checkCode) || !checkCode.equalsIgnoreCase(checkCodeGen)) {
            return new Result(ResultEnum.CHECK_CODE_ERROR.getCode(),ResultEnum.CHECK_CODE_ERROR.getMsg());
        }else {
            return new Result(200);
        }
    }

    @Override
    public Result<User> register(String userName, String password, String email, Integer position) {

        //在注册的时候,我建议是发送个邮件,可以验证邮箱是否正确

        //性别默认为男,入职时间为现在,bossId为无

        String strFromCurrentTime = TimeUtils.getStrFromCurrentTime(System.currentTimeMillis());

        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        int i = userDao.insertNewOne(userName,password,email,position,"男",strFromCurrentTime);

        //成功注册
        if (i > 0) {
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
        }else {
            return new Result<>(ResultEnum.DATABASE_ERROR.getCode(),ResultEnum.DATABASE_ERROR.getMsg());
        }
    }

    @Override
    public Integer getIdByName(String userName) {
        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        User user = userDao.selectByName(userName);
        return user.getUserId();
    }

    @Override
    public Result<MyTeam> selectMyTeam(Integer bossId) {
        //1.调用dao,获取下一级的用户
        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        List<User> users = userDao.selectUsersByBossId(bossId);
        if (users.size() == 0) {
            return new Result<>(ResultEnum.NO_TEAM.getCode(),ResultEnum.NO_TEAM.getMsg());
        }

        //2.平移,获取taskService对象
        TaskService taskService = SingletonFactory.getTaskServiceSingleton();

        //3.获取任务,那么现在要封装成对象.
        List<Task> tasks = taskService.getTasksByUsers(users);

        List<MyTeam> myTeams = new ArrayList<>();
        for (User user : users) {
            if (null == user.getTaskId() || 0 == user.getTaskId()) {
                myTeams.add(new MyTeam(user.getUserName(), "无任务", -1, null, -1, user.getUserId(), null));
                continue;
            }
            for (Task task : tasks) {
                if (user.getTaskId().equals(task.getTaskId())) {
                    myTeams.add(new MyTeam(user.getUserName(), task.getTaskName(), task.getLevel(), task.getDeadline(), task.getStatus(), task.getUserId(), task.getTaskId()));
                    break;
                }
            }
        }
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),myTeams);
    }
}
