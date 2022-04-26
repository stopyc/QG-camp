package cn.stopyc.po;

import java.util.Date;

/**
 * @program: qg-engineering-management-system
 * @description: 通知类
 * @author: stop.yc
 * @create: 2022-04-26 21:12
 **/
public class Notice {

    /**
     * 通知信息的id
     */
    private Integer noticeId;

    /**
     * 通知者的id
     */
    private Integer notifierId;


    /**
     * 通知内容
     */
    private String content;


    /**
     * 通知时间
     */
    private Date time;

    /**
     * 被通知者的id
     */
    private Integer notifiedId;

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", notifierId=" + notifierId +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", notifiedId=" + notifiedId +
                '}';
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getNotifierId() {
        return notifierId;
    }

    public void setNotifierId(Integer notifierId) {
        this.notifierId = notifierId;
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

    public Integer getNotifiedId() {
        return notifiedId;
    }

    public void setNotifiedId(Integer notifiedId) {
        this.notifiedId = notifiedId;
    }

    public Notice() {
    }

    public Notice(Integer noticeId, Integer notifierId, String content, Date time, Integer notifiedId) {
        this.noticeId = noticeId;
        this.notifierId = notifierId;
        this.content = content;
        this.time = time;
        this.notifiedId = notifiedId;
    }
}
