package cn.stopyc.dao;

import cn.stopyc.po.Task;

/**
* @Description: TaskDao
* @Param:
* @return:
* @Author: stop.yc
* @Date: 2022/4/17
*/
public interface TaskDao {

    /**
     * 根据用户id获取任务
     * @param idByName
     * @return
     */
    Task getTaskByUserId(Integer idByName);
}
