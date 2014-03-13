package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.services.ComputerService;
import com.servlet.wrapper.PageWrapper;


@Controller
@RequestMapping("/Home")
public class Home {
	
	final static Logger logger = LoggerFactory.getLogger(Home.class);
	
	@Autowired
	private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model, String orderBy , String orderDirection ,
			String pageNum , String computersPerPage , String filterName ) {

		PageWrapper page = new PageWrapper();
				
//		HttpSession s = request.getSession();
//		if(s.getAttribute("pageData") != null){
//			page = (PageWrapper) s.getAttribute("pageData");
//		}
//		else{
//			page.setPageNumber(1);
//			page.setComputerPerPage(20);
//			page.setOrderDirection("ASC");
//			page.setOrderedBy("c.id");
//		}
		
		page.setPageNumber(1);
		page.setComputerPerPage(20);
		page.setOrderDirection("ASC");
		page.setOrderBy("c.id");
		
		if(orderBy != null){
			page.setOrderBy(orderBy);
			page.setPageNumber(1);
		}
		
		if((orderDirection != null)&&(orderDirection != "")){
			page.setOrderDirection(orderDirection);
		}
					
		if(pageNum != null) {
			page.setPageNumber(Integer.valueOf(pageNum));
		}
		
		if(computersPerPage != null){
			page.setComputerPerPage(Integer.valueOf(computersPerPage));
		}
		
		if(filterName == null){
			filterName = page.getFilterName();
		}		
		
		if(filterName != null && filterName !=""){
			page.setFilterName(filterName);
			page.setPageNumber(1);
			computerService.readByPage(page);			
		}
		else {
			page.setFilterName("");
			computerService.readByPage(page);
		}
		
		page.setNumberOfPages((Integer) (page.getTotalNumberOfRecords()/page.getComputerPerPage())+1); 	
		
//		s.setAttribute("pageData" , page);
				
		logger.debug(page.toString());
		model.addAttribute("pageData",page);		
		
		if(page != null)
			logger.info(page.toString());
		
		return "dashboard";
	}

}
