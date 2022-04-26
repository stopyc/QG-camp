package cn.stopyc.service.impl;

import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.dao.NoticeDao;
import cn.stopyc.dao.UserDao;
import cn.stopyc.po.User;
import cn.stopyc.service.NoticeService;
import cn.stopyc.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: qg-engineering-management-system
 * @description: 通知业务接口实现类
 * @author: stop.yc
 * @create: 2022-04-26 21:16
 **/
public class NoticeServiceImpl implements NoticeService {


    @Override
    public void sendNotice(User user, List<String> usernames, String msg) {

        //1.获取dao
        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        NoticeDao noticeDao = SingletonFactory.getNoticeDaoSingleton();

        //2.获取通知者的id
        Integer notifierId = user.getUserId();

        //3.获取消息通知的时间
        String time = TimeUtils.getStrFromCurrentTime(System.currentTimeMillis());

        //4.拼接sql语句,一次性插入多条
        StringBuilder sb = new StringBuilder( "insert into `t_notice` (`notifierId`,`content`,`time`,`notifiedId`) values ");

        //5.?占位符中的很多消息(`content`)
        List<String> msg1 = new ArrayList<>();

        //6.遍历被通知者,拼接字符串,获取?信息
        for (String username : usernames) {
            User u = userDao.selectByName(username);
            msg1.add(msg);
            sb.append("(").append("'"+notifierId+"'").append(",").append("?").append(",").append("'"+time+"'").append(",").append("'"+u.getUserId()+"'").append("),");
        }

        //7.处理sql语句
        sb.deleteCharAt(sb.lastIndexOf(","));
        String sql = sb.toString();

        //8.调用dao
        noticeDao.sendNotice(sql,msg1.toArray());
    }
}
