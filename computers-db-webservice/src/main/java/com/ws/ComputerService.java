package com.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@WebService
public interface ComputerService {

	@WebMethod
	public abstract void readByPage(PageWrapper page);

	public abstract Computer readFilterByID(Long id);

	@WebMethod
	public abstract void delete(String id);

	@WebMethod
	public abstract void update(Computer c);

	@WebMethod
	public abstract void create(Computer c);

}