package main.java.gov.va.vba.persistence.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {
	private static DBConnectionFactory instance = new DBConnectionFactory();
	public static final String serverName = "IBCDS408";
	public static final String portNumber = "1521";
	public static final String sid = "DEV.BCDSS";
	public static final String dbUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
	public static final String dbuser = "BCDSS";
	public static final String dbpassword = "developmentonly";
	public static final String driver_class = "com.mysql.jdbc.Driver";

	private DBConnectionFactory() {
        try {
            Class.forName(driver_class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbuser, dbpassword);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }   
     
    public static Connection getConnection() {
        return instance.createConnection();
    }
}