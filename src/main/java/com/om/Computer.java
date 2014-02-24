package com.om;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Computer{

	
	private long id;
	private Company company;
	private String name;
	private Date introduced;
	private Date discontinued;
		
	
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {		
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company comp) {
		if (comp == null){
			comp = new Company();
			comp.setId(0);
			comp.setName("Inconnue");
		}			
		this.company = comp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {						
		return "\n"+getId() + " : " + getName()+" comp :"+getCompany().getName()+" "+getIntroduced()+"->"+getDiscontinued();
	}
	
}
