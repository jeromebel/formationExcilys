package com.om;



import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
 
public class Customer {
 
	@NotEmpty //make sure name is not empty
	private String name;
 
	@Range(min = 1, max = 150) //age need between 1 and 150
	private int age;
	
	@DateTimeFormat(pattern="yyyy-mm-dd")
	@Past
	private DateTime dateOfAdd; 
	
	
	public DateTime getDateOfAdd() {
		return dateOfAdd;
	}

	public void setDateOfAdd(DateTime date) {
		this.dateOfAdd = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	//getter and setter methods
	
 
}
