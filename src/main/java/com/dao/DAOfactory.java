package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public enum DAOfactory {	
	INSTANCE;
	private ComputerDAO computerDao ;
	private CompanyDAO  companyDao  ;
	private LogDAO 		 logDao;
	private BoneCP connectionPool;
	
	private ThreadLocal<Connection> tlConnection;
	
	final Logger LOG = LoggerFactory.getLogger(DAOfactory.class);

	private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String user = "root";
	private String passwd = "admin";
	
	private DAOfactory(){
		computerDao = new ComputerDAO();
		companyDao  = new CompanyDAO();
		logDao		= new LogDAO();
		tlConnection = new ThreadLocal<Connection>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			LOG.info("[BONECP]Connection successfully openned");
		} catch (ClassNotFoundException CNFe) {
			LOG.error("[CLASSNFEXCEPTION]");
			CNFe.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		BoneCPConfig config = new BoneCPConfig();

		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(passwd);

		config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);
		try {
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
		
	}
	
	public LogDAO getLogDAO(){
		return logDao;
	}
	
	public ComputerDAO getComputerDAO(){
		return computerDao;
	}
	
	public CompanyDAO getCompanyDAO(){
		return companyDao;
	}
	
	public void startTransaction(){
		
		try {
			tlConnection.set(connectionPool.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			getConnexion().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void endTransaction(){
		Connection cn = getConnexion();
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
	
	public Connection getConnexion() {	
		LOG.info("[BONECP] RETURNING CONNECTION");
		
		if(tlConnection.get() == null) {
			LOG.error("ThreadLocal NULL");
			return null;
		} 
		
		return tlConnection.get();	
	}
	
}
