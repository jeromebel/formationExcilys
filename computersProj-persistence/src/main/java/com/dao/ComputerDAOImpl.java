package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;
import com.om.Company;
import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
	final Logger log = LoggerFactory.getLogger(ComputerDAOImpl.class);

	@Autowired
	private BoneCPDataSource dataSource;

	@Override
	public Integer readTotalCount() throws SQLException {
		PreparedStatement stmt = null;
		Connection cn = DataSourceUtils.getConnection(dataSource);

		stmt = cn.prepareStatement("SELECT COUNT(*) "
				+ " FROM computer;");
		ResultSet rs = (ResultSet) stmt.executeQuery();
		while (rs.next()) {
			Integer result = rs.getInt(1);
			return result;
		}

		if (stmt != null)
			stmt.close();
		
		if (rs != null)
			rs.close();

		return null;
	}

	@Override
	public Integer readTotalCountFilterByName(String name) throws SQLException {
		PreparedStatement stmt = null;
		Connection cn = DataSourceUtils.getConnection(dataSource);

		stmt = (PreparedStatement) cn
				.prepareStatement("SELECT COUNT(*) "
						+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id "
						+ " WHERE c.name LIKE ? OR f.name LIKE ? ;");
		stmt.setString(1, "%" + name + "%");
		stmt.setString(2, "%" + name + "%");
		ResultSet rs = (ResultSet) stmt.executeQuery();
		while (rs.next()) {
			Integer result = rs.getInt(1);
			return result;
		}

		if (stmt != null)
			stmt.close();
		
		if (rs != null)
			rs.close();

		return null;
	}

	@Override
	public List<Computer> readByPage(PageWrapper page)
			throws SQLException {

		Connection cn = DataSourceUtils.getConnection(dataSource);
		PreparedStatement stmt = null;

		stmt = (PreparedStatement) cn
				.prepareStatement("SELECT c.id, c.name, c.company_id, c.introduced, c.discontinued , f.name"
						+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id "
						+ " ORDER BY "
						+ page.getOrderedBy()
						+ " "
						+ page.getOrderDirection() + " LIMIT ? OFFSET ? ");
		int limit = page.getComputerPerPage();
		stmt.setInt(1, limit);
		int offset = (page.getPageNumber() - 1) * page.getComputerPerPage();
		stmt.setInt(2, offset);

		return getComputers(cn, stmt);

	}

	@Override
	public List<Computer> readFilterByName(PageWrapper page)
			throws SQLException {

		Connection cn = DataSourceUtils.getConnection(dataSource);

		if ("".equals(page.getFilterName()))
			return this.readByPage(page);

		PreparedStatement stmt = null;

		stmt = (PreparedStatement) cn
				.prepareStatement("SELECT c.id,  c.name, c.company_id , c.introduced, c.discontinued ,f.name"
						+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id "
						+ " WHERE c.name LIKE ? OR f.name LIKE ? "
						+ " ORDER BY "
						+ page.getOrderedBy()
						+ " "
						+ page.getOrderDirection() + " LIMIT ? OFFSET ? ;");
		stmt.setString(1, "%" + page.getFilterName() + "%");
		stmt.setString(2, "%" + page.getFilterName() + "%");
		stmt.setInt(3, page.getComputerPerPage());
		stmt.setInt(4, (page.getPageNumber() - 1) * page.getComputerPerPage());

		return getComputers(cn, stmt);

	}

	@Override
	public Computer readFilterByID(Long id) throws SQLException {

		PreparedStatement stmt = null;
		Connection cn = DataSourceUtils.getConnection(dataSource);
		stmt = (PreparedStatement) cn
				.prepareStatement("SELECT c.id, c.name, c.company_id, c.introduced, c.discontinued , f.name"
						+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id "
						+ " WHERE c.id = ? ;");
		stmt.setLong(1, id);
		List<Computer> computers = getComputers(cn, stmt);
		if (computers.size() != 1) {
			log.error("id of the computer is not found");
			return null;
		}

		return computers.get(0);
	}

	@Override
	public void delete(String id) throws NumberFormatException, SQLException {
		StringBuilder strBuildSQL = new StringBuilder();
		strBuildSQL.append("DELETE FROM computer ");
		strBuildSQL.append(" WHERE ").append(" id=")
				.append(Integer.valueOf(id));

		Connection cn = null;
		PreparedStatement stmt = null;
		cn = DataSourceUtils.getConnection(dataSource);
		stmt = (PreparedStatement) cn.prepareStatement("DELETE FROM computer  "
				+ " WHERE id=?");
		stmt.setInt(1, Integer.valueOf(id));
		stmt.executeUpdate();

		log.info("Computer num " + id + " well deleted");

		if (stmt != null)
			stmt.close();
	}

	@Override
	public void update(Computer c) throws SQLException {

		Connection cn = null;
		PreparedStatement stmt = null;
		cn = DataSourceUtils.getConnection(dataSource);
		stmt = (PreparedStatement) cn.prepareStatement("UPDATE computer SET "
				+ " name= ?," + " introduced= ?," + " discontinued= ?,"
				+ " company_id= ?" + " WHERE id= ?");
		stmt.setString(1, c.getName());
		stmt.setString(1, c.getName());
		if (c.getIntroduced() != null)
			stmt.setTimestamp(2, new Timestamp(c.getIntroduced().getMillis()));
		else
			stmt.setNull(2, Types.TIMESTAMP);
		if (c.getDiscontinued() != null)
			stmt.setTimestamp(3, new Timestamp(c.getDiscontinued().getMillis()));
		else
			stmt.setNull(3, Types.TIMESTAMP);
		if ((c.getCompany() != null) && (c.getCompany().getId() != 0))
			stmt.setInt(4, c.getCompany().getId());
		else
			stmt.setNull(4, Types.BIGINT);
		stmt.setLong(5, c.getId());

		stmt.executeUpdate();

		log.info("Computer num " + c.getId() + " well Update");

		if (stmt != null)
			stmt.close();

	}

	@Override
	public void create(Computer c) throws SQLException {

		Connection cn = null;
		PreparedStatement stmt = null;

		cn = DataSourceUtils.getConnection(dataSource);
		stmt = (PreparedStatement) cn.prepareStatement(
				"INSERT INTO computer (name , introduced , discontinued , company_id) "
						+ "VALUES(?,?,?,?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
		stmt.setString(1, c.getName());
		if (c.getIntroduced() != null)
			stmt.setTimestamp(2, new Timestamp(c.getIntroduced().getMillis()));
		else
			stmt.setNull(2, Types.TIMESTAMP);
		if (c.getDiscontinued() != null)
			stmt.setTimestamp(3, new Timestamp(c.getDiscontinued().getMillis()));
		else
			stmt.setNull(3, Types.TIMESTAMP);
		if ((c.getCompany() != null) && (c.getCompany().getId() != 0))
			stmt.setInt(4, c.getCompany().getId());
		else
			stmt.setNull(4, Types.BIGINT);

		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		while (rs.next())
			c.setId(rs.getLong(1));

		log.info("Computer num " + c.getId() + " well created");

		if (stmt != null)
			stmt.close();

	}

	private List<Computer> getComputers(Connection cn, PreparedStatement stmt)
			throws SQLException {

		List<Computer> allComputers = new ArrayList<Computer>();
		ResultSet rs = null;
		rs = (ResultSet) stmt.executeQuery();
		while (rs.next()) {
			Computer c = new Computer();

			c.setId(rs.getInt(1));

			c.setName(rs.getString(2));

			Company comp = new Company();
			comp.setId(rs.getInt(3));
			comp.setName(rs.getString(6));

			c.setCompany(comp);

			if (rs.getTimestamp(4) == null)
				c.setIntroduced(null);
			else
				c.setIntroduced(new DateTime(rs.getTimestamp(4)));

			if (rs.getTimestamp(5) == null)
				c.setDiscontinued(null);
			else
				c.setDiscontinued(new DateTime(rs.getTimestamp(5)));
			allComputers.add(c);
		}

		if (rs != null)
			rs.close();

		if (stmt != null)
			stmt.close();

		return allComputers;
	}

}
