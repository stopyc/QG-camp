package cn.stopyc.dao;

import cn.stopyc.po.Notice;

import java.util.List;

/**
* @Description: 通知dao
* @Param:
* @return:
* @Author: stop.yc
* @Date: 2022/4/26
*/
public interface NoticeDao {

    /**
     * 发送消息
     * @param sql:sql语句
     * @param msg:信息;
     */
    void sendNotice(String sql, Object[] msg);


    /**
     * 通过用户id获取通知信息集合
     * @param userId:用户id
     * @return: 返回通知集合
     */
    List<Notice> getNotice(Integer userId);


    /**
     * 删除任务
     * @param sql:sql
     */
    void deleteNotice(String sql);
}
