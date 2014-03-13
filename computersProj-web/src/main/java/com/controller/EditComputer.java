package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	protected String doGet(ModelMap model,String idComputer) {
				
		List<Company> companies = companyService.readAll();
		model.addAttribute("companies", companies);
				
		Computer c = computerService.readFilterByID(Long.valueOf(idComputer));
		
		model.addAttribute("computerEdit", c);
		if(c.getCompany() != null)
			model.addAttribute("companyId", c.getCompany().getId());
		else
			model.addAttribute("companyId", 0);
		model.addAttribute("dateFormat",DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()).toLowerCase());
		
		return "editComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model, String computerId ,
			@Valid Computer computerEdit, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		computerEdit.setId(Integer.valueOf(computerId));
					
		if(!result.hasErrors()){
			
			computerService.update(computerEdit);
			
			Computer computerChanged = computerService.readFilterByID(Long.valueOf(computerId));		
			
			List<ComputerDTO> computerDTOs = new ArrayList<ComputerDTO>();
			computerDTOs.add(MapComputer.computerToDTO(computerChanged));
			
			PageWrapper page = new PageWrapper();
			page.setPageNumber(1);
			page.setComputerPerPage(20);
			page.setOrderDirection("ASC");
			page.setOrderBy("c.id");
			page.setFilterName(computerEdit.getName());
			
			computerService.readByPage(page);
			
			page.setNumberOfPages((Integer) (page.getTotalNumberOfRecords()/page.getComputerPerPage())+1);
			
			redirectAttributes.addFlashAttribute("pageData", page);			
			return "redirect:Home";	
		}
		else {
			
			model.addAttribute("computerEdit", computerEdit);
			model.addAttribute("companyId", computerEdit.getCompany().getId());
			model.addAttribute("dateFormat",DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()).toLowerCase());
			
			List<Company> companies = companyService.readAll();
			model.addAttribute("companies", companies);
			
			return "editComputer";
			
		}
		
	}

}
