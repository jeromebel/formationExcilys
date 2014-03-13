<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="pageData" required="true" type="java.lang.Object"%>
<%@ attribute name="currPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="computersPerPage" required="true"
	type="java.lang.Integer"%>
<%@ attribute name="totalPages" required="true" type="java.lang.Integer"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<ul>

	<c:if test="${ currPage == 1 }">

		<li class="prev disabled"><a href=""><spring:message
					code="label.first"></spring:message></a></li>
		<li class="disabled"><a href=""><spring:message
					code="label.prev"></spring:message> </a></li>
	</c:if>
	<c:if test="${ currPage > 1 }">
		<li class="prev "><h:pageLink pageData="${pageData}" numPage="1"><spring:message
					code="label.first"></spring:message></h:pageLink></li>
		<li><h:pageLink pageData="${pageData}" numPage="${currPage-1}"><spring:message
					code="label.prev"></spring:message></h:pageLink></li>
	</c:if>

	<c:forEach var="page" begin="${(currPage - 5 < 1) ? 1 :  currPage - 5}"
		end="${(currPage + 5 > totalPages) ? totalPages :  currPage + 5}">
		<c:choose>
			<c:when test="${currPage != page }">
				<li>
				<h:pageLink pageData="${pageData}" numPage="${page}">${page}</h:pageLink>
				</li>
			</c:when>
			<c:otherwise>
				<li class="active"><a href="Home?pageNum=${page}">${page}</a></li>
			</c:otherwise>
		</c:choose>

	</c:forEach>

	<c:if test="${ currPage == totalPages }">
		<li class="disabled"><a href=""><spring:message
					code="label.next"></spring:message></a></li>
		<li class="next disabled"><a href=""><spring:message
					code="label.last"></spring:message></a></li>
	</c:if>
	<c:if test="${ currPage != totalPages }">
		<li><h:pageLink pageData="${pageData}" numPage="${currPage+1}"><spring:message
					code="label.next"></spring:message></h:pageLink></li>
		<li class="next "><h:pageLink pageData="${pageData}" numPage="${totalPages}"><spring:message
					code="label.last"></spring:message></h:pageLink></li>
	</c:if>



</ul>