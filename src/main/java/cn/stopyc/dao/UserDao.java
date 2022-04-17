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
}
