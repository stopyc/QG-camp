package cn.stopyc.dao;

import cn.stopyc.po.Task;
import cn.stopyc.po.User;

import java.util.Date;
import java.util.List;

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
     * @param idByName:用户id
     * @return: 返回任务对象
     */
    Task getTaskByUserId(Integer idByName);

    /**
     * 通过任务id集合获取最子任务总数
     * @param generalTaskId:任务id
     * @return: 任务集合
     */
    List<Task> getLittleTask(int generalTaskId);


    /**
     * 通过任务id,获取子级任务集合
     * @param taskId:任务id
     * @return: 任务集合
     */
    List<Task> getSonTasks(String taskId);

    /**
     * 通过任务id,删除任务
     * @param taskId:任务id
     */
    void deleteTask(Integer taskId);

    /**
     * 更新状态,表示为完成
     * @param taskId:任务id
     */
    void updateStatus(Integer taskId);


    /**
     * 添加任务
     * @param task:任务
     * @param userId:用户id
     */
    void addTask(Task task, int userId);


    /**
     * 设置最级任务id
     * @param generalId:最级任务id
     * @param userId: 用户id
     */
    void setGeneralId(int generalId,int userId) ;

    /**
     * 通过用户集合获取任务集合
     * @param users:用户集合
     * @return: 任务集合
     */
    List<Task> selectTasksByUsers(List<User> users);

    /**
     * 修改任务
     * @param taskName:任务名称
     * @param level:任务难度
     * @param deadline:截止日期
     * @param taskId:需要修改的任务id
     */
    void modifyTask(String taskName, Integer level, Date deadline, Integer taskId);
}

