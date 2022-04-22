package cn.stopyc.dao;

import cn.stopyc.po.User;

import java.util.List;

/**
* @Description: 用户dao类接口
* @Param:
* @return:
* @Author: stop.yc
* @Date: 2022/4/16
*/
public interface UserDao {

    /**
     * 根据用户名和密码获取用户对象
     * @param userName:用户名
     * @param password:密码
     * @return: User对象
     */
    User selectByNameAndPassword(String userName,String password);


    /**
     * 根据用户名查询用户
     * @param userName:用户姓名
     * @return
     */
    User selectByName(String userName);

    /**
     * 新增一个用户
     * @param userName:名字
     * @param password:密码
     * @param email:邮箱
     * @param position:职位
     * @param gender:性别
     * @param hireDate:入职时间
     * @return
     */
    int insertNewOne(String userName, String password, String email, Integer position,String gender,String hireDate);

    /**
     * 通过用户id获取用户
     * @param idByName:用户id
     * @return
     */
    User getUserByUserId(Integer idByName);

    /**通过任务id获取用户
     *
     * @param taskId
     * @return
     */
    User getUserByTaskId(String taskId);

    /**
     * 删除用户的任务
     * @param taskId
     */
    void deleteTask(Integer taskId);

    /**
     * 添加用户任务
     * @param taskId:任务id
     * @param userId:用户id
     */
    void addTaskId(int taskId, int userId);

    /**
     * 根据上级id获取用户集合
     * @param bossId
     * @return
     */
    List<User> selectUsersByBossId(Integer bossId);

    /**
     * 踢人
     * @param userId:用户id
     */
    void kickMember(Integer userId);


    /**
     * 获取下级用户
     * @param userId:上级id
     * @return
     */
    List<User> getSonUser(Integer userId);

    /**
     * 删除任务id
     * @param id:用户id
     */
    void removeTask(Integer id);


    /**
     * 根据条件进行查询
     * @param sql
     * @param conditions
     * @return
     */
    List<User> selectByConditions(String sql, Object[] conditions);


    /**
     * 通过用户集合返回上级集合
     * @param users:用户集合
     * @return
     */
    List<User> selectBossesByUsers(List<User> users);


    /**
     * 进队
     * @param inTeamUserId:进队用户id
     * @param userId:上级id
     */
    void inTeam(Integer inTeamUserId, Integer userId);
}
