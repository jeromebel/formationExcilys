package com.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Company;
import com.om.Computer;
import com.services.CompanyService;
import com.services.ComputerService;
import com.servlet.wrapper.PageWrapper;


@Controller
@RequestMapping("/EditComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Autowired
	private ComputerService computerService;	
	
	@Autowired
	private CompanyService companyService;
    
    public EditComputer() {
        super();
    }

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model, HttpServletRequest request) {
				
		String id = request.getParameter("idComputer");
		
		List<Company> companies = companyService.readAll();
		request.setAttribute("companies", companies);
				
		Computer c = computerService.readFilterByID(Long.valueOf(id));
		ComputerDTO cDTO = MapComputer.computerToDTO(c);
		
		model.addAttribute("computerEdit", cDTO);
		model.addAttribute("companyId", c.getCompany().getId());
		
		return "editComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model, HttpServletRequest request) {
		String computerId   = request.getParameter("computerId");

		Boolean formValid = true;
		StringBuilder message = new StringBuilder();
		
		String computerName = request.getParameter("computerName");
		if(computerName.equals("")) {
			formValid = false;
			message.append("Please enter a computer Name<br>");
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		String introduced  = request.getParameter("introducedDate");
		
		try {
			dateFormat.parse(introduced);
		} catch (ParseException e1) {
			formValid = false;
			message.append("Please enter a valide introduced date format : YYYY-MM-DD<br>");
		}
		
		String discontinued = request.getParameter("discontinuedDate");

		try {
			dateFormat.parse(discontinued);
		} catch (ParseException e1) {
			formValid = false;
			message.append("Please enter a valide discontinued date format : YYYY-MM-DD<br>");
		}
		
		String companyId    = request.getParameter("company");
		
		ComputerDTO cDTO = new ComputerDTO();
		cDTO.setId(computerId);
		cDTO.setName(computerName);
		cDTO.setIntroduced(introduced);
		cDTO.setDiscontinued(discontinued);
		cDTO.setCompanyId(companyId);		
						
		if(formValid){
			
			computerService.update(MapComputer.dtoToComputer(cDTO));
			
			Computer computerChanged = computerService.readFilterByID(Long.valueOf(computerId));		
			cDTO = MapComputer.computerToDTO(computerChanged);
			
			List<ComputerDTO> computerDTOs = new ArrayList<ComputerDTO>();
			computerDTOs.add(cDTO);
			
			PageWrapper<ComputerDTO> page = new PageWrapper<ComputerDTO>();
			page.setPageNumber(1);
			page.setComputerPerPage(20);
			page.setOrderDirection("ASC");
			page.setOrderedBy("c.id");
			page.setFilterName(cDTO.getName());
			
			computerService.readFilterByName(page);
			
			page.setNumberOfPages((Integer) (page.getTotalNumberOfRecords()/page.getComputerPerPage())+1);
			
			model.addAttribute("pageData",page);
						
			return "dashboard";			
		}
		else {
			Computer c = computerService.readFilterByID(Long.valueOf(computerId));
			cDTO = MapComputer.computerToDTO(c);
			request.setAttribute("computerEdit", cDTO);
			request.setAttribute("companyId", c.getCompany().getId());
			
			List<Company> companies = companyService.readAll();
			model.addAttribute("companies", companies);
			
		}
		
		model.addAttribute("error", formValid);
		model.addAttribute("message", message.toString());
		return "editComputer";
		
	}

}
