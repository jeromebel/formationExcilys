package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.om.Company;
import com.om.Computer;

@SuppressWarnings("rawtypes")
public class ComputerRowMapper implements RowMapper{

	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Computer c = new Computer();
		c.setId(rs.getLong("c.id"));
		c.setName(rs.getString("c.name"));
		
		Company comp = new Company();
		comp.setId(rs.getInt("c.company_id"));
		comp.setName(rs.getString("f.name"));
		c.setCompany(comp);
		
		if (rs.getTimestamp("c.introduced") == null)
			c.setIntroduced(null);
		else
			c.setIntroduced(new DateTime(rs.getTimestamp("c.introduced")));

		if (rs.getTimestamp("c.discontinued") == null)
			c.setDiscontinued(null);
		else
			c.setDiscontinued(new DateTime(rs.getTimestamp("c.discontinued")));
		
		return c;
	}
	

}
