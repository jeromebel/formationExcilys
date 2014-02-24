package com;

import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;

import com.om.Computer;

public class Main {
	
	public static void main(String[] args) {
		GenericApplicationContext ctxt = new GenericApplicationContext();
		
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(ctxt);
				
		String[] basePackages = {"com.services","com.om"};
		scanner.scan(basePackages);
		ctxt.refresh();
		
		Computer computer = ctxt.getBean("computer",Computer.class);
		computer.setName("Ordi");
		assert computer.getName() != null;
		System.out.println(computer.getName());

	}

}
