package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.dto.ComputerDTO;
import com.services.ComputerService;
import com.servlet.wrapper.PageWrapper;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = LoggerFactory.getLogger(Home.class);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
			ComputerService.readFilterByName(page);			
		}
		else {
			page.setFilterName("");
			ComputerService.readByPage(page);
		}
		
		page.setNumberOfPages((Integer) (page.getTotalNumberOfRecords()/page.getComputerPerPage())+1); 	
		
		s.setAttribute("pageData" , page);
				
		request.setAttribute("pageData", page);
		
		if(page != null)
			logger.info(page.toString());
		request.getRequestDispatcher("WEB-INF/view/dashboard.jsp").forward(request, response);
	}

}
