package com.java.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * jdbc-基础类，操作数据库：增删改查
 */
public class SqlDBUtils {

	protected static String driver;
	protected  static String url;
	protected  static String username;
	protected static String password;
	
	public SqlDBUtils(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, 
					username,
					password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static boolean executeSql(String sql) {
		System.out.println(sql);
		boolean bln = Boolean.TRUE.booleanValue();
		Connection connection = getConnection();
		java.sql.PreparedStatement st = null;
		try {
			connection.setAutoCommit(false);
			st = connection.prepareStatement(sql);
			st.execute();
			connection.commit();
		} catch (SQLException e) {
			System.err.println("sql语句执行异常: " + e.getMessage() + "  " + sql);
			return false;
		} finally {
			try {
				if (st != null)
					st.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			closeConnection(connection);
		}

		return bln;
	}

	public int executeSqlBatch(List sqlList) {
		int[] updateRecordsArray = null;
		int updateRecords = 0;
		int sSize = 0;
		Statement stmt = null;
		Connection conn = getConnection();
		try {
			stmt = conn.createStatement();
			for (int i = 0; i < sqlList.size(); i++) {
				String updateSql = (String) sqlList.get(i);
				stmt.addBatch(updateSql);
				sSize = sSize + updateSql.length();

				if (sSize > 40 * 1024) {
					updateRecordsArray = stmt.executeBatch();
					stmt.clearBatch();
					conn.commit();
					sSize = 0;
					for (int j = 0; j < updateRecordsArray.length; j++)
						updateRecords += updateRecordsArray[j];
				}
			}
			updateRecordsArray = stmt.executeBatch();
		} catch (SQLException e) {
			System.out.println("执行批处理异常：" + e);
			for (int i = 0; i < sqlList.size(); i++) {
				System.out.println("sql异常：" + sqlList.get(i));
				break;
			}
		} finally {
			try {
				if (conn != null)
					conn.commit();
				if (stmt != null)
					stmt.close();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < updateRecordsArray.length; i++)
			updateRecords += updateRecordsArray[i];

		return updateRecords;
	}

	public static String searchSql(String sql) {
		String str = null;
		Connection connection = getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		// System.out.println(sql);
		try {
			connection.setAutoCommit(false);
			st = connection.prepareStatement(sql);
			// connection.setAutoCommit(false);
			// st = connection.prepareStatement(sql);
			rs = st.executeQuery();
			connection.commit();
			if (rs.next())
				str = rs.getString(1);
		} catch (SQLException e) {
			System.err.println("sql语句执行异常: " + e.getMessage() + "  " + sql);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			closeConnection(connection);
		}

		return str;
	}

	public static List searchResult(String sql) {
		System.out.println(sql);
		List list = new ArrayList();
		Connection connection = getConnection();
		ResultSet rs = null;
		Statement st = null;

		try {
			// System.out.println(sql);
			//connection.setAutoCommit(false);
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			connection.commit();
			int colCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String result[] = new String[colCount];
				for (int j = 0; j < colCount; j++) {
					result[j] = rs.getString(j + 1);
				}
				list.add(result);
				result = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				//connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			closeConnection(connection);
		}
		return list;
	}
	
	/**
	 * 查询中间库表数据信息
	 * @param sql
	 * @return
	 */
	public static List searchResultForMidTable(String sql) {
		List list = new ArrayList();
		Connection connection = getConnection();
		ResultSet rs = null;
		Statement st = null;

		try {
			connection.setAutoCommit(false);
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			connection.commit();
			int colCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String result[] = new String[colCount];
				for (int j = 0; j < colCount; j++) {
					result[j] = rs.getString(j + 1);
				}
				list.add(result);
				result = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				//connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			closeConnection(connection);
		}
		return list;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SqlDBUtils sqlDBUtils = new SqlDBUtils("com.mysql.jdbc.Driver","jdbc:mysql://localhost/db2019?useUnicode=true&characterEncoding=utf-8","root","root");

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

			for (int i = 1; i <= 10; i++) {
				try {
					ps.setString(1, UUID.randomUUID().toString().replace("-",""));
					ps.setString(2, "zhangsan" + i);
					ps.setInt(3, 18);
					ps.setString(4, "341122188906231410");
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
