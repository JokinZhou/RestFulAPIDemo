package util;
/**
 * 数据库相关信息，区分不同环境
 * @author fei.zhang
 * @date 2017年3月26日
 */
public class DBInfo {

	private String dbUrl = "jdbc:mysql://db201.uat1.rs.com:3306/";// 数据库地址
	private String dbUser = "qa";// 数据库账号
	private String dbPwd = "dGxlX2J5X2";// 数据库密码

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

}
