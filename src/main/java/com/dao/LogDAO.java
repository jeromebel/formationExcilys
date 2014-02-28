package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDAO {
	
	final Logger LOG = LoggerFactory.getLogger(LogDAO.class);
	
	public void create(long computerId, String description){
		Connection cn = null;
		PreparedStatement stmt = null;
		try {
			cn = DAOfactory.INSTANCE.getConnection();
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
