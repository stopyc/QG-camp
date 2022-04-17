package cn.stopyc.service;

import cn.stopyc.constant.Result;
import cn.stopyc.po.User;

/**
* @Description: 用户service层
* @Param:
* @return:
* @Author: stop.yc
* @Date: 2022/4/16
*/
public interface UserService {

    /**
     * 登录功能
     * @param userName:用户姓名
     * @param password:用户密码
     * @return
     */
    Result<User> login(String userName, String password);


    /**
     * 根据用户名查询用户
     * @param userName:需要查询的用户姓名
     * @return
     */
    Result<User> select(String userName);
}
