package com.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ComputerDAO;
import com.dao.LogDAO;
import com.dto.MapComputer;
import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService{
	
	final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private LogDAO logDB;

	   
	@Override
	@Transactional(readOnly=true)
	public void readByPage(PageWrapper page) {

		try {
			List<Computer> computers;
			computers = computerDAO.readByPage(page);

			page.setResults(MapComputer.getComputersDTO(computers));
		} catch (SQLException e) {
			LOG.error("\nSQL error\nreadByPage");
		}

	}

	@Override
	@Transactional(readOnly=true)
	public Computer readFilterByID(Long id) {

		try {
			Computer result;
			result = computerDAO.readFilterByID(id);
			result.setId(id);

			return result;
		} catch (SQLException e) {
			LOG.error("\nSQL error\nreadFilterByID");
		}
		return null;
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(String id) {
		try {

			computerDAO.delete(id);
			logDB.create(Long.valueOf(id),
					"Computer updated");
		} catch (SQLException e) {
			LOG.error("\nSQL error\ndelete");
		}
	}


	@Override
	@Transactional(readOnly=false)
	public void update(Computer c) {
		try {

			computerDAO.update(c);
			logDB.create(c.getId(),
					"Computer updated");

		} catch (SQLException e) {
			LOG.error("\nSQL error\nupdate");
		}
	}


	@Override
	@Transactional(readOnly=false)
	public void create(Computer c) {
		try {
			
			computerDAO.create(c);
			logDB.create(c.getId(),
					"Add new computer");
			
		} catch (SQLException e) {
			LOG.error("\nSQL error\ncreate");
		}

	}

}
