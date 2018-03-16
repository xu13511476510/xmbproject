package com.bianlaoshi.new1;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zhuangyuan on 2017/11/14.
 */

public class JDBCoperation {
    static String driver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/zhubajie";
    static String username = "root";
    static String password = "645456278";
    private static Connection conn=null;

    public JDBCoperation(){
        this.conn=getConn();
    }

    public static Connection getConn() {
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
            Log.v("connect","Success loading Mysql Driver!");
        } catch (ClassNotFoundException e) {
            Log.e("connect","Error loading Mysql Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
