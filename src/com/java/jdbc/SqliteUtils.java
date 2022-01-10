package com.java.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SqliteUtils {

    /**
     * sqlite插入语句
     * @param sql
     * @return
     * @throws Exception
     */
    public int executeUpdate(String sql,String dbPath) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement prep = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            //以代理的方式生成Connection的对象，调用Connection的close方法时将Connection加入到线程池中。线程池中加入的是Connection的代理对象即可。
        } finally {
            if (prep != null) {
                try {
                    prep.close();
                } finally {
                    if (conn != null) {
                        conn.close();
                    }
                }
            }
        }
        return 1;
    }


    /**
     * 查询语句
     * @param dbPath
     * @param sql
     * @return
     * @throws SQLException
     */
    public ArrayList executeQuery(String sql, String dbPath) throws SQLException {
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        ArrayList uCollection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            int ColCount = rs.getMetaData().getColumnCount();
            uCollection = new ArrayList();
            while (rs.next()) {
                HashMap uFieldValues =  new HashMap();
                for (int i = 1; i < ColCount + 1; i++) {
                    String fieldValue = rs.getString(i);
                    if (fieldValue == null) fieldValue = "";
                    uFieldValues.put(rs.getMetaData().getColumnName(i), fieldValue);
                }
                uCollection.add(uFieldValues);
            }
            stat.close();
        } catch (Exception ex) {
            if (prep != null) {
                try {
                    prep.close();
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }
            }
        } finally {
            if (prep != null) {
                try {
                    prep.close();
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }
            }
        }
        return uCollection;
    }
}
