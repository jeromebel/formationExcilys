package com.ws;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.om.Company;
import com.repository.CompanyRepo;

@WebService(endpointInterface = "com.ws.CompanyService")
@Transactional(readOnly=true)
public class CompanyServiceImpl implements CompanyService {	
	
	@Autowired
	private CompanyRepo companyRepo;

	@Override
	public List<Company> readAll(){		
		List<Company> result = companyRepo.findAll();
		return result;
	}

}
