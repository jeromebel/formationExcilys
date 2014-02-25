package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.ComputerDTO;
import com.services.ComputerService;
import com.servlet.wrapper.PageWrapper;


@Controller
@RequestMapping("/Home")
public class Home {
	
	final static Logger logger = LoggerFactory.getLogger(Home.class);
	
	@Autowired
	private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model, HttpServletRequest request) {

		PageWrapper<ComputerDTO> page = new PageWrapper<ComputerDTO>();
				
		HttpSession s = request.getSession();
		if(s.getAttribute("pageData") != null){
			page = (PageWrapper<ComputerDTO>) s.getAttribute("pageData");
		}
		else{
			page.setPageNumber(1);
			page.setComputerPerPage(20);
			page.setOrderDirection("ASC");
			page.setOrderedBy("c.id");
		}
		
		String orderBy = request.getParameter("orderBy");
		if(orderBy != null){
			page.setOrderedBy(orderBy);
			page.setPageNumber(1);
		}
		
		String orderDirection = request.getParameter("orderDirection");
		if(orderDirection != null){
			page.setOrderDirection(orderDirection);
		}
					
		String strPageNum = request.getParameter("pageNum");
		if(strPageNum != null) {
			page.setPageNumber(Integer.valueOf(strPageNum));
		}
		
		String strNbrPerPage = request.getParameter("computersPerPage");
		if(strNbrPerPage != null){
			page.setComputerPerPage(Integer.valueOf(strNbrPerPage));
		}
				
		String nomFiltre = request.getParameter("filterName");
		
		if(nomFiltre == null){
			nomFiltre = page.getFilterName();
		}
		
		
		if(nomFiltre != null && nomFiltre !=""){
			page.setFilterName(nomFiltre);
			page.setPageNumber(1);
			computerService.readFilterByName(page);			
		}
		else {
			page.setFilterName("");
			computerService.readByPage(page);
		}
		
		page.setNumberOfPages((Integer) (page.getTotalNumberOfRecords()/page.getComputerPerPage())+1); 	
		
		s.setAttribute("pageData" , page);
				
		//request.setAttribute("pageData", page);
		model.addAttribute("pageData",page);		
		
		if(page != null)
			logger.info(page.toString());
		
		return "dashboard";
	}

}
