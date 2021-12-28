package com.java.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc 连接数据查询
 * 需要引入jar包：sqljdbc4-4.0.jar
 * com.microsoft.sqlserver.jdbc.SQLServerDriver
 */
public class JDBCDemo {

    private String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String jdbcUrl = "jdbc:sqlserver://10.20.40.111:1433;DatabaseName=statistics";
    private String user = "sa";
    private String passWord = "Pde888";

    private Map<String, Object> selectData1(){

        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<String> todayData = new ArrayList<String>();
        // 此处只是随便调用的库
        String sql = "SELECT MC,SL from s_zhtj WHERE BH=1 AND MC LIKE ? ORDER BY PX";

        Connection connection = null;
//        Statement stmt = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            //第一步：获取驱动类，注册驱动："com.microsoft.sqlserver.jdbc.SQLServerDriver"
            Class.forName(driverClass);
            //第二步：得到数据库连接
            connection = DriverManager.getConnection(jdbcUrl,user,passWord);
            //第三步：声明，叙述
//            stmt = connection.createStatement();
            //第四步：执行sql脚本
            preparedStatement = connection.prepareStatement(sql);
            //设置参数
            preparedStatement.setString(1,"%2010%");
            rs = preparedStatement.executeQuery();
            //第五步：返回数据集
            while (rs.next()) {
                todayData.add(rs.getString("MC") + "-" + rs.getString("SL"));
                System.out.println(rs.getString("MC") + "-" + rs.getString("SL"));
            }
            returnMap.put("datas", todayData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
        } finally {
            //第六步：关闭：ResultSet，Statement，Connection
            finallyClosed(connection, preparedStatement, rs);
        }
        return returnMap;
    }

    /**
     * 关闭驱动
     * @param connection
     * @param stmt
     * @param rs
     */
    private void finallyClosed(Connection connection, PreparedStatement stmt, ResultSet rs) {
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JDBCDemo xx = new JDBCDemo();
        xx.selectData1();
    }
}
