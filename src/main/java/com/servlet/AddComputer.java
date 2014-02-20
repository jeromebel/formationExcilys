package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Company;
import com.services.CompanyService;
import com.services.ComputerService;
import com.servlet.wrapper.PageWrapper;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = CompanyService.readAll();
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("WEB-INF/view/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean formValid = true;
		StringBuilder message = new StringBuilder();
		String computerName = request.getParameter("computerName");
		if(computerName.equals("")) {
			formValid = false;
			message.append("Please enter a computer Name");
		}
		String introduced  = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");		
		String companyId    = request.getParameter("company");
		
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
			
			ComputerService.create(MapComputer.dtoToComputer(cDTO));
						
			page.setFilterName(computerName);
						
			page.setNumberOfPages((Integer) (page.getResults().size())/page.getComputerPerPage()); 		
			
			s.setAttribute("pageData" , page);
			
			request.setAttribute("pageData", page);
			request.getRequestDispatcher("WEB-INF/view/dashboard.jsp").forward(request, response);
		}
		else{
			List<Company> companies = CompanyService.readAll();
			request.setAttribute("companies", companies);
			request.setAttribute("error", true);
			request.setAttribute("message", message.toString());
			request.getRequestDispatcher("WEB-INF/view/addComputer.jsp").forward(request, response);
		}
			
		
		
	}

}
