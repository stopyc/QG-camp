package cn.stopyc.service;

import java.sql.ResultSet;

/**
 * @author YC104
 */
public interface IResultSetHandler<T>{

    /**
    * @Description: 接口中抽象方法,实现对结果集的处理,返回所指定的类型
    * @Param: [rs]
    * @return: T
    * @Author: stop.yc
    * @Date: 2022/3/29
    */
    T handle(ResultSet rs) throws Exception;
}
