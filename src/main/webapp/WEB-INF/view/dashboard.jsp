<jsp:include page="../../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<section id="main">
	<h1 id="homeTitle">${pageData.totalNumberOfRecords} Computers found</h1>
	<div id="actions">
		<form action="Home" method="GET">
			<input type="search" id="searchbox" name="filterName" value=""
				placeholder="Search name"><input type="submit"
				id="searchsubmit" value="Filter by name" class="btn primary">
		</form>
		<a class="btn success" id="add" href="AddComputer">Add Computer</a>
	</div>

	<div class="pagination">
		<h:PaginationLinks currPage="${pageData.pageNumber}"
			totalPages="${pageData.numberOfPages}" computersPerPage="${pageData.computerPerPage}"></h:PaginationLinks>
	</div> 


	<form action="Home" class="formular" method="GET">
		<div class="actions">
			<div class="clearfix">
				<label for="computerPerPage">Computer per Page:</label>
				<div class="input">
					<select name="computersPerPage">
						<option selected value="${pageData.computerPerPage }">${pageData.computerPerPage }</option>
						<c:if test="${pageData.computerPerPage !=10 }">
							<option value="10">10</option>
						</c:if>
						<c:if test="${pageData.computerPerPage !=20 }">
							<option value="20">20</option>
						</c:if>
						<c:if test="${pageData.computerPerPage !=50 }">
							<option value="50">50</option>
						</c:if>
					</select>
				</div>
			</div>

			<button type="submit" name="pageNum" value="${pageData.pageNumber}"
				class="btn primary">
				<i class="icon icon-cross"></i>Valid
			</button>
		</div>
	</form>


	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th style="width:20%;"><h:OrderByLink pageData="${pageData }" field="c.name" text="Computer name"></h:OrderByLink></th>
				<th style="width:20%;"><h:OrderByLink pageData="${pageData }" field="c.introduced" text="Introduced Date"></h:OrderByLink> </th>
				<th style="width:20%;"><h:OrderByLink pageData="${pageData }" field="c.discontinued" text="Discontinued Date"></h:OrderByLink></th>
				<th style="width:20%;"><h:OrderByLink pageData="${pageData }" field="f.name" text="Company"></h:OrderByLink></th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>

		</thead>
		<tbody>

			<c:forEach var="computerDTO" items="${pageData.results}">
				<tr>
					<td>${computerDTO.name}</td>
					<td>${computerDTO.introduced}</td>
					<td>${computerDTO.discontinued}</td>
					<td>${computerDTO.companyName}</td>
					<td>
						<form method="GET" action="EditComputer">
							<button type="submit" name="idComputer" value="${computerDTO.id}"
								class="btn btn-sm">
								<span class="ui-icon ui-icon-pencil"
									style="float: left; margin-right: .3em;"></span>
							</button>
						</form>
					</td>
					<td>
						<form method="GET" action="DeleteComputer">
							<button type="submit" name="idComputer" value="${computerDTO.id}"
								class="btn btn-sm">
								<span class="ui-icon ui-icon-close"
									style="float: left; margin-right: .3em;"></span>
							</button>
						</form>
					</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</section>

<jsp:include page="../../include/footer.jsp" />
