package cn.stopyc.service.impl;

import cn.stopyc.bean.MyNotice;
import cn.stopyc.bean.SingletonFactory;
import cn.stopyc.constant.Result;
import cn.stopyc.constant.ResultEnum;
import cn.stopyc.dao.NoticeDao;
import cn.stopyc.dao.UserDao;
import cn.stopyc.dao.impl.NoticeDaoImpl;
import cn.stopyc.po.Notice;
import cn.stopyc.po.User;
import cn.stopyc.service.NoticeService;
import cn.stopyc.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: qg-engineering-management-system
 * @description: 通知业务接口实现类
 * @author: stop.yc
 * @create: 2022-04-26 21:16
 **/

public class NoticeServiceImpl implements NoticeService {


    @Override
    public void sendNotice(User user, List<String> usernames, String msg) {

        //不需要发通知了
        if (usernames.size() == 0) {
            return;
        }

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

    @Override
    public Result<List<MyNotice>> getNotice(String username) {

        //1.获取dao
        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        NoticeDao noticeDao = SingletonFactory.getNoticeDaoSingleton();

        //2.通过用户名获取用户id
        User user = userDao.selectByName(username);
        Integer userId = user.getUserId();

        //3.获取被通知者id是用户id的通知集合,可能为空
        List<Notice> notices = noticeDao.getNotice(userId);
        if (notices.size() == 0) {
            return new Result<>(ResultEnum.NO_NOTICE.getCode(),ResultEnum.NO_NOTICE.getMsg());
        }

        //4.封装java对象,获取notices中所有的被通知者的id集合,,去重后,然后进行查找用户
        List<Integer> userIdList = new ArrayList<>();
        for (Notice notice : notices) {
            userIdList.add(notice.getNotifierId());
        }

        //5.去重
        userIdList = userIdList.stream().distinct().collect(Collectors.toList());

        //6.获取所有用户对象
        StringBuilder sb = new StringBuilder("select * from `t_user` where `userId` in (");
        for (Integer id : userIdList) {
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        String sql = sb.toString();
        System.out.println(sql);
        System.out.println(userIdList);
        List<User> users = userDao.getUsersByUserIds(sql);

        //7.封装
        List<MyNotice> list = new ArrayList<>();
        for (Notice notice : notices) {
            for (User user1 : users) {
                if (user1.getUserId().equals(notice.getNotifierId())) {
                    list.add(new MyNotice(notice.getNoticeId(),user1.getUserName(),notice.getContent(),notice.getTime()));
                }
            }
        }
        System.out.println(list);

        return new Result<>(200,"",list);
    }

    @Override
    public Result<Object> deleteNoticeByIds(int[] deleteIds) {

        //1.拼接字符串
        StringBuilder sb = new StringBuilder("delete from `t_notice` where `noticeId` in (");
        for (Integer id : deleteIds) {
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        String sql = sb.toString();

        //2.获取dao
        NoticeDao noticeDao = SingletonFactory.getNoticeDaoSingleton();

        //3.调用方法
        noticeDao.deleteNotice(sql);

        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());


    }
}
