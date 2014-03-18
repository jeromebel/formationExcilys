package com.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.om.Company;

@WebService
public interface CompanyService {

	@WebMethod
	public abstract List<Company> readAll();

}