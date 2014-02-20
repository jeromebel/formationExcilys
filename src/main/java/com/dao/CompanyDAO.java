package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.om.Company;

public class CompanyDAO {
	
	final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
		
	public List<Company> readAll(){
		
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;
		List<Company> companies = new ArrayList<Company>();
		
		try {
			cn = DAOfactory.INSTANCE.getConnexion();
			stmt = (Statement) cn.createStatement();
			rs = (ResultSet) stmt
					.executeQuery("SELECT * FROM company ;");
			while (rs.next()) {
				Company c = new Company();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				companies.add(c);
			}
			return companies;

		} catch (Exception e) {
			LOG.debug("Error to retrieve companies");
			e.printStackTrace();			
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
		}
		return null;
		
	}

}
