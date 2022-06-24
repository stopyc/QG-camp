package cn.stopyc.util;

import cn.stopyc.pool.MyDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @program: Dream
 * @description: 数据库工具类
 * @author: stop.yc
 * @create: 2022-03-18 10:57
 **/
public class DbUtil {

    public static Connection getConn () throws SQLException {
        return MyDataSource.getConn();
    }

    public static void release(Connection conn, Statement st, ResultSet rs) {
        MyDataSource.release(conn,st,rs);
    }

}
