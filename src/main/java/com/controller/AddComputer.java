package com.controller;

import java.util.List;

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
import com.services.CompanyService;
import com.services.ComputerService;
import com.servlet.wrapper.PageWrapper;

@Controller
@RequestMapping("/AddComputer")
public class AddComputer {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ComputerService computerService;

	public AddComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model, HttpServletRequest request) {
		List<Company> companies = companyService.readAll();
		model.addAttribute("computerDTO", new ComputerDTO());
		model.addAttribute("companies", companies);
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model, HttpServletRequest request,
			@Valid ComputerDTO computerDto, BindingResult result) {
		Boolean formValid = true;
		StringBuilder message = new StringBuilder();

		if (result.hasErrors()) {
			formValid = false;
			message.append("Computer Name "+ result.getAllErrors().get(0).getDefaultMessage());
		}

		if (formValid) {

			HttpSession s = request.getSession();
			PageWrapper<ComputerDTO> page = new PageWrapper<ComputerDTO>();
			if (s.getAttribute("pageData") != null) {
				page = (PageWrapper<ComputerDTO>) s.getAttribute("pageData");
			} else {
				page.setPageNumber(1);
				page.setOrderDirection("id");
				page.setComputerPerPage(20);
				page.setOrderDirection("ASC");
			}

			page.setFilterName(computerDto.getName());

			computerService.create(MapComputer.dtoToComputer(computerDto));
			computerService.readFilterByName(page);

			page.setNumberOfPages((Integer) (page.getResults().size())
					/ page.getComputerPerPage());

			s.setAttribute("pageData", page);

			model.addAttribute("pageData", page);
			return "dashboard";
		} else {
			List<Company> companies = companyService.readAll();
			model.addAttribute("companies", companies);
			model.addAttribute("error", true);
			model.addAttribute("message", message.toString());

			return "addComputer";
		}

	}

}
