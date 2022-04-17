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
}
