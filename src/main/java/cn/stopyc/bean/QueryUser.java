package cn.stopyc.bean;

import java.util.Date;

/**
 * @program: qg-engineering-management-system
 * @description: 前端查询对象
 * @author: stop.yc
 * @create: 2022-04-21 19:39
 **/
public class QueryUser {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 任务id
     */
    private Integer taskId;

    /**
     * 上级姓名;
     */
    private String bossName;

    /**
     * 上级id
     */
    private Integer bossId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 入职时间
     */
    private Date hireDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public QueryUser() {
    }

    @Override
    public String toString() {
        return "QueryUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", gender=" + gender +
                ", taskId=" + taskId +
                ", bossName='" + bossName + '\'' +
                ", bossId=" + bossId +
                ", taskName='" + taskName + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }

    public QueryUser(Integer userId, String userName, Integer gender, Integer taskId, String bossName, Integer bossId, String taskName, Date hireDate) {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.taskId = taskId;
        this.bossName = bossName;
        this.bossId = bossId;
        this.taskName = taskName;
        this.hireDate = hireDate;
    }
}
