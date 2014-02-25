package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CompanyDAO;
import com.dao.DAOfactory;
import com.om.Company;

@Service
public class CompanyServiceImpl implements CompanyService {	
	
	@Autowired
	private CompanyDAO companyDAO;
	
	/* (non-Javadoc)
	 * @see com.services.CompanyService#readAll()
	 */
	@Override
	public List<Company> readAll(){
		
		DAOfactory.INSTANCE.startTransaction();
		List<Company> result = companyDAO.readAll();
		DAOfactory.INSTANCE.endTransaction();		
		return result;
	}

}
