package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

public interface ComputerDAO {

	public abstract Integer readTotalCount() throws SQLException;

	public abstract Integer readTotalCountFilterByName(String name)
			throws SQLException;

	public abstract List<Computer> readByPage(PageWrapper page)
			throws SQLException;

	public abstract List<Computer> readFilterByName(
			PageWrapper page) throws SQLException;

	public abstract Computer readFilterByID(Long id) throws SQLException;

	public abstract void delete(String id) throws NumberFormatException,
			SQLException;

	public abstract void update(Computer c) throws SQLException;

	public abstract void create(Computer c) throws SQLException;

}