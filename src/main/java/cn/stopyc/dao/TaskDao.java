package cn.stopyc.dao;

import cn.stopyc.po.Task;

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
     * @param idByName
     * @return
     */
    Task getTaskByUserId(Integer idByName);

    /**
     * 通过任务id集合获取子任务总数
     * @param generalTaskId
     * @return
     */
    List<Task> getLittleTask(int generalTaskId);


    /**
     * 通过任务id,获取子级任务集合
     * @param taskId
     * @return
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

//    /**
//     * 根据任务id集合获取任务集合
//     * @param taskIds
//     * @return
//     */
//    List<Task> getAllTaskByIds(List<Integer> taskIds);

//    /**
//     * 根据总任务id获取最低层任务集合
//     * @param generalId
//     * @return
//     */
//    List<Task> getLittlestTasks(int generalId);

    /**
     * 设置最级任务id
     * @param generalId
     * @param userId
     */
    void setGeneralId(int generalId,int userId) ;
}
