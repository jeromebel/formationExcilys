<jsp:include page="../../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">
	<h1 id="homeTitle">${pageData.totalNumberOfRecords}
		<spring:message code="label.datafound"></spring:message>
	</h1>
	<div id="actions">
		<form action="Home" method="GET">
			<input type="search" id="searchbox" name="filterName" value=""
				placeholder="Search name">
			<button type="submit" class="btn primary">
				<span class="ui-icon ui-icon-search"
					style="float: left; margin-right: .3em;"></span>
				<spring:message code="label.buttonsearch"></spring:message>
			</button>
		</form>
		<a class="btn success" id="add" href="AddComputer"><spring:message
				code="label.buttonaddcomputer"></spring:message></a>
	</div>

	<div class="pagination">
		<h:PaginationLinks
			pageData="${pageData}"
			currPage="${pageData.pageNumber}"
			totalPages="${pageData.numberOfPages}"
			computersPerPage="${pageData.computerPerPage}"></h:PaginationLinks>
	</div>


	<form action="Home" method="GET">
		<div class="actions">
			<div class="clearfix">
				<label for="computerPerPage"><spring:message
						code="label.computersperpage"></spring:message>:</label>
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
				<spring:message code="label.buttonvalid"></spring:message>
			</button>
		</div>
	</form>


	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th style="width: 20%;"><h:OrderByLink pageData="${pageData }"
						field="c.name">
						<spring:message code="label.table.header.computer"></spring:message>
					</h:OrderByLink></th>
				<th style="width: 20%;"><h:OrderByLink pageData="${pageData }"
						field="c.introduced">
						<spring:message code="label.table.header.introduced"></spring:message>
					</h:OrderByLink></th>
				<th style="width: 20%;"><h:OrderByLink pageData="${pageData }"
						field="c.discontinued">
						<spring:message code="label.table.header.discontinued"></spring:message>
					</h:OrderByLink></th>
				<th style="width: 20%;"><h:OrderByLink pageData="${pageData }"
						field="f.name">
						<spring:message code="label.table.header.company"></spring:message>
					</h:OrderByLink></th>
				<th><spring:message code="label.buttonedit"></spring:message></th>
				<th><spring:message code="label.buttondelete"></spring:message></th>
			</tr>

		</thead>
		<tbody>

			<c:forEach var="computerDTO" items="${pageData.results}">
				<tr>
					<td>${computerDTO.name}</td>
					<td>${computerDTO.introduced}</td>
					<td>${computerDTO.discontinued}</td>
					<td>${computerDTO.company.name}</td>
					<td>
						<form method="GET" action="EditComputer">
							<button type="submit" name="idComputer" value="${computerDTO.id}"
								class="btn btn-xs">
								<span class="ui-icon ui-icon-pencil"
									style="float: left; margin-right: .3em;"></span>
							</button>
						</form>
					</td>
					<td>
						<form method="GET" action="DeleteComputer">
							<button type="submit" name="idComputer" value="${computerDTO.id}"
								class="btn btn-xs">
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
