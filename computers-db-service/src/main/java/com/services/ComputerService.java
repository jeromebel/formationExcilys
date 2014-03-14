package com.services;

import org.springframework.security.access.annotation.Secured;

import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

public interface ComputerService {

	public abstract void readByPage(PageWrapper page);

	public abstract Computer readFilterByID(Long id);

	@Secured ({"ROLE_ADMIN"})
	public abstract void delete(String id);

	@Secured ({"ROLE_ADMIN"})
	public abstract void update(Computer c);

	@Secured ({"ROLE_ADMIN"})
	public abstract void create(Computer c);

}