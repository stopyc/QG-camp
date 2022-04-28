package cn.stopyc.dao.impl;

import cn.stopyc.dao.NoticeDao;
import cn.stopyc.po.Notice;
import cn.stopyc.service.impl.BeanListHandle;
import cn.stopyc.util.CRUDUtils;

import java.util.List;

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

    @Override
    public List<Notice> getNotice(Integer userId) {
        String sql = "select * from `t_notice` where `notifiedId`=?";
        return CRUDUtils.query(sql,new BeanListHandle<>(Notice.class),userId);
    }

    @Override
    public void deleteNotice(String sql) {
        CRUDUtils.update(sql);
    }
}
