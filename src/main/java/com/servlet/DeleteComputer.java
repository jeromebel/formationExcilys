package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dto.ComputerDTO;
import com.dto.MapComputer;
import com.om.Computer;
import com.services.ComputerService;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("idComputer");
				
		Computer c = ComputerService.readFilterByID(Long.valueOf(id));
		ComputerDTO cDTO = MapComputer.computerToDTO(c);
		request.setAttribute("computerDelete", cDTO);
		request.getRequestDispatcher("WEB-INF/view/confirmationDeleteComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerId   = request.getParameter("computerId");
		
		ComputerService.delete(computerId);
				
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
