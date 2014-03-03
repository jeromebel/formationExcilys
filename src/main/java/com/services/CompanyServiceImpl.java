package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CompanyDAO;
import com.om.Company;

@Service
@Transactional(readOnly=true)
public class CompanyServiceImpl implements CompanyService {	
	
	@Autowired
	private CompanyDAO companyDAO;
	
	/* (non-Javadoc)
	 * @see com.services.CompanyService#readAll()
	 */
	@Override
	public List<Company> readAll(){		
		List<Company> result = companyDAO.readAll();
		return result;
	}

}
