package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.Company;
import com.repository.CompanyRepo;

@Service
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
