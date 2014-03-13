<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="pageData" required="true" type="java.lang.Object"%>
<%@ attribute name="numPage" required="false" type="java.lang.String"%>
<%@ attribute name="orderField" required="false" type="java.lang.String"%>
<%@ attribute name="orderDir" required="false" type="java.lang.String"%>

<c:choose>
	<c:when test="${orderField != null}">
		<a
			href="Home?orderBy=${orderField}&
	orderDirection=${orderDir}&
	pageNum=${pageData.pageNumber}&
	computersPerPage=${pageData.computerPerPage}&
	filterName=${pageData.filterName}">
			<jsp:doBody />
		</a>
	</c:when>
	<c:when test="${numPage != null}">
		<a
			href="Home?orderBy=${pageData.orderBy}&
	orderDirection=${pageData.orderDirection}&
	pageNum=${numPage}&
	computersPerPage=${pageData.computerPerPage}&
	filterName=${pageData.filterName}">
			<jsp:doBody />
		</a>
	</c:when>
	<c:otherwise>
		<a
			href="Home?orderBy=${pageData.orderBy}&
	orderDirection=${pageData.orderDirection}&
	pageNum=${pageData.pageNumber}&
	computersPerPage=${pageData.computerPerPage}&
	filterName=${pageData.filterName}">
			<jsp:doBody />
		</a>
	</c:otherwise>
</c:choose>
