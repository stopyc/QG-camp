package cn.stopyc.dao.impl;

import cn.stopyc.dao.TaskDao;
import cn.stopyc.po.Task;
import cn.stopyc.service.impl.BeanHandler;
import cn.stopyc.util.CRUDUtils;

import javax.swing.*;

/**
 * @program: qg-engineering-management-system
 * @description:实现dao类
 * @author: stop.yc
 * @create: 2022-04-17 22:37
 **/
public class TaskDaoImpl implements TaskDao {

    private TaskDaoImpl() {

    }

    @Override
    public Task getTaskByUserId(Integer idByName) {
        String sql = "select * from `t_task` where `userId`=?";
        return CRUDUtils.query(sql,new BeanHandler<>(Task.class),idByName);
    }
}
