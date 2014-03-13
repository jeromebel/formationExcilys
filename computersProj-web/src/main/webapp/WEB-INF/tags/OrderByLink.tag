<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="field" required="true" type="java.lang.String"%>
<%@ attribute name="text" required="false" type="java.lang.String"%>
<%@ attribute name="pageData" required="true" type="java.lang.Object"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<c:choose>
	<c:when test="${pageData.orderBy == field}">
		<c:if test="${pageData.orderDirection == 'ASC'}">
			<h:pageLink pageData="${pageData}" orderField="${field }" orderDir="DESC">
			<jsp:doBody />
				<span class="ui-icon ui-icon-carat-1-n"
				style="float: left; margin-right: .3em;"></span>
			</h:pageLink>
		</c:if>
		<c:if test="${pageData.orderDirection == 'DESC'}">
			<h:pageLink pageData="${pageData}" orderField="${field }" orderDir="ASC">
			 <jsp:doBody />
			 <span class="ui-icon ui-icon-carat-1-s"
				style="float: left; margin-right: .3em;"></span>
			</h:pageLink>
		</c:if>
	</c:when>
	<c:otherwise>
		<h:pageLink pageData="${pageData}" orderField="${field }">
			<jsp:doBody />
			<span class="ui-icon ui-icon-carat-2-n-s"
			style="float: left; margin-right: .3em;"></span>
		</h:pageLink>
	</c:otherwise>
</c:choose>