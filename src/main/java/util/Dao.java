/**
 * 
 */
package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/*import com.chinaredstar.restapi.enity.DBInfo;*/

import comm.Config;

/**
 * 数据库操作
 * @author fei.zhang
 * @date 2017年3月26日
 */
public class Dao {

	public static void main(String[] args) {
	}

	// private Connection conn = null;
	// PreparedStatement statement = null;

	public Connection connSql() {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbUrl(Config.getProp("dbUrl"));
		dbInfo.setDbUser(Config.getProp("dbUser"));
		dbInfo.setDbPwd(Config.getProp("dbPwd"));
		String dbUrl = dbInfo.getDbUrl();
		String dbUserName = dbInfo.getDbUser();
		String dbPwd = dbInfo.getDbPwd();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载驱动
			conn = DriverManager.getConnection(dbUrl, dbUserName, dbPwd);
		}
		// 捕获加载驱动程序异常
		catch (ClassNotFoundException cnfex) {
			System.err.println("装载 JDBC/ODBC 驱动程序失败。");
			cnfex.printStackTrace();
		}
		// 捕获连接数据库异常
		catch (SQLException sqlex) {
			System.err.println("无法连接数据库");
			sqlex.printStackTrace();
		}
		return conn;
	}

	public Connection connSql2(String dbUrl, String dbUserName, String dbPwd) {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbUrl(Config.getProp(dbUrl));
		dbInfo.setDbUser(Config.getProp(dbUserName));
		dbInfo.setDbPwd(Config.getProp(dbPwd));
		String dbUrl2 = dbInfo.getDbUrl();
		String dbUserName2 = dbInfo.getDbUser();
		String dbPwd2 = dbInfo.getDbPwd();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载驱动
			conn = DriverManager.getConnection(dbUrl2, dbUserName2, dbPwd2);
		}
		// 捕获加载驱动程序异常
		catch (ClassNotFoundException cnfex) {
			System.err.println("装载 JDBC/ODBC 驱动程序失败。");
			cnfex.printStackTrace();
		}
		// 捕获连接数据库异常
		catch (SQLException sqlex) {
			System.err.println("无法连接数据库");
			sqlex.printStackTrace();
		}
		return conn;
	}

	// // 关闭数据库
	// public void deconnSql() {
	// try {
	// if (conn != null)
	// conn.close();
	// } catch (Exception e) {
	// System.out.println("关闭数据库异常：");
	// e.printStackTrace();
	// }
	// }

	/**
	 * 执行查询sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet selectSql(String sql) {
		Connection conn = connSql();
		PreparedStatement statement;
		ResultSet rs = null;

		try {
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet selectSql2(String sql, String dbUrl, String dbUserName, String dbPwd) {
		Connection conn = connSql2(dbUrl, dbUserName, dbPwd);
		PreparedStatement statement;
		ResultSet rs = null;

		try {
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	/**
	 * 执行插入sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public int insertSql(String sql) {
		Connection conn = connSql();
		PreparedStatement statement;
		int i = 0;
		try {
			statement = conn.prepareStatement(sql);
			i = statement.executeUpdate();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("插入数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("插入时出错：");
			e.printStackTrace();
		}
		return i;
	}

	public int insertSql2(String sql, String dbUrl, String dbUserName, String dbPwd) {
		Connection conn = connSql2(dbUrl, dbUserName, dbPwd);
		PreparedStatement statement;
		int i = 0;
		try {
			statement = conn.prepareStatement(sql);
			i = statement.executeUpdate();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("插入数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("插入时出错：");
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 执行删除sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public int deleteSql(String sql) {
		Connection conn = connSql();
		PreparedStatement statement;
		int i = 0;
		try {
			statement = conn.prepareStatement(sql);
			i = statement.executeUpdate();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("删除数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("删除时出错：");
			e.printStackTrace();
		}
		return i;
	}

	public int deleteSql2(String sql, String dbUrl, String dbUserName, String dbPwd) {
		Connection conn = connSql2(dbUrl, dbUserName, dbPwd);
		PreparedStatement statement;
		int i = 0;
		try {
			statement = conn.prepareStatement(sql);
			i = statement.executeUpdate();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("删除数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("删除时出错：");
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 执行更新sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public int updateSql(String sql) {
		Connection conn = connSql();
		PreparedStatement statement;
		int i = 0;
		try {
			statement = conn.prepareStatement(sql);
			i = statement.executeUpdate();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("更新数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("更新时出错：");
			e.printStackTrace();
		}
		return i;
	}

	public int updateSql2(String sql, String dbUrl, String dbUserName, String dbPwd) {
		Connection conn = connSql2(dbUrl, dbUserName, dbPwd);
		PreparedStatement statement;
		int i = 0;
		try {
			statement = conn.prepareStatement(sql);
			i = statement.executeUpdate();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("更新数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("更新时出错：");
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 打印结果集
	 * 
	 * 具体列根据自己的数据库表结构更改
	 * 
	 * @param rs
	 */
	public void printres(ResultSet rs) {
		System.out.println("-----------------");
		System.out.println("查询结果:");
		System.out.println("-----------------");

		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnNumber; i++) {
					if (i > 1)
						System.out.print(", ");
					String columnValue = rs.getString(i);// 列值
					System.out.print(rsmd.getColumnName(i) + " " + columnValue);
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			System.out.println("显示时数据库出错。");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("显示出错。");
			e.printStackTrace();
		}
	}

	/**
	 * 输入sql、列名，从数据库回取字段值(String)
	 * @param sql
	 * @param columnName
	 * @return
	 */
	public String selectSqlToStr(String sql, String columnName) {// columnName是返回数据的字段名

		Connection conn = connSql();
		PreparedStatement statement;
		String result = null;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				result = rs.getString(columnName);
			}

			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	public String selectSqlToStr2(String sql, String columnName, String dbUrl, String dbUserName, String dbPwd) {// columnName是返回数据的字段名

		Connection conn = connSql2(dbUrl, dbUserName, dbPwd);
		PreparedStatement statement;
		String result = null;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				result = rs.getString(columnName);
			}

			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 输入数据库信息、sql、列名，从数据库回取字段值(int)
	 * @param sql
	 * @param columnName
	 * @return
	 */
	public int selectSqlToInt(String sql, String columnName) {// columnName是返回数据的字段名
		Connection conn = connSql();
		PreparedStatement statement;
		int result = 0;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				result = rs.getInt(columnName);
			}

			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int selectSqlToInt2(String sql, String columnName, String dbUrl, String dbUserName, String dbPwd) {// columnName是返回数据的字段名
		Connection conn = connSql2(dbUrl, dbUserName, dbPwd);
		PreparedStatement statement;
		int result = 0;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				result = rs.getInt(columnName);
			}

			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 输入查询sql，返回map
	 * @param sql
	 * @return
	 */
	public ResultSet selectSqlToSet(String sql) {
		// connSql();
		ResultSet resultSet = selectSql(sql);
		return resultSet;
	}

	public ResultSet selectSqlToSet2(String sql, String dbUrl, String dbUserName, String dbPwd) {
		// connSql();
		ResultSet resultSet = selectSql2(sql, dbUrl, dbUserName, dbPwd);
		return resultSet;
	}

}

