package com.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ComputerDAO;
import com.dao.DAOfactory;
import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@Service
@Transactional(readOnly=true)
public class ComputerServiceImpl implements ComputerService{
	
	final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Autowired
	private ComputerDAO computerDAO;

	   
	@Override
	public void readByPage(PageWrapper<ComputerDTO> page) {

		try {
			List<Computer> computers;
			page.setTotalNumberOfRecords(computerDAO.readTotalCount());
			computers = computerDAO.readByPage(page);


			page.setResults(MapComputer.getComputersDTO(computers));
		} catch (SQLException e) {
			LOG.error("SQL error");
		}

	}


	@Override
	public void readFilterByName(PageWrapper<ComputerDTO> page) {
		try {

			List<Computer> computers = computerDAO.readFilterByName(page);
			page.setResults(MapComputer.getComputersDTO(computers));

			String name = page.getFilterName();
			page.setTotalNumberOfRecords(computerDAO.readTotalCountFilterByName(name));

		} catch (SQLException e) {
			LOG.error("SQL error");
		}

	}


	@Override
	public Computer readFilterByID(Long id) {

		try {
			Computer result;
			result = computerDAO.readFilterByID(id);
			result.setId(id);

			return result;
		} catch (SQLException e) {
			LOG.error("SQL error");
		}
		return null;
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(String id) {
		try {

			computerDAO.delete(id);
			DAOfactory.INSTANCE.getLogDAO().create(Long.valueOf(id),
					"Computer updated");
		} catch (SQLException e) {
			LOG.error("SQL error");
		}
	}


	@Override
	@Transactional(readOnly=false)
	public void update(Computer c) {
		try {

			computerDAO.update(c);
			DAOfactory.INSTANCE.getLogDAO().create(c.getId(),
					"Computer updated");

		} catch (SQLException e) {
			LOG.error("SQL error");
		}
	}


	@Override
	@Transactional(readOnly=false)
	public void create(Computer c) {
		try {
			
			computerDAO.create(c);
			DAOfactory.INSTANCE.getLogDAO().create(c.getId(),
					"Add new computer");
			
		} catch (SQLException e) {
			LOG.error("SQL error");
		}

	}

}
