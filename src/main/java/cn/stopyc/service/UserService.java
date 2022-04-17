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


    /**
     * 验证码校验
     * @param checkCode:用户输入
     * @param checkCodeGen:生成的
     * @return
     */
    Result checkCheckCode(String checkCode,String checkCodeGen);


    /**
     * 用户注册
     * @param userName:姓名
     * @param password:密码
     * @param email:邮箱
     * @param position:职位
     * @return
     */
    Result<User> register(String userName, String password, String email, Integer position);


    /**
     * 通过用户名获取id
     * @param userName:用户名
     * @return
     */
    Integer getIdByName(String userName);
}
