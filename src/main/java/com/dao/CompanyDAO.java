package com.dao;

import java.util.List;

import com.om.Company;

public interface CompanyDAO {

	public abstract List<Company> readAll();

}