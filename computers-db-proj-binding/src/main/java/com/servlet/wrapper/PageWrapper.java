package com.servlet.wrapper;

import java.util.List;
import com.dto.ComputerDTO;

/**
 * @author excilys
 *
 * @param <T>
 */
public class PageWrapper {
	
	private List<ComputerDTO> results;
	private String orderBy;
	private String orderDirection;
	private String filterName;
	private int pageNumber;
	private int computerPerPage;
	private int totalNumberOfRecords;
	private int numberOfPages;	

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public List<ComputerDTO> getResults() {
		return results;
	}

	public void setResults(List<ComputerDTO> results) {
		this.results = results;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}

	public void setTotalNumberOfRecords(int totalNumberOfRecords) {
		this.totalNumberOfRecords = totalNumberOfRecords;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Numero of page : ").append(this.getPageNumber());
		sb.append("Records per page : ").append(this.getComputerPerPage());
		sb.append("Number of pages : ").append(this.getNumberOfPages());
		sb.append("Order by : ").append(this.getOrderBy());
		sb.append("Direction : ").append(this.getOrderDirection());
		sb.append("Filtred by : ").append(this.getFilterName());
		sb.append("Results : ").append(this.getResults());
				
		return sb.toString();
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderedBy) {
		this.orderBy = orderedBy;
	}

	public int getComputerPerPage() {
		return computerPerPage;
	}

	public void setComputerPerPage(int computerPerPage) {
		this.computerPerPage = computerPerPage;
	}
}
