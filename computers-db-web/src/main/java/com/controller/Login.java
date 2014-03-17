package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Login")
public class Login {

	 @RequestMapping(method = RequestMethod.GET)
		protected String doGet(ModelMap model , String idComputer , String failed) {
		 	if (failed != null){
		 		model.addAttribute("errorCode", "loginfail");
		 	}			
			return "loginPage";
	    }
	 
}
