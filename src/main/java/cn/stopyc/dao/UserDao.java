package cn.stopyc.dao;

import cn.stopyc.po.User;

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
}
