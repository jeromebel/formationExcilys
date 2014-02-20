package com.services;

import java.util.List;

import com.dao.DAOfactory;
import com.om.Company;

public class CompanyService {
	
	public static List<Company> readAll(){
		DAOfactory.INSTANCE.startTransaction();
		List<Company> result = DAOfactory.INSTANCE.getCompanyDAO().readAll();
		DAOfactory.INSTANCE.endTransaction();		
		return result;
	}

}
