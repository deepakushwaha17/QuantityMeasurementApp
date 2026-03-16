package com.apps.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

	private static ConnectionPool instance;

	private final List<Connection> pool = new ArrayList<>();

	private static final int POOL_SIZE = 5;

	private ConnectionPool() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/quantity_measurement_db";
			String user = "root";
			String password = "Root@123";

			for (int i = 0; i < POOL_SIZE; i++) {

				Connection conn = DriverManager.getConnection(url, user, password);
				pool.add(conn);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error creating connection pool", e);
		}
	}

	public static synchronized ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}

		return instance;
	}

	public synchronized Connection getConnection() {
		if (pool.isEmpty()) {
			throw new RuntimeException("No DB connections available");
		}

		return pool.remove(pool.size() - 1);
	}

	public synchronized void releaseConnection(Connection conn) {
		pool.add(conn);
	}
}