package com.java.jdbc;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class SqliteDemo {

    //跟路径（支持windows或linux）： C:-jfjdag-run
    public static final String ROOT_SRC = (System.getProperty("user.dir").length() == 1 ? "" :System.getProperty("user.dir")) + File.separator;

    //下载路径:offline是sqlite数据库
    public static final String DOWNLOAD_PATH = ROOT_SRC + "read" + File.separator + "download" + File.separator + "offlineread" + File.separator;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SqliteUtils sqlite = new SqliteUtils();

        //修改，删除，出入
        String delSql = getDelSqliteSql();
        String insertSql = getInsertSqliteSql();
        sqlite.executeUpdate(delSql,DOWNLOAD_PATH + "offline");//此路径也可以写死：D:\sqlite\offline\offline

        //查询
        String querySql = querySqliteSql();
        ArrayList list = sqlite.executeQuery(querySql, DOWNLOAD_PATH + "offline");

    }

    /**
     * 清理sqlite表sql语句集合
     * @return
     */
    private static String getDelSqliteSql() {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from approve;");
        sb.append("delete from usercc;");
        sb.append("delete from use_approve;");
        sb.append("delete from variables");
        return sb.toString();
    }

    private static String getInsertSqliteSql() {
        String fix = "INSERT INTO variables(id,relation_id,var_type,content)VALUES('";
        StringBuffer sb = new StringBuffer();
        Map<String, Object> metaData = null;
        metaData.forEach((key, value) -> {
            sb.append(fix);
            sb.append(UUID.randomUUID().toString().replace("-", "") + "',");
            sb.append("'").append(key).append("',");
            sb.append("'xxx'").append(",");
            sb.append("'").append(JSONObject.toJSONString(value)).append("'");
            sb.append(");\n");
        });
        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }

    private static String querySqliteSql() {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from approve where i_state != 0");
        return sb.toString();
    }

}
