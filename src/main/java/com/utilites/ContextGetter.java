package com.utilites;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextGetter {
	private static ContextGetter contextGetter;
	private static ApplicationContext context;
	
	private ContextGetter() {}
	
	static {
		 context = new ClassPathXmlApplicationContext("SpringBeans.xml");
	}
	
	public static ContextGetter getInstance(){
		if (contextGetter != null)
			contextGetter = new ContextGetter();
		return contextGetter;
	}
	
	public static ApplicationContext getContext() {
		return context;
	}
}
