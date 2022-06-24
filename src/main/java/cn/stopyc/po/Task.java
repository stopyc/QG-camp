package cn.stopyc.po;


import java.util.Date;

/**
 * @program: qg-engineering-management-system
 * @description: 任务类
 * @author: stop.yc
 * @create: 2022-04-17 22:32
 **/
public class Task {

    /**
     * 任务id
     */
    private Integer taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 所属用户的id
     */
    private Integer userId;
    /**
     * 难度等级
     */
    private Integer level;
    /**
     * 截止日期
     */
    private Date deadline;
    /**
     * 父级任务的id
     */
    private Integer parentTaskId;
    /**
     * 任务完成状态
     */
    private Integer status;

    public Task(Integer taskId, String taskName, Integer userId, Integer level, Date deadline, Integer parentTaskId, Integer status, Integer generalId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.userId = userId;
        this.level = level;
        this.deadline = deadline;
        this.parentTaskId = parentTaskId;
        this.status = status;
        this.generalId = generalId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", userId=" + userId +
                ", level=" + level +
                ", deadline=" + deadline +
                ", parentTaskId=" + parentTaskId +
                ", status=" + status +
                ", generalId=" + generalId +
                '}';
    }

    public Integer getGeneralId() {
        return generalId;
    }

    public void setGeneralId(Integer generalId) {
        this.generalId = generalId;
    }

    private Integer generalId;


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Integer parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Task(Integer taskId, String taskName, Integer userId, Integer level, Date deadline, Integer parentTaskId, Integer status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.userId = userId;
        this.level = level;
        this.deadline = deadline;
        this.parentTaskId = parentTaskId;
        this.status = status;
    }

    public Task() {
    }
}
