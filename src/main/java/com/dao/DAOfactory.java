package com.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DAOfactory {
	INSTANCE;
	private LogDAO logDao;
	private BasicDataSource source;

	private ThreadLocal<Connection> tlConnection;

	final Logger LOG = LoggerFactory.getLogger(DAOfactory.class);

	final private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	final private String user = "root";
	final private String passwd = "admin";

	private DAOfactory() {
		logDao = new LogDAO();
		tlConnection = new ThreadLocal<Connection>();

		source = new BasicDataSource();
		source.setDriverClassName("com.mysql.jdbc.Driver");
		source.setUrl(url);
		source.setUsername(user);
		source.setPassword(passwd);
		source.setMaxActive(10);
		source.setInitialSize(5);

	}

	public LogDAO getLogDAO() {
		return logDao;
	}

	public void startTransaction() {

		//récupération de la DataSource à partir du contexte
		
		try {
			Connection c = source.getConnection();
			tlConnection.set(c);
		} catch (SQLException e1) {
			LOG.error("Problem to get connection in Start connection");
		}

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void endTransaction() {
		Connection cn = getConnection();
		try {
			cn.commit();
			cn.setAutoCommit(true);
			cn.close();
			tlConnection.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rollbackTransaction() {
		Connection cn = tlConnection.get();
		if (cn != null) {
			try {
				cn.rollback();
				cn.setAutoCommit(true);
			} catch (SQLException e) {
				LOG.error("SQL error");
			} finally {
				try {
					cn.close();
				} catch (SQLException e) {
					LOG.warn("Problem to close connection");
				}
			}
		}
	}

	public Connection getConnection() {
		LOG.info("RETURNING CONNECTION");

		if (tlConnection.get() == null) {
			LOG.error("ThreadLocal NULL");
			return null;
		}

		return tlConnection.get();
	}

}
