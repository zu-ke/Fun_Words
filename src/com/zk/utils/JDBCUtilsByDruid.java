package com.zk.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 韩顺平
 * @version 1.0
 * 基于druid数据库连接池的工具类
 */
public class JDBCUtilsByDruid {

    private static DataSource ds;

    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();

    //在静态代码块完成 ds初始化
    static {
        Properties properties = new Properties();
        try {
            //类加载器
            //properties.load(new FileInputStream("src\\druid.properties"));
            properties.load(new FileInputStream(JDBCUtilsByDruid.class
                    .getResource("/druid.properties").getPath().substring(1)));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //编写getConnection方法
/*    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }*/

    /*从ThreadLocal获取连接，从而保证在同一个线程中获取的是同一个连接*/
    public static Connection getConnection() {
        Connection connection = threadLocalConn.get();
        if (connection == null) {
            /*当前threadLocalConn中没有连接，就从连接池中取出一个连接*/
            try {
                connection = ds.getConnection();
                /*不要自动提交*/
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            threadLocalConn.set(connection);
        }
        return connection;
    }

    /*提交事务*/
    public static void commit(){
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    /*放回连接池*/
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        threadLocalConn.remove();
    }

    /*回滚事务*/
    public static void rollback(){
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    /*放回连接池*/
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        threadLocalConn.remove();
    }

    //关闭连接, 老师再次强调： 在数据库连接池技术中，close 不是真的断掉连接
    //而是把使用的Connection对象放回连接池
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void zkClose(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    throw new RuntimeException("Error closing resource", e);
                }
            }
        }
    }

}
