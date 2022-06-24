package cn.stopyc.pool;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @program: my-database-pool02
 * @description:
 * @author: stop.yc
 * @create: 2022-04-03 11:03
 **/
public class MyDataSource implements MyDataSourceInterface {

    public static String url = null;
    public static String driver = null;
    public static String username = null;
    public static String password = null;
    //最大的空闲连接数

    public static Integer poolMaxActionConnections = null;
    //最小的空闲连接数

    public static Integer poolMinActionConnections = null;

    //从连接池中获取一个连接最大等待多久时间(毫秒)

    public static Integer poolTimeToWait = null;

    private static List<Connection> pool = new LinkedList<Connection>();

    private static Integer activePool = 0;

    //监视器对象

    private static final Object monitor = new Object();


    static {
        try {
            InputStream in = MyDataSource.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(in);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            poolMaxActionConnections = new Integer(properties.getProperty("poolMaxActionConnections")) ;
             poolMinActionConnections = new Integer(properties.getProperty("poolMinActionConnections")) ;
             poolTimeToWait = new Integer(properties.getProperty("poolTimeToWait")) ;
            Class.forName(driver);
            //首先先初始化三个连接池
            for (Integer i = 0; i < poolMinActionConnections; i++) {
                Connection con = DriverManager.getConnection(url,username,password);
                pool.add(con);
                activePool++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * @Description: 拿连接
    * @Param: []
    * @return: java.sql.Connection
    * @Author: stop.yc
    * @Date: 2022/4/3
    */
    public static Connection getConn() throws SQLException {
        Connection conn = null;
        //如果连接为空,表示刚进来方法,或者是连接池没有连接,需要等待继续下次获取
        while (conn == null) {
            synchronized (monitor){
                //目前连接池没有东西
                if (pool.isEmpty()) {
                    //有两种情况,一种是还没到达最大数量被取完,那么就需要继续开启新的连接
                    if (activePool < poolMaxActionConnections) {
                        //新建一个
                        conn = DriverManager.getConnection(url,username,password);
                        //那么现在拥有的连接就多了一个
                        activePool++;
                    }
                    //另一种是已经达到最大的数量,但是被全部拿走了,那么这时候就不能创建了,只能等待了
                }
                //目前连接池还有,那么你就直接拿走
                else {
                    conn = pool.remove(0);
                }
                //如果还是等于null,就表示他只能是等待了
                if (conn == null) {
                    try {
                        long currentTimeMillis = System.currentTimeMillis();
                        monitor.wait(poolTimeToWait);
                        long disTime = System.currentTimeMillis() - currentTimeMillis;
                        if (disTime >= poolTimeToWait) {
                            throw new Exception("获取连接超时");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }

        }
        return conn;
    }

    public static void release(Connection conn, Statement st,ResultSet rs) {

        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (st != null && !st.isClosed()) {
                st.close();
            }
            synchronized (monitor) {
                if (conn != null && !conn.isClosed()) {
                    //如果池子中还有空位的话
                    if (pool.size() < poolMaxActionConnections) {
                        //那么就补进去
                        pool.add(conn);
                    }else {
                        //否则就直接关闭,一般不会吧感觉,因为最大数是固定好的
                        conn.close();
                    }
                    //通知上面的监视器说不用等待了,已经有连接可以拿了
                    monitor.notifyAll();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
