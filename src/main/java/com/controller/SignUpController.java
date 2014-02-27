package com.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.om.Customer;

@Controller
@RequestMapping("/customer")
public class SignUpController {
 
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String addCustomer(@Valid Customer customer, BindingResult result) {
 
		if (result.hasErrors()) {
			System.out.println("Error");
			return "hello";
		} else {
			System.out.println("Name : "+customer.getName());
			System.out.println("Age : "+customer.getAge());
			System.out.println(" Date : "+customer.getDateOfAdd());
			return "hello";
		}
 
	}
 
	@RequestMapping(method = RequestMethod.GET)
	public String displayCustomerForm(ModelMap model) {
		Customer c = new Customer();
		c.setName("Frank");
		model.addAttribute("customer", c);
		return "hello";
 
	}
 
}
