package cn.stopyc.service;

import cn.stopyc.bean.MyTeam;
import cn.stopyc.bean.QueryUser;
import cn.stopyc.constant.Result;
import cn.stopyc.po.User;

import java.util.List;

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
    Result<Integer> login(String userName, String password);


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


    /**
     * 根据上级id查找队伍信息
     * @param bossId:上级id
     * @return
     */
    Result<MyTeam> selectMyTeam(Integer bossId);


    /**
     * 上级踢人业务
     * @param userId:被踢的用户id
     */
    void kickMember(Integer userId);


    /**
     * 查询下级用户
     * @param queryUser:封装查询对象
     * @return
     */
    Result<QueryUser> queryUser(User queryUser, Integer sort);


    /**
     * 进队业务
     * @param inTeamUserId:要进队的人的id
     * @param bossName:上级id
     * @return
     */
    Result inTeam(Integer inTeamUserId, String bossName);
}
