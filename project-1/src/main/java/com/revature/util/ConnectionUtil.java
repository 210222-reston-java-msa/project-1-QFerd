package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	

		private static Logger log = Logger.getLogger(ConnectionUtil.class);
		
		public static Connection getConnection() {
			
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				log.warn("Cannot load the driver");
				e.printStackTrace();
			}
			
			
			Properties props = new Properties();

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Connection conn = null;
			
			try {
				props.load(loader.getResourceAsStream("connection.properties"));

				String url = props.getProperty("url"); 

				String username = props.getProperty("username");

				String password = props.getProperty("password");
				
				try {
					conn = DriverManager.getConnection(url, username, password);
					log.info("Connection successful");
				} catch (SQLException e) {
					log.warn("Unable to obtain a connection to the database");
				}
				
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			log.info("Connected to db");
			return conn;
		}
		
}
