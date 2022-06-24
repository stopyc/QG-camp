package cn.stopyc.service;

import java.sql.ResultSet;

/**
 * @author YC104
 */
public interface IResultSetHandler<T>{

    /**
     * 接口中抽象方法,实现对结果集的处理,返回所指定的类型
     * @param rs:查询结果集
     * @return: 返回指定数据
     * @throws Exception :
     */
    T handle(ResultSet rs) throws Exception;
}
