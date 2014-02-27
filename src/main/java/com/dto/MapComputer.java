package com.dto;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.om.Company;
import com.om.Computer;

public class MapComputer {
	final static Logger logger = LoggerFactory.getLogger(MapComputer.class);

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
			c.setIntroduced(null);
			logger.debug("Introduced Date invalide format or null");
		}
		
		try {
			Date d = dateFormat.parse(cDTO.getDiscontinued());
			c.setDiscontinued(d);
		} catch (ParseException e1) {
			c.setDiscontinued(null);
			logger.debug("Discontinued Date invalide format or null");
		}
		Company comp = new Company();
		try{
			comp.setId(Integer.valueOf(cDTO.getCompanyId()));
		} catch (NumberFormatException e){
			comp.setId(0);
			logger.debug("Company Id invalid");
		}
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
