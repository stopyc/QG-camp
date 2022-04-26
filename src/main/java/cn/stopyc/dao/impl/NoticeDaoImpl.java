package cn.stopyc.dao.impl;

import cn.stopyc.dao.NoticeDao;
import cn.stopyc.util.CRUDUtils;

/**
 * @program: qg-engineering-management-system
 * @description: 通知dao实现类
 * @author: stop.yc
 * @create: 2022-04-26 21:17
 **/
public class NoticeDaoImpl implements NoticeDao {


    @Override
    public void sendNotice(String sql, Object[] msg) {
        CRUDUtils.update(sql,msg);
    }
}
