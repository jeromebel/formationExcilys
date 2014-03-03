package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class LogDAO {
	
	final Logger LOG = LoggerFactory.getLogger(LogDAO.class);
	
	@Autowired
	private BoneCPDataSource dataSource;
	
	public void create(long computerId, String description){
		Connection cn = null;
		PreparedStatement stmt = null;
		try {
			cn = DataSourceUtils.getConnection(dataSource);
			stmt = (PreparedStatement) cn
					.prepareStatement("INSERT INTO log (description , computer_id )"
							+ "VALUES(?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, description);
			stmt.setLong(2, computerId);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			while(rs.next())
				LOG.info(description +" "+ computerId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();

			} catch (SQLException e) {
			}
		}
	}

}
