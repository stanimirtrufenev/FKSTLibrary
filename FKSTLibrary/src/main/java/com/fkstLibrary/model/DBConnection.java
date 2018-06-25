package com.bookLords.model;

import java.sql.DriverManager;
import java.sql.Connection;

public class DBConnection {
	private static final String JDBC_MYSQL_LOCALHOST_3306_DIPLOMA = "jdbc:mysql://localhost:3306/book_lords?autoReconnect=true&useSSL=false";
	private static final String DB_PASSWORD = "admin";
	private static final String DB_USERNAME = "root";
	private static DBConnection instance;
	private Connection connection;

	private DBConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(JDBC_MYSQL_LOCALHOST_3306_DIPLOMA, DB_USERNAME, DB_PASSWORD);
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			try {
				instance = new DBConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}
