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
     * @return: 返回用户
     */
    User selectByNameAndPassword(String userName,String password);


    /**
     * 根据用户名查询用户
     * @param userName:用户姓名
     * @return 返回用户
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
     * @return 返回执行数据
     */
    int insertNewOne(String userName, String password, String email, Integer position,String gender,String hireDate);

    /**
     * 通过用户id获取用户
     * @param idByName:用户id
     * @return 返回用户
     */
    User getUserByUserId(Integer idByName);

    /**通过任务id获取用户
     *
     * @param taskId:任务id
     * @return 返回用户
     */
    User getUserByTaskId(String taskId);

    /**
     * 删除用户的任务
     * @param taskId:任务id
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
     * @param bossId:上级id
     * @return 返回用户集合
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
     * @return 返回用户集合
     */
    List<User> getSonUser(Integer userId);

    /**
     * 删除任务id
     * @param id:用户id
     */
    void removeTask(Integer id);


    /**
     * 根据条件进行查询
     * @param sql:条件sql
     * @param conditions:条件
     * @return :返回用户集合
     */
    List<User> selectByConditions(String sql, Object[] conditions);


    /**
     * 通过用户集合返回上级集合
     * @param users:用户集合
     * @return :返回用户集合
     */
    List<User> selectBossesByUsers(List<User> users);


    /**
     * 进队
     * @param inTeamUserId:进队用户id
     * @param userId:上级id
     */
    void inTeam(Integer inTeamUserId, Integer userId);


    /**
     * 修改密码
     * @param newPassword:密码
     * @param userId:用户id
     */
    void changePassword(String newPassword, Integer userId);


    /**
     * 通过用户id修改个人信息
     * @param userName:用户姓名
     * @param email:邮箱
     * @param gender:性别
     * @param userId:用户id
     */
    void updateUser(String userName, String email, String gender, Integer userId);
}
