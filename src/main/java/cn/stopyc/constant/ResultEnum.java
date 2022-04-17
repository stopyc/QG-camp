package cn.stopyc.constant;

public enum ResultEnum {
    //自定义
    //通用
    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(200,"成功"),
    SERVER_INTERNAL_ERROR(500,"服务器内部错误"),
    RESOURCE_NOT_FOUND(404,"资源未找到"),
    PARAMETER_NOT_VALID(400,"参数不合法"),
    DATABASE_ERROR(600,"数据库操作错误"),
    //用户模块 5002XX
    PASSWORD_FAILED(500200,"用户名或密码错误"),
    REPEAT_NAME(500210,"用户名重复"),
    FIND_USER_FAILED(500220,"用户名不存在"),
    CHECK_CODE_ERROR(500230,"验证码错误"),


    ;
    //编号
    private Integer code;
    //信息
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
