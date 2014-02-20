package com.servlet.wrapper;

import java.util.List;

/**
 * @author excilys
 *
 * @param <T>
 */
public class PageWrapper<T> {
	
	private List<T> results;
	private String orderedBy;
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

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
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
		sb.append("Order by : ").append(this.getOrderedBy());
		sb.append("Direction : ").append(this.getOrderDirection());
		sb.append("Filtred by : ").append(this.getFilterName());
		sb.append("Results : ").append(this.getResults());
				
		return sb.toString();
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public int getComputerPerPage() {
		return computerPerPage;
	}

	public void setComputerPerPage(int computerPerPage) {
		this.computerPerPage = computerPerPage;
	}
}
