package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Computer;
import com.services.ComputerService;


@Controller
@RequestMapping("/DeleteComputer")
public class DeleteComputer {
	final Logger LOG = LoggerFactory.getLogger(DeleteComputer.class);
	
	@Autowired
	private ComputerService computerService;
    
    public DeleteComputer() {
        super();
       
    }

    @RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model , String idComputer) {
		
		LOG.debug("Id computer recieve : "+idComputer);
				
		Computer c = computerService.readFilterByID(Long.valueOf(idComputer));
		ComputerDTO cDTO = MapComputer.computerToDTO(c);
		
		model.addAttribute("computerDelete",cDTO);
		return "confirmationDeleteComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model , String computerId) {
		
    	LOG.debug("idComputer : "+computerId);
		computerService.delete(computerId);
		
		model.addAttribute("delete", true);		
		return "confirmationDeleteComputer";				
	}

}
