package cn.stopyc.dao.impl;

import cn.stopyc.dao.UserDao;
import cn.stopyc.po.User;
import cn.stopyc.service.impl.BeanHandler;
import cn.stopyc.util.CRUDUtils;

/**
 * @program: qg-engineering-management-system
 * @description: UserDao实现类
 * @author: stop.yc
 * @create: 2022-04-16 16:21
 **/
public class UserDaoImpl implements UserDao {

    private UserDaoImpl() {

    }

    @Override
    public User selectByNameAndPassword(String userName, String password) {
        String sql = "select * from `t_user` where `userName`=? and `password`=?";
        return CRUDUtils.query(sql,new BeanHandler<>(User.class),userName,password);
    }

    @Override
    public User selectByName(String userName) {
        String sql = "select * from `t_user` where `userName`=?";
        return CRUDUtils.query(sql,new BeanHandler<>(User.class),userName);
    }

    @Override
    public int insertNewOne(String userName, String password, String email, Integer position,String gender,String hireDate) {

        String sql = "insert into `t_user` (`userName`,`password`,`position`,`email`,`gender`,`hireDate`) " +
                "values (?,?,?,?,?,?)";
        return CRUDUtils.update(sql,userName,password,position,email,gender,hireDate);
    }

    @Override
    public User getUserByUserId(Integer idByName) {
        String sql = "select * from `t_user` where `userId`=?";
        return  CRUDUtils.query(sql,new BeanHandler<>(User.class),idByName);
    }

    @Override
    public User getUserByTaskId(String taskId) {
        String sql = "select * from `t_user` where `taskId`=?";
        return CRUDUtils.query(sql,new BeanHandler<>(User.class),taskId);
    }

    @Override
    public void deleteTask(Integer taskId) {
        String sql = "update `t_user` set `taskId`=0 where `taskId`=?";
        CRUDUtils.update(sql,taskId);
    }

    @Override
    public void addTaskId(int taskId, int userId) {
        String sql = "update `t_user` set `taskId`=? where `userId`=?";
        CRUDUtils.update(sql,taskId,userId);
    }


}
