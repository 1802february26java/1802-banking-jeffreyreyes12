package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private static final String url = "jdbc:oracle:thin:@myrevaturerds.cav0wzdoso3h.us-east-1.rds.amazonaws.com:1521:ORCL";
	private static final String username =  "BANKING_DB";
	private static final String password = "banker";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
}
