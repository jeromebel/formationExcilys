package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class ComputerDTO {
	private String id;
	private String companyName;
	private String companyId;
	@NotEmpty
	private String name;
	private String introduced;
	private String discontinued;

	
	public String toString(){
		return "\n"+id +" : " + name +" "+ introduced +"->"+ discontinued +" " + companyName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String company) {
		this.companyName = company;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}	
	
}
