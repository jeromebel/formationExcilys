<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="currPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="computersPerPage" required="true"
	type="java.lang.Integer"%>
<%@ attribute name="totalPages" required="true" type="java.lang.Integer"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<ul>
	
	<c:if test="${ currPage == 1 }">
	
		<li class="prev disabled"><a href=""><spring:message code="label.first"></spring:message></a></li>
		<li class="disabled"><a href=""><spring:message code="label.prev"></spring:message> </a></li>
	</c:if>
	<c:if test="${ currPage > 1 }">
		<li class="prev "><a href="Home?pageNum=1"><spring:message code="label.first"></spring:message></a></li>
		<li ><a href="Home?pageNum=${currPage-1}"><spring:message code="label.prev"></spring:message></a></li>
	</c:if>

	<c:forEach var="page" begin="${(currPage - 5 < 1) ? 1 :  currPage - 5}" end="${(currPage + 5 > totalPages) ? totalPages :  currPage + 5}">
		<c:choose>
			<c:when test="${currPage != page }">
				<li><a href="Home?pageNum=${page}">${page}</a></li>
			</c:when>
			<c:otherwise>
				<li class="active"><a href="Home?pageNum=${page}">${page}</a></li>
			</c:otherwise>
		</c:choose>

	</c:forEach>

	<c:if test="${ currPage == totalPages }">
		<li class="disabled"><a href=""><spring:message code="label.next"></spring:message></a></li>
		<li class="next disabled"><a href=""><spring:message code="label.last"></spring:message></a></li>
	</c:if>
	<c:if test="${ currPage != totalPages }">
		<li ><a href="Home?pageNum=${currPage+1}"><spring:message code="label.next"></spring:message></a></li>
		<li class="next "><a href="Home?pageNum=${totalPages}"><spring:message code="label.last"></spring:message></a></li>
	</c:if>



</ul>