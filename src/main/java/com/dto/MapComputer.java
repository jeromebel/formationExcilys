package com.dto;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dao.CompanyDAO;
import com.dao.DAOfactory;
import com.om.Company;
import com.om.Computer;

public class MapComputer {

	public static Computer dtoToComputer(ComputerDTO cDTO){
		Computer c = new Computer();
		
		if(cDTO.getId() != null)
			c.setId(Integer.valueOf(cDTO.getId()));
		
		if(cDTO.getName() != null)
			c.setName(cDTO.getName());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

		try {
			Date d = dateFormat.parse(cDTO.getIntroduced());
			c.setIntroduced(d);
		} catch (ParseException e1) {
			c.setIntroduced(new Timestamp(0));
			e1.printStackTrace();
		}
		
		try {

			Date d = dateFormat.parse(cDTO.getIntroduced());
			c.setDiscontinued(d);
		} catch (ParseException e1) {
			c.setDiscontinued(new Timestamp(0));
			e1.printStackTrace();
		}
		Company comp = new Company();
		comp.setId(Integer.valueOf(cDTO.getCompanyId()));
		if( cDTO.getCompanyName() != null)
			comp.setName(cDTO.getCompanyName());	
		c.setCompany(comp);
		
		return c;
	}
	
	public static ComputerDTO computerToDTO(Computer c){
		if (c==null)
			return null;
		
		ComputerDTO cDTO = new ComputerDTO();		
		cDTO.setId(String.valueOf(c.getId()));
		cDTO.setCompanyId(String.valueOf(c.getCompany().getId()));
		cDTO.setCompanyName(c.getCompany().getName());
		cDTO.setName(c.getName());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (c.getIntroduced() != null)
			cDTO.setIntroduced(dateFormat.format(c.getIntroduced()));
		else
			cDTO.setIntroduced("inconnue");
		if (c.getDiscontinued() != null)
			cDTO.setDiscontinued(dateFormat.format(c.getDiscontinued()));
		else
			cDTO.setDiscontinued("inconnue");
		return cDTO;
	}
	
	public static List<ComputerDTO> getComputersDTO(List<Computer> computers){
		List<ComputerDTO> computersDAO = new ArrayList<ComputerDTO>();
		for(Computer c : computers){
			ComputerDTO cDTAO = computerToDTO(c);
			computersDAO.add(cDTAO);
		}
		return computersDAO;		
	}
}
