package cn.stopyc.bean;

import java.util.Date;

/**
 * @program: qg-engineering-management-system
 * @description: 对应前端的消息对象
 * @author: stop.yc
 * @create: 2022-04-27 00:22
 **/
public class MyNotice {

    /**
     * 任务id
     */
    private Integer noticeId;

    /**
     * 通知者的姓名
     */
    private String notifier;


    /**
     * 通知内容
     */
    private String content;


    /**
     * 通知时间
     */
    private Date time;


    @Override
    public String toString() {
        return "MyNotice{" +
                "noticeId=" + noticeId +
                ", notifier='" + notifier + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNotifier() {
        return notifier;
    }

    public void setNotifier(String notifier) {
        this.notifier = notifier;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public MyNotice() {
    }

    public MyNotice(Integer noticeId, String notifier, String content, Date time) {
        this.noticeId = noticeId;
        this.notifier = notifier;
        this.content = content;
        this.time = time;
    }
}
