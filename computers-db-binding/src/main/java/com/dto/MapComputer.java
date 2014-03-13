package com.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

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
		
		DateTime d;
		try {
			d = getLocalDateFormat().parseDateTime(cDTO.getIntroduced());
		} catch (Exception e) {
			d = null;
			logger.debug(e.getMessage());
		}
		c.setIntroduced(d);
		
		try {
			d =  getLocalDateFormat().parseDateTime(cDTO.getDiscontinued());
		}  catch (Exception e) {
			d = null;
			logger.debug(e.getMessage());
		}
		c.setDiscontinued(d);
		
		Company comp = new Company();
		try{
			comp.setId(Integer.valueOf(cDTO.getCompany().getId()));
		} catch (NumberFormatException e){
			comp = null;
			logger.debug("Company Id invalid");
		}
		if(comp.getId() == 0)
			comp = null;
			
		if( cDTO.getCompany().getName() != null)
			comp.setName(cDTO.getCompany().getName());	
		c.setCompany(comp);
		
		return c;
	}
	
	public static ComputerDTO computerToDTO(Computer c){
		if (c==null)
			return null;
		
		ComputerDTO cDTO = new ComputerDTO();		
		cDTO.setId(String.valueOf(c.getId()));
		
		CompanyDTO comp = new CompanyDTO();
		try {
			comp.setId(String.valueOf(c.getCompany().getId()));
			comp.setName(c.getCompany().getName());
		} catch (NullPointerException nullE){
			comp = null;
			logger.debug("company null");
		}
		cDTO.setCompany(comp);
		
		cDTO.setName(c.getName());
		
		if (c.getIntroduced() != null)
			cDTO.setIntroduced(getLocalDateFormat().print(c.getIntroduced()));
		else
			cDTO.setIntroduced("");
		if (c.getDiscontinued() != null)
			cDTO.setDiscontinued(getLocalDateFormat().print(c.getDiscontinued()));
		else
			cDTO.setDiscontinued("");
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
	
	public static DateTimeFormatter getLocalDateFormat() {
        String pattern = DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale());
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);        
       
        return fmt;
    }
}
