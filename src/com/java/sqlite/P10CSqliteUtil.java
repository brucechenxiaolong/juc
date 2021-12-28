package com.java.sqlite;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * P10C-单机版 sqlite，增删改查操作方法定义
 * 以及原文存储处理
 */
public class P10CSqliteUtil {

    //D:\workspace\test\juc\temp\p10client\data\
    private static final String DB_PATH = (System.getProperty("user.dir").length() == 1 ? "" :System.getProperty("user.dir")) + File.separator
            + "temp" + File.separator + "p10client" + File.separator + "data" + File.separator;//sqlite.db存放路径

    private static final String DB_NAME = "p10c.db";//sqlite.db文件名称

    private static final String ORIGINAL_FILE_PATH = (System.getProperty("user.dir").length() == 1 ? "" :System.getProperty("user.dir")) + File.separator
            + "temp" + File.separator + "p10client" + File.separator + "data" + File.separator + "file" + File.separator;//原文存储路径

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement prep = null;
        String dbPath = DB_PATH + DB_NAME;//D:\workspace\test\juc\temp\p10client\data\p10c.db
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
            Statement stat = conn.createStatement();
            stat.executeUpdate("INSERT INTO usercc (id,loginname,password,username) VALUES ('2','dc','ias4WEYIzsXOhZKnqIDv0A==','done');");
            //分页时，可以使用ResultSet来完成分页rs.absolute(100)，也可以sql语句中完成分页select ... limit 100,10；
            //下面是释放数据库连接的过程，使用数据库连接池时不应该释放连接，而是将连接重新放到连接池中。
            //以代理的方式生成Connection的对象，调用Connection的close方法时将Connection加入到线程池中。线程池中加入的是Connection的代理对象即可。
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
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
            }
        }
    }


    /**
     * sqlite插入语句
     * @param sql
     * @return
     * @throws Exception
     */
    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement prep = null;
        String targetPath = DB_PATH + DB_NAME;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+targetPath);
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
    public ArrayList executeQuery(String dbPath,String sql) throws SQLException {
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

    /**
     * 清除文件
     */
    public void deleteFile() {
        String targetPath = DB_PATH + DB_NAME;
        File targetfile = new File(targetPath);
        if (targetfile.exists()) {
            targetfile.delete();
        }
    }


}