package cn.stopyc.service;

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
}
