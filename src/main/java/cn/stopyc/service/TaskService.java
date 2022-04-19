package cn.stopyc.service;

import cn.stopyc.constant.Result;
import cn.stopyc.po.Task;

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
}
