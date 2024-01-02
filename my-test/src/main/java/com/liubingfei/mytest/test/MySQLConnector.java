package com.liubingfei.mytest.test;
import java.sql.*;

public class MySQLConnector {
    public static void main(String[] args) {
        // 定义数据库连接信息
        String url = "jdbc:mysql://172.21.35.63:3306/zczqdb";
        String user = "qn_zczq";
        String password = "Qn_zczq@2022";

        // 加载JDBC驱动程序
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC驱动程序加载失败！");
            e.printStackTrace();
            return;
        }

        // 建立数据库连接
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
            return;
        } finally {
            // 关闭数据库连接
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("数据库连接已关闭。");
                } catch (SQLException e) {
                    System.err.println("关闭数据库连接出错！");
                    e.printStackTrace();
                }
            }
        }
    }
}