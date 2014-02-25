package com.services;

import com.dto.ComputerDTO;
import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

public interface ComputerService {

	public abstract void readByPage(PageWrapper<ComputerDTO> page);

	public abstract void readFilterByName(PageWrapper<ComputerDTO> page);

	public abstract Computer readFilterByID(Long id);

	public abstract void delete(String id);

	public abstract void update(Computer c);

	public abstract void create(Computer c);

}