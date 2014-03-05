package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.util.ValidDateTime;

public class ComputerDTO {
	private String id;
	private CompanyDTO company;
	@NotEmpty
	private String name;
	@ValidDateTime
	private String introduced;
	@ValidDateTime
	private String discontinued;

	
	public String toString(){
		return "\n"+id +" : " + name +" "+ introduced +"->"+ discontinued +" " + company.getName();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	
}
