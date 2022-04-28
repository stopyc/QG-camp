package cn.stopyc.service;

import cn.stopyc.bean.MyNotice;
import cn.stopyc.constant.Result;
import cn.stopyc.po.User;

import java.util.List;

/**
* @Description: 通知业务接口
* @Param:
* @return:
* @Author: stop.yc
* @Date: 2022/4/26
*/
public interface NoticeService {


    /**
     * 发送通知
     * @param user:通知者
     * @param usernames:被通知者
     * @param msg:内容
     */
    void sendNotice(User user, List<String> usernames, String msg);


    /**
     * 通过用户名获取通知
     * @param username :用户名
     * @return: 返回结果集(通知列表)
     */
    Result<List<MyNotice>> getNotice(String username);


    /**
     * 通过ids删除通知
     * @param deleteIds:通知id数组
     * @return: 返回结果集(成功与否)
     */
    Result<Object> deleteNoticeByIds(int[] deleteIds);
}
