package cn.stopyc.bean;

import java.util.Date;

/**
 * @program: qg-engineering-management-system
 * @description: 我的队伍, 对应前端对象
 * @author: stop.yc
 * @create: 2022-04-19 21:46
 **/
public class MyTeam {

    /**
     * 成员姓名
     */
    private String teamMemberName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务难度
     */
    private Integer level;

    /**
     * 截止日期
     */
    private Date deadline;

    /**
     * 任务完成状态
     */
    private Integer status;

    /**
     * 用户id
     */
    private Integer userId;

    @Override
    public String toString() {
        return "MyTeam{" +
                "teamMemberName='" + teamMemberName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", level=" + level +
                ", deadline=" + deadline +
                ", status=" + status +
                ", userId=" + userId +
                ", taskId=" + taskId +
                '}';
    }

    public String getTeamMemberName() {
        return teamMemberName;
    }

    public void setTeamMemberName(String teamMemberName) {
        this.teamMemberName = teamMemberName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public MyTeam() {
    }

    public MyTeam(String teamMemberName, String taskName, Integer level, Date deadline, Integer status, Integer userId, Integer taskId) {
        this.teamMemberName = teamMemberName;
        this.taskName = taskName;
        this.level = level;
        this.deadline = deadline;
        this.status = status;
        this.userId = userId;
        this.taskId = taskId;
    }

    /**
     * 任务id
     */
    private Integer taskId;



}
