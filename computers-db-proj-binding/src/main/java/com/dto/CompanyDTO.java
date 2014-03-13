package com.dto;

public class CompanyDTO {
	String id;
	String name;
	
//	private CompanyDTO(CompanyDTOBuilder builder){
//		this.id = builder.id;
//		this.name = builder.name;
//	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	public static class CompanyDTOBuilder {
//		private String id;
//		private String name;
//		
//		public CompanyDTOBuilder id(String newId){
//			this.id = newId;
//			return this;
//		}
//		
//		public CompanyDTOBuilder name(String newName){
//			this.name = newName;
//			return this;
//		}
//		
//		public CompanyDTO build() {
//			return new CompanyDTO(this);
//		}
//	}
	
	
}
