package com.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dao.DAOfactory;
import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@Component
public class ComputerService {

	public static void readByPage(PageWrapper<ComputerDTO> page) {

		try {
			List<Computer> computers;
			DAOfactory.INSTANCE.startTransaction();
			page.setTotalNumberOfRecords(DAOfactory.INSTANCE.getComputerDAO()
					.readTotalCount());
			computers = DAOfactory.INSTANCE.getComputerDAO().readByPage(page);

			DAOfactory.INSTANCE.endTransaction();

			page.setResults(MapComputer.getComputersDTO(computers));
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}

	}

	public static void readFilterByName(PageWrapper<ComputerDTO> page) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			List<Computer> computers = DAOfactory.INSTANCE.getComputerDAO()
					.readFilterByName(page);
			page.setResults(MapComputer.getComputersDTO(computers));

			String name = page.getFilterName();
			page.setTotalNumberOfRecords(DAOfactory.INSTANCE.getComputerDAO()
					.readTotalCountFilterByName(name));

			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}

	}

	public static Computer readFilterByID(Long id) {

		try {
			DAOfactory.INSTANCE.startTransaction();
			Computer result;
			result = DAOfactory.INSTANCE.getComputerDAO().readFilterByID(id);

			DAOfactory.INSTANCE.endTransaction();
			return result;
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}
		return null;
	}

	public static void delete(String id) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			DAOfactory.INSTANCE.getComputerDAO().delete(id);
			DAOfactory.INSTANCE.getLogDAO().create(Long.valueOf(id),
					"Computer updated");

			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}
	}

	public static void update(Computer c) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			DAOfactory.INSTANCE.getComputerDAO().update(c);
			DAOfactory.INSTANCE.getLogDAO().create(c.getId(),
					"Computer updated");

			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}
	}

	public static void create(Computer c) {
		try {
			DAOfactory.INSTANCE.startTransaction();

			DAOfactory.INSTANCE.getComputerDAO().create(c);
			DAOfactory.INSTANCE.getLogDAO().create(c.getId(),
					"Add new computer");
			
			DAOfactory.INSTANCE.endTransaction();
		} catch (SQLException e) {
			DAOfactory.INSTANCE.rollbackTransaction();
		}

	}

}
