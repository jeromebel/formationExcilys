package com.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public enum DAOfactory {
	INSTANCE;
	private LogDAO logDao;
	@Autowired
	private BasicDataSource dataSource;

	final Logger LOG = LoggerFactory.getLogger(DAOfactory.class);

	final private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	final private String user = "root";
	final private String passwd = "admin";


	public LogDAO getLogDAO() {
		return logDao;
	}

	public Connection getConnection() {
		LOG.info("RETURNING CONNECTION");

		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error("SQL error to return connection");
		}
		
		return null;
	}

}
