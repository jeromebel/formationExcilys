package com.services;

import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

public interface ComputerService {

	public abstract void readByPage(PageWrapper page);

	public abstract Computer readFilterByID(Long id);

	public abstract void delete(String id);

	public abstract void update(Computer c);

	public abstract void create(Computer c);

}