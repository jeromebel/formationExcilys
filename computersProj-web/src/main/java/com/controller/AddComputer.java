package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
		model.addAttribute("computer", new Computer());
		model.addAttribute("companies", companies);
		model.addAttribute("dateFormat",DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()).toLowerCase());
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model, HttpServletRequest request,
			@Valid ComputerDTO computer, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (!result.hasErrors()) {

			HttpSession s = request.getSession();
			PageWrapper page = new PageWrapper();
			if (s.getAttribute("pageData") != null) {
				page = (PageWrapper) s.getAttribute("pageData");
			} else {
				page.setPageNumber(1);
				page.setOrderDirection("id");
				page.setComputerPerPage(20);
				page.setOrderDirection("ASC");
			}

			page.setFilterName(computer.getName());

			computerService.create(MapComputer.dtoToComputer(computer));
			computerService.readFilterByName(page);

			page.setNumberOfPages((Integer) (page.getResults().size())
					/ page.getComputerPerPage());

			s.setAttribute("pageData", page);

			redirectAttributes.addFlashAttribute("pageData", page);			
			return "redirect:Home";
		} else {
			model.addAttribute("computer", computer);
			model.addAttribute("dateFormat",DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()).toLowerCase());
			return "addComputer";
		}

	}

}
