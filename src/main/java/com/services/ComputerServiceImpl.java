package com.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ComputerDAO;
import com.dao.DAOfactory;
import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@Service
public class ComputerServiceImpl implements ComputerService{

	@Autowired
	private ComputerDAO computerDAO;

	
	/* (non-Javadoc)
	 * @see com.services.ComputerService#readByPage(com.servlet.wrapper.PageWrapper)
	 */
	@Override
	public void readByPage(PageWrapper<ComputerDTO> page) {

		try {
			List<Computer> computers;
			DAOfactory.INSTANCE.startTransaction();
			page.setTotalNumberOfRecords(computerDAO.readTotalCount());
			computers = computerDAO.readByPage(page);

			DAOfactory.INSTANCE.endTransaction();

			page.setResults(MapComputer.getComputersDTO(computers));
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}

	}

	/* (non-Javadoc)
	 * @see com.services.ComputerService#readFilterByName(com.servlet.wrapper.PageWrapper)
	 */
	@Override
	public void readFilterByName(PageWrapper<ComputerDTO> page) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			List<Computer> computers = computerDAO.readFilterByName(page);
			page.setResults(MapComputer.getComputersDTO(computers));

			String name = page.getFilterName();
			page.setTotalNumberOfRecords(computerDAO.readTotalCountFilterByName(name));

			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}

	}

	/* (non-Javadoc)
	 * @see com.services.ComputerService#readFilterByID(java.lang.Long)
	 */
	@Override
	public Computer readFilterByID(Long id) {

		try {
			DAOfactory.INSTANCE.startTransaction();
			Computer result;
			result = computerDAO.readFilterByID(id);
			result.setId(id);

			DAOfactory.INSTANCE.endTransaction();
			return result;
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.services.ComputerService#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			computerDAO.delete(id);
			DAOfactory.INSTANCE.getLogDAO().create(Long.valueOf(id),
					"Computer updated");

			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see com.services.ComputerService#update(com.om.Computer)
	 */
	@Override
	public void update(Computer c) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			computerDAO.update(c);
			DAOfactory.INSTANCE.getLogDAO().create(c.getId(),
					"Computer updated");

			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see com.services.ComputerService#create(com.om.Computer)
	 */
	@Override
	public void create(Computer c) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			computerDAO.create(c);
			DAOfactory.INSTANCE.getLogDAO().create(c.getId(),
					"Add new computer");
			
			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}

	}

}
