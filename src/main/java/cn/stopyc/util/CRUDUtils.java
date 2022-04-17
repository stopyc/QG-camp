package cn.stopyc.util;

import cn.stopyc.service.IResultSetHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: Dream
 * @description: 增删改查封装工具类
 * @author: stop.yc
 * @create: 2022-03-18 11:56
 **/
public class CRUDUtils {


    /**
    * @Description: 增删改的sql封装
    * @Param: [sql, params]
    * @return: int
    * @Author: stop.yc
    * @Date: 2022/3/18
    */
    public static int update(String sql,Object... params){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取连接
            con = DbUtil.getConn();
            con.setAutoCommit(false);
            //2.执行sql语句
            ps = con.prepareStatement(sql);
            //  params.length 获取可变长度的长度
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1,params[i]);
            }
            System.out.println(ps);
            int i = ps.executeUpdate();
            con.commit();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {

                DbUtil.release(con,ps,rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
    * @Description:封装查询语句
    * @Param: [sql, handler, params]
    * @return: T
    * @Author: stop.yc
    * @Date: 2022/3/29
    */
    public static <T> T query(String sql, IResultSetHandler<T> handler, Object... params) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取连接
            con = DbUtil.getConn();
            //2.执行查询
            ps = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1,params[i]);
            }
            rs = ps.executeQuery();
            return handler.handle(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.release(con,ps,rs);
        }
        return null;
    }
}
