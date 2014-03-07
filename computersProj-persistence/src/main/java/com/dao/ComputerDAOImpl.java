package com.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;
import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
	final Logger log = LoggerFactory.getLogger(ComputerDAOImpl.class);

	final String FILTER_NAME = "filterName";
	final String FILTER_ID = "filterId";
	final String LIMIT = "limit";
	final String OFFSET = "offset";
	final String NAME  = "name";
	final String INTRODUCED = "introduced";
	final String DISCONTINUED = "discontinued";
	final String COMPANY_ID = "company_id";
	
	final String SQL_READ_TOTAL_COUNT = "SELECT COUNT(*) FROM computer;";
	final String SQL_READ_TOTAL_COUNT_BY_NAME = "SELECT COUNT(*) "
			+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id "
			+ " WHERE c.name LIKE :filterName OR f.name LIKE :filterName ;";
	
	final String SQL_READ_ALL = "SELECT c.id, c.name, c.company_id, c.introduced, c.discontinued , f.name"
			+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id ";
	final String SQL_READ_ALL_BY_NAME = "SELECT c.id,  c.name, c.company_id , c.introduced, c.discontinued ,f.name"
			+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id "
			+ " WHERE c.name LIKE :filterName OR f.name LIKE :filterName ";
	final String SQL_READ_BY_ID = "SELECT c.id, c.name, c.company_id, c.introduced, c.discontinued , f.name"
			+ " FROM computer c LEFT JOIN company f ON c.company_id = f.id "
			+ " WHERE c.id = :filterId ;";
	
	final String SQL_DELETE = "DELETE FROM computer WHERE id=:filterId";
	final String SQL_UPDATE = "UPDATE computer SET  name= :name," + " introduced= :introduced," + " discontinued= :discontinued,"
			+ " company_id = :company_id" + " WHERE id= :filterId";
	final String SQL_CREATE = "INSERT INTO computer (name , introduced , discontinued , company_id) "
			+ "VALUES(?,?,?,?)";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
		return namedParameterJdbcTemplate;
	}
	
	@Override
	public Integer readTotalCount() throws SQLException {
		return readTotalCountFilterByName("");
	}

	@Override
	public Integer readTotalCountFilterByName(String name) throws SQLException {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(FILTER_NAME,"%"+name+"%");
		return getNamedParameterJdbcTemplate().queryForObject(SQL_READ_TOTAL_COUNT_BY_NAME , namedParameters,Integer.class);
	}

	@Override
	public List<Computer> readByPage(PageWrapper page)
			throws SQLException {		

		int limit = page.getComputerPerPage();
		int offset = (page.getPageNumber() - 1) * page.getComputerPerPage();

		String sql = SQL_READ_ALL
						+ " ORDER BY "
						+ page.getOrderedBy()
						+ " "
						+ page.getOrderDirection() + " LIMIT :limit OFFSET :offset";
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(LIMIT, limit);
		namedParameters.addValue(OFFSET,offset);	
		
		log.debug(namedParameters.toString());
				
		return getNamedParameterJdbcTemplate().query(sql, namedParameters, new ComputerRowMapper());
	}

	@Override
	public List<Computer> readFilterByName(PageWrapper page)
			throws SQLException {

		int limit = page.getComputerPerPage();
		int offset = (page.getPageNumber() - 1) * page.getComputerPerPage();

		String sql = SQL_READ_ALL_BY_NAME
						+ " ORDER BY "
						+ page.getOrderedBy()
						+ " "
						+ page.getOrderDirection() + " LIMIT :limit OFFSET :offset";
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(FILTER_NAME,"%"+page.getFilterName()+"%");
		namedParameters.addValue(LIMIT, limit);
		namedParameters.addValue(OFFSET, offset);		
				
		return getNamedParameterJdbcTemplate().query(sql, namedParameters, new ComputerRowMapper());

	}

	@Override
	public Computer readFilterByID(Long id) throws SQLException {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(FILTER_ID, id);
		
		List<Computer> computers = getNamedParameterJdbcTemplate().query(SQL_READ_BY_ID, namedParameters, new ComputerRowMapper());
		
		if (computers.size() != 1) {
			log.error("id of the computer is not found");
			return null;
		}

		return computers.get(0);
	}

	@Override
	public void delete(String id) throws NumberFormatException, SQLException {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(FILTER_ID, id);
		getNamedParameterJdbcTemplate().update(SQL_DELETE, namedParameters);

		log.info("Computer num " + id + " well deleted");

	}

	@Override
	public void update(Computer c) throws SQLException {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		namedParameters.addValue(NAME, c.getName());
		if (c.getIntroduced() != null)
			namedParameters.addValue(INTRODUCED, new Timestamp(c.getIntroduced().getMillis()));
		else
			namedParameters.addValue(INTRODUCED, null);
		if (c.getDiscontinued() != null)
			namedParameters.addValue(DISCONTINUED, new Timestamp(c.getDiscontinued().getMillis()));
		else
			namedParameters.addValue(DISCONTINUED, null);
		if ((c.getCompany() != null) && (c.getCompany().getId() != 0))
			namedParameters.addValue(COMPANY_ID, c.getCompany().getId());
		else
			namedParameters.addValue(COMPANY_ID, null);
		
		namedParameters.addValue(FILTER_ID, c.getId());
		
		getNamedParameterJdbcTemplate().update(SQL_UPDATE, namedParameters);

		log.info("Computer num " + c.getId() + " well Update");

	}

	@Override
	public void create(Computer c) throws SQLException {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		namedParameters.addValue(NAME, c.getName());
		if (c.getIntroduced() != null)
			namedParameters.addValue(INTRODUCED, new Timestamp(c.getIntroduced().getMillis()));
		else
			namedParameters.addValue(INTRODUCED, null);
		if (c.getDiscontinued() != null)
			namedParameters.addValue(DISCONTINUED, new Timestamp(c.getDiscontinued().getMillis()));
		else
			namedParameters.addValue(DISCONTINUED, null);
		if ((c.getCompany() != null) && (c.getCompany().getId() != 0))
			namedParameters.addValue(COMPANY_ID, c.getCompany().getId());
		else
			namedParameters.addValue(COMPANY_ID, null);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		getNamedParameterJdbcTemplate().update(SQL_CREATE, namedParameters , keyHolder);

		c.setId(keyHolder.getKey().intValue());

		log.info("Computer num " + c.getId() + " well created");


	}

}
