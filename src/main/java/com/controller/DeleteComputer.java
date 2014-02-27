package com.controller;

import javax.servlet.http.HttpServletRequest;

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
	
	@Autowired
	private ComputerService computerService;
    
    public DeleteComputer() {
        super();
       
    }

    @RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model , HttpServletRequest request) {
		String id = request.getParameter("idComputer");
				
		Computer c = computerService.readFilterByID(Long.valueOf(id));
		ComputerDTO cDTO = MapComputer.computerToDTO(c);
		request.setAttribute("computerDelete", cDTO);
		model.addAttribute("computerDelete",cDTO);
		return "confirmationDeleteComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model , HttpServletRequest request) {
		String computerId   = request.getParameter("computerId");
				
		computerService.delete(computerId);
		
		model.addAttribute("delete", true);		
		return "confirmationDeleteComputer";				
	}

}
