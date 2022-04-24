package cn.stopyc.service;

import cn.stopyc.bean.MyTeam;
import cn.stopyc.constant.Result;
import cn.stopyc.po.Task;
import cn.stopyc.po.User;

import java.util.List;

/**
* @Description: 任务Service
* @Param:
* @return:
* @Author: stop.yc
* @Date: 2022/4/17
*/
public interface TaskService {


    /**
     * 通过用户id获取任务
      * @param idByName
     * @return
     */
    Result<List<Task>> getTaskByUserId(Integer idByName);


    /**
     * 通过用户id获取所属任务完成信息
     * @param userId:用户id
     * @return
     */
    Result getTaskCompleteByUserId(Integer userId);


    /**
     * 点击任务完成按钮
     * @param taskId:任务id
     * @return
     */
    Result ok(String taskId);

    /**
     * 添加任务信息
     * @param task:任务
     * @param userId:用户id
     * @return
     */
    Result add(Task task,int userId);


    /**
     * 通过用户集合,获取任务集合
     * @param users:用户集合
     * @return
     */
    List<Task> getTasksByUsers(List<User> users);

    /**
     * 删除任务
     * @param taskId:任务id
     */
    void deleteTask(Integer taskId);


    /**
     * 修改任务
     * @param task:需要修改的任务
     * @return :返回结果(成功与否)
     */
    Result<Object> modify(Task task);
}
