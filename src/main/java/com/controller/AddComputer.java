package com.controller;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Company;
import com.services.CompanyService;
import com.services.ComputerService;
import com.servlet.wrapper.PageWrapper;


@Controller
@RequestMapping("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
    public AddComputer() {
        super();
        // TODO Auto-generated constructor stub
    }


    @RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model , HttpServletRequest request) {
		List<Company> companies = companyService.readAll();
		model.addAttribute("companies", companies);
		return "addComputer";
		}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model , HttpServletRequest request) {
		Boolean formValid = true;
		StringBuilder message = new StringBuilder();
		String computerName = request.getParameter("computerName");
		if(computerName.equals("")) {
			formValid = false;
			message.append("Please enter a computer Name");
		}
		String introduced  = request.getParameter("introducedDate");
		if(introduced == null){
			introduced = "";
			formValid = false;
			message.append("Please enter a introduced Date");
		}
		String discontinued = request.getParameter("discontinuedDate");	
		if(discontinued == null){
			discontinued = "";
			formValid = false;
			message.append("Please enter a discontinued Date");
		}
		String companyId    = request.getParameter("company");
		if("0".equals(companyId)){
			companyId = null;
			formValid = false;
			message.append("Please choose a Company");
		}
		
		
		ComputerDTO cDTO = new ComputerDTO();
		cDTO.setName(computerName);
		cDTO.setIntroduced(introduced);
		cDTO.setDiscontinued(discontinued);
		cDTO.setCompanyId(companyId);
								
		if(formValid == true) {
			
			HttpSession s = request.getSession();
			PageWrapper<ComputerDTO> page = new PageWrapper<ComputerDTO>();
			if(s.getAttribute("pageData") != null){
				page = (PageWrapper<ComputerDTO>) s.getAttribute("pageData");
			}
			else{
				page.setPageNumber(1);;
				page.setOrderDirection("id");
				page.setComputerPerPage(20);
				page.setOrderDirection("ASC");
			}
			
			page.setFilterName(computerName);
			
			computerService.create(MapComputer.dtoToComputer(cDTO));
			computerService.readFilterByName(page);
			
						
			page.setNumberOfPages((Integer) (page.getResults().size())/page.getComputerPerPage()); 		
			
			s.setAttribute("pageData" , page);
			
			model.addAttribute("pageData", page);
			return "dashboard";
		}
		else{
			List<Company> companies = companyService.readAll();
			model.addAttribute("companies", companies);
			model.addAttribute("error", true);
			model.addAttribute("message", message.toString());
			
			return "addComputer";
		}
			
		
		
	}

}
