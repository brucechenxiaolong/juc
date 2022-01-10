package com.java.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MysqlDemo {
    public static void main(String[] args) {
        SqlDBUtils sqlDBUtils = new SqlDBUtils("com.mysql.jdbc.Driver","jdbc:mysql://localhost/db2019?useUnicode=true&characterEncoding=utf-8","root","root");

        //1.使用多线程批量插入数据到表中
//        for (int i = 0; i < 20; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName());
//                    batchInsert(sqlDBUtils);
//                }
//            }).start();
//        }

        //2.使用多线程修改同一张表的同一条数据，看看结果是什么
        //关系型数据库都遵循：ACID原则
        for (int i = 0; i < 10; i++) {
            int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    update(sqlDBUtils, j);
                }
            }).start();
        }

    }

    /**
     * 修改信息
     * @param sqlDBUtils
     * @param j
     */
    private static void update(SqlDBUtils sqlDBUtils, int j) {

        String sql = "update pdes_user set dep_name='部门_"+j+"' where user_name='张三'";

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = sqlDBUtils.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.execute();
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sqlDBUtils.closeConnection(conn);
            }
        }
    }

    /**
     * 批量新增
     * @param sqlDBUtils
     */
    private static void batchInsert(SqlDBUtils sqlDBUtils) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = sqlDBUtils.getConnection();

            String insertSql = "INSERT INTO students(id,student_name,age,card_id) VALUES(" +
                    "?," +
                    "?," +
                    "?," +
                    "?)";
            ps = conn.prepareStatement(insertSql);

            for (int i = 1; i <= 10000; i++) {
                try {
                    ps.setString(1, UUID.randomUUID().toString().replace("-",""));
                    ps.setString(2, "lifangping" + i);
                    ps.setInt(3, 36);
                    ps.setString(4, "9221221889062" + i);
                    ps.addBatch();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ps.executeBatch();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sqlDBUtils.closeConnection(conn);
            }
        }
    }
}
