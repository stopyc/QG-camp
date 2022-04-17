package cn.stopyc.service.impl;

import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.UserDao;
import cn.stopyc.dao.impl.UserDaoImpl;
import cn.stopyc.po.User;
import cn.stopyc.service.UserService;

/**
 * @program: qg-engineering-management-system
 * @description: UserService实现类
 * @author: stop.yc
 * @create: 2022-04-16 16:20
 **/
public class UserServiceImpl implements UserService {


    /**
     * 私有构造器
     */
    private UserServiceImpl(){

    }


    @Override
    public Result<User> login(String userName, String password) {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //1.dao类进行查询登录的用户信息
        User user = userDao.selectByNameAndPassword(userName, password);

        if (user == null) {
            //2.如果找不到这个用户,那么就是你密码输入错误了,用户名已经通过异步查询进行筛了一次了
            return new Result<>(ResultEnum.PASSWORD_FAILED.getCode(),ResultEnum.PASSWORD_FAILED.getMsg());
        } else {
            //3.如果对象不为null,那么就表示找到了这个对象,表示成功了
            //4.登录成功,应该返回对象,但是不应该包含用户的敏感信息
            user.setPassword("");
            return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
        }
    }

    @Override
    public Result<User> select(String userName) {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //1.根据用户姓名找人
        User user = userDao.selectByName(userName);

        if (user == null) {
            //2.如果找不到这个用户,提示用户名不存在
            return new Result<>(ResultEnum.FIND_USER_FAILED.getCode(),ResultEnum.FIND_USER_FAILED.getMsg());
        } else {
            //3.如果对象不为null,那么就表示找到了这个对象,表示成功了
            //4.登录成功,应该返回对象,但是不应该包含用户的敏感信息
            user.setPassword("");
            return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
        }
    }
}
