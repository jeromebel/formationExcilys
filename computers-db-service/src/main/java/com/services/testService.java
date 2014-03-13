package com.services;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;

import com.dao.ComputerDAOImpl;
import com.om.Computer;

@Controller
public class testService {

	public static void main(String[] args) {
		Computer c = new Computer();
		c.setName("BelOrdi");
		
		ComputerDAOImpl cdao = new ComputerDAOImpl();
		try {
			cdao.create(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ComputerServiceImpl serv = new ComputerServiceImpl();
//		serv.create(c);

	}

}
