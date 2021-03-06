package cn.stopyc.dao.impl;

import cn.stopyc.dao.TaskDao;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;
import cn.stopyc.service.impl.BeanHandler;
import cn.stopyc.service.impl.BeanListHandle;
import cn.stopyc.util.CRUDUtils;
import cn.stopyc.util.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * @program: qg-engineering-management-system
 * @description: 实现dao类
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

    @Override
    public List<Task> getLittleTask(int generalTaskId) {
        String sql = "select * from `t_task` where `generalId`=?";
        return CRUDUtils.query(sql, new BeanListHandle<>(Task.class), generalTaskId);
    }

    @Override
    public List<Task> getSonTasks(String taskId) {
        String sql = "select * from `t_task` where `parentTaskId`=?";
        return CRUDUtils.query(sql,new BeanListHandle<>(Task.class),taskId);
    }

    @Override
    public void deleteTask(Integer taskId) {
        String sql = "delete from `t_task` where `generalId`=? or `taskId`=?";
        CRUDUtils.update(sql,taskId,taskId);
    }

    @Override
    public void updateStatus(Integer taskId) {
        String sql = "update `t_task` set `status`=1 where `taskId`=?";
        CRUDUtils.update(sql,taskId);
    }

    @Override
    public void addTask(Task task, int userId) {
        String sql = "insert into `t_task` (`taskName`,`userId`,`level`,`deadline`,`parentTaskId`,`status`,`generalId`) values (?,?,?,?,?,'0',?)";
        CRUDUtils.update(sql,task.getTaskName(),userId,task.getLevel(),TimeUtils.getStrFromDate(task.getDeadline()),task.getParentTaskId(),task.getGeneralId());
    }

    @Override
    public void setGeneralId(int generalId,int userId) {
        String updateSql = "update `t_task` set `generalId`=? where `userId`=?";
        CRUDUtils.update(updateSql,generalId,userId);
    }

    @Override
    public List<Task> selectTasksByUsers(List<User> users) {
        //1.拼接字符串
        StringBuilder sb = new StringBuilder("select * from `t_task` where `userId` in (");
        for (User u : users) {
            sb.append(u.getUserId()).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        String sql = sb.toString();
        return CRUDUtils.query(sql,new BeanListHandle<>(Task.class));
    }

    @Override
    public void modifyTask(String taskName, Integer level, Date deadline, Integer taskId) {
        String sql = "update `t_task` set `taskName`=?,`level`=?,`deadline`=? where `taskId`=?";
        CRUDUtils.update(sql,taskName,level,TimeUtils.getStrFromDate(deadline),taskId);
    }


}
