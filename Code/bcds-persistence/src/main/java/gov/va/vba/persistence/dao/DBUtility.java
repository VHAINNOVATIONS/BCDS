package gov.va.vba.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	private static Connection connection = null;
	
	public static Connection getConnection() throws Exception {
		if (connection != null) {
			return connection;
		} else {
			String serverName = "127.0.0.1";
			String portNumber = "1521";
			String sid = "Dev";
			String dbUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;

			Class.forName("oracle.jdbc.driver.OracleDriver");

			connection = DriverManager.getConnection(dbUrl, "BCDSS_DEV", "developmentonly");
			return connection;
		}
	}
}