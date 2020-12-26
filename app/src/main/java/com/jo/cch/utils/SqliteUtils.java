package com.jo.cch.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class SqliteUtils {

	private static String url;
    private  static String driver;

    /**
     * 文件读取，只会执行一次，使用静态代码块
     */
    static {

        //4.注册驱动
        try {
            String courseFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + File.separator + "cchdata" + File.separator;
            url = "jdbc:sqlite:" + courseFile + "word.mbtiles";
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取连接
     * @return 连接对象
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, null, null);
        return conn;
    }

    /**
     * 释放资源
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st,Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
