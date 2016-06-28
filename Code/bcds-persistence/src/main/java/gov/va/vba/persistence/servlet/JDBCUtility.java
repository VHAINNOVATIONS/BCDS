package gov.va.vba.persistence.servlet;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtility {
	private static Connection connection = null;
	
	public static Connection getConnection() throws Exception {
		if (connection != null) {
			return connection;
		} else {
			String serverName = "IBCDS408";
			String portNumber = "1521";
			String sid = "DEV.BCDSS";
			String dbUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection(dbUrl, "BCDSS_DEV", "developmentonly");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return connection;
		}
	}
}