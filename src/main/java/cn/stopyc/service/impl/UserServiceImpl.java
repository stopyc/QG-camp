package cn.stopyc.service.impl;

import cn.stopyc.bean.MyTeam;
import cn.stopyc.bean.QueryUser;
import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.TaskDao;
import cn.stopyc.dao.UserDao;
import cn.stopyc.dao.impl.TaskDaoImpl;
import cn.stopyc.dao.impl.UserDaoImpl;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;
import cn.stopyc.service.TaskService;
import cn.stopyc.service.UserService;
import cn.stopyc.util.StringUtil;
import cn.stopyc.util.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
    public Result<Integer> login(String userName, String password) {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //1.dao类进行查询登录的用户信息
        User user = userDao.selectByNameAndPassword(userName, password);

        if (user == null) {
            //2.如果找不到这个用户,那么就是你密码输入错误了,用户名已经通过异步查询进行筛了一次了
            return new Result<>(ResultEnum.PASSWORD_FAILED.getCode(),ResultEnum.PASSWORD_FAILED.getMsg(),-1);
        } else {
            //3.如果对象不为null,那么就表示找到了这个对象,表示成功了
            //4.登录成功,应该返回对象,但是不应该包含用户的敏感信息
            user.setPassword("");
            return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),user.getPosition());
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
        int i = userDao.insertNewOne(userName,password,email,position,"1",strFromCurrentTime);

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

    @Override
    public void kickMember(Integer userId) {
        //踢人业务,任务id变为0,上级Id变为0,记住下级也需要变化

        List<Integer> needChangeIds = new ArrayList<>();
        needChangeIds.add(userId);
        //1.拿dao
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //2.被踢的这个人单独处理,他的上级没有了,但是下级还在,表示他的下级的bossId没有发生变化.
        //删除bossId
        userDao.kickMember(userId);

        Iterator<Integer> iterator = needChangeIds.iterator();

        while(iterator.hasNext()) {
            //3.获取需要删除任务的用户id
            Integer id = iterator.next();
            //4.删除
            userDao.removeTask(id);
            //5.需要改变的id集合少了一个
            iterator.remove();
            //6.需要获取下级用户集合
            List<User> users = userDao.getSonUser(id);
            //7.添加需要改变任务id的用户id
            for (User user : users) {
                needChangeIds.add(user.getUserId());
            }
            //8.重要!!更新迭代器!
            iterator = needChangeIds.iterator();
        }
    }

    @Override
    public Result<QueryUser> queryUser(User user,Integer sort) {
        //1.获取dao
        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        TaskDao taskDao = SingletonFactory.getTaskDaoSingleton();

        //2.根据条件拼接sql语句
        StringBuilder sb = new StringBuilder("select * from `t_user` where 1 ");

        //3.占位符的条件集合
        List<String> conditions = new ArrayList<>();


        //String sql = "select * from `t_tiber` where  `tiberName`  like CONCAT('%',?,'%') or `creatorName` like CONCAT('%',?,'%') and `camp` = '"+camp+"' order by `power` desc";
        //4.姓名不为空
        if (StringUtil.isNotEmpty(user.getUserName())) {
            sb.append("and `userName` like CONCAT('%',?,'%') ");
            conditions.add(user.getUserName().trim());
        }

        //5.职位 ,-1表示全部职位
        if (user.getPosition() != -1) {
            sb.append("and `position`=? ");
            conditions.add(user.getPosition()+"");
        }

        //6.性别,-1表示全部性别
        if (!Objects.equals(user.getGender(), "-1")) {
            sb.append("and `gender`=? ");
            conditions.add(user.getGender());
        }

        //7.上级,1表示要有上级,0表示没有,-1表示都要
        if (user.getBossId() != -1) {
            //有上级
            if (user.getBossId() == 1) {
                sb.append("and (`bossId`!= '0' and `bossId` IS NOT NULL)");
            }else {
                sb.append("and (`bossId`='0' or `bossId` IS NULL)");
            }
        }

        //8.升序,降序,姓名和入职时间
        switch (sort) {
            case 1:
                sb.append(" order by `userName`");
                break;
            case 2:
                sb.append(" order by `userName` desc");
                break;
            case 3:
                sb.append(" order by `hireDate` ");
                break;
            case 4:
                sb.append(" order by `hireDate` desc ");
                break;
            default:
                break;
        }

        String sql = sb.toString();
        List<User> users = userDao.selectByConditions(sql,conditions.toArray());

        //找不到用户
        if(users.size() == 0) {
            return new Result<>(ResultEnum.NOT_FOUND.getCode(),ResultEnum.NOT_FOUND.getMsg());
        }
        //现在已经拿到了用户数据,现在需要封装数据返回前端

        //9.获取任务集合
        List<Task> tasks = taskDao.selectTasksByUsers(users);

        //10.获取上级集合
        List<User> bosses = userDao.selectBossesByUsers(users);

        List<QueryUser> queryUsers = new ArrayList<>();
        for (User u :users) {
            //没有任务
            if (null == u.getTaskId() || 0 == u.getTaskId()) {
                //没有上属
                if (null == u.getBossId() || 0 ==u.getBossId()) {
                    queryUsers.add(new QueryUser(u.getUserId(),u.getUserName(),Integer.parseInt(u.getGender()),0,"无上属",0, "无任务",u.getHireDate()));
                }else {
                    for (User boss : bosses) {
                        if (boss.getUserId().equals(u.getBossId())) {
                            queryUsers.add(new QueryUser(u.getUserId(),u.getUserName(),Integer.parseInt(u.getGender()),0,boss.getUserName(),boss.getUserId(), "无任务",u.getHireDate()));
                            break;
                        }
                    }
                }
                continue;
            }
            for (Task task : tasks) {
                if (u.getTaskId().equals(task.getTaskId())) {
                    for (User boss : bosses) {
                        if (boss.getUserId().equals(u.getBossId())) {
                            queryUsers.add(new QueryUser(u.getUserId(),u.getUserName(),Integer.parseInt(u.getGender()),task.getTaskId(),boss.getUserName(),boss.getUserId(), task.getTaskName(),u.getHireDate()));
                            break;
                        }
                    }
                }
            }
        }
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),queryUsers);
    }

    @Override
    public Result inTeam(Integer inTeamUserId, String bossName) {

        //1.获取dao
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        //2.获取上级用户,目的是得到id
        User user = userDao.selectByName(bossName);

        //3.调用进队方法
        userDao.inTeam(inTeamUserId,user.getUserId());

        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }
}

















