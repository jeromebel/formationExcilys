package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
	protected String doPost(ModelMap model, HttpServletRequest request ,
			@Valid ComputerDTO computerDto, BindingResult result) {
		String computerId = request.getParameter("computerId");
		computerDto.setId(computerId);

		Boolean formValid = true;
		StringBuilder message = new StringBuilder();
		
		if (result.hasErrors()) {
			formValid = false;
			message.append("Computer Name "+ result.getAllErrors().get(0).getDefaultMessage());
		}
					
		if(formValid){
			
			computerService.update(MapComputer.dtoToComputer(computerDto));
			
			Computer computerChanged = computerService.readFilterByID(Long.valueOf(computerId));		
			computerDto = MapComputer.computerToDTO(computerChanged);
			
			List<ComputerDTO> computerDTOs = new ArrayList<ComputerDTO>();
			computerDTOs.add(computerDto);
			
			PageWrapper<ComputerDTO> page = new PageWrapper<ComputerDTO>();
			page.setPageNumber(1);
			page.setComputerPerPage(20);
			page.setOrderDirection("ASC");
			page.setOrderedBy("c.id");
			page.setFilterName(computerDto.getName());
			
			computerService.readFilterByName(page);
			
			page.setNumberOfPages((Integer) (page.getTotalNumberOfRecords()/page.getComputerPerPage())+1);
			
			HttpSession s = request.getSession();
			s.setAttribute("pageData" , page);
			
			model.addAttribute("pageData",page);
						
			return "dashboard";	
		}
		else {
			Computer c = computerService.readFilterByID(Long.valueOf(computerDto.getCompanyId()));
			computerDto = MapComputer.computerToDTO(c);
			request.setAttribute("computerEdit", computerDto);
			request.setAttribute("companyId", c.getCompany().getId());
			
			List<Company> companies = companyService.readAll();
			model.addAttribute("companies", companies);
			
		}
		
		model.addAttribute("error", formValid);
		model.addAttribute("message", message.toString());
		return "editComputer";
		
	}

}
