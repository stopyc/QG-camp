package cn.stopyc.dao;

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
}
