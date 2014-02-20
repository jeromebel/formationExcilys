package com.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Company;
import com.om.Computer;
import com.services.CompanyService;
import com.services.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String id = request.getParameter("idComputer");
		
		List<Company> companies = CompanyService.readAll();
		request.setAttribute("companies", companies);
		
//		HttpSession s = request.getSession();
//		s.setAttribute("editId", id);
		
		Computer c = ComputerService.readFilterByID(Integer.valueOf(id));
		ComputerDTO cDTO = MapComputer.computerToDTO(c);
		request.setAttribute("computerEdit", cDTO);
		request.setAttribute("companyId", c.getCompany().getId());
		request.getRequestDispatcher("WEB-INF/view/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			ComputerService.update(MapComputer.dtoToComputer(cDTO));
			
			Computer computerChanged = ComputerService.readFilterByID(Integer.valueOf(computerId));		
			cDTO = MapComputer.computerToDTO(computerChanged);
			
			List<ComputerDTO> computerDTOs = new ArrayList<ComputerDTO>();
			computerDTOs.add(cDTO);
			
			request.setAttribute("nbrComputers", computerDTOs.size());
			request.setAttribute("computersList", computerDTOs);
			request.getRequestDispatcher("WEB-INF/view/dashboard.jsp").forward(request, response);
		}
		else {
			Computer c = ComputerService.readFilterByID(Integer.valueOf(computerId));
			cDTO = MapComputer.computerToDTO(c);
			request.setAttribute("computerEdit", cDTO);
			request.setAttribute("companyId", c.getCompany().getId());
			
			List<Company> companies = CompanyService.readAll();
			request.setAttribute("companies", companies);
			request.setAttribute("error", true);
			request.setAttribute("message", message.toString());
			request.getRequestDispatcher("WEB-INF/view/editComputer.jsp").forward(request, response);
		}
		
	}

}
