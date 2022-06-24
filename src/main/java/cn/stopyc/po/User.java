package cn.stopyc.po;

import java.util.Date;

/**
 * @program: qg-engineering-management-system
 * @description: 用户类
 * @author: stop.yc
 * @create: 2022-04-16 16:08
 **/
public class User {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户职位:(0:总负责人,1:经理,2:包工头,3:小工)
     */
    private Integer position;
    /**
     * 任务id
     */
    private Integer taskId;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 用户入职时间
     */
    private Date hireDate;

    public Integer getUserId() {
        return userId;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", position=" + position +
                ", taskId=" + taskId +
                ", gender='" + gender + '\'' +
                ", hireDate=" + hireDate +
                ", bossId=" + bossId +
                '}';
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * 用户上级id(总负责人的boss默认是-1)
     */
    private Integer bossId;

    public User(Integer userId, String userName, String password, String email, Integer position, Integer taskId, String gender, Date hireDate, Integer bossId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.position = position;
        this.taskId = taskId;
        this.gender = gender;
        this.hireDate = hireDate;
        this.bossId = bossId;
    }
}
