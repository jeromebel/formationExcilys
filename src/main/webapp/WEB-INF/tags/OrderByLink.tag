<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="field" required="true" type="java.lang.String"%>
<%@ attribute name="text" required="false" type="java.lang.String"%>
<%@ attribute name="pageData" required="true" type="java.lang.Object"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:choose>
	<c:when test="${pageData.orderedBy == field}">
		<c:if test="${pageData.orderDirection == 'ASC'}">
			<a href="Home?orderBy=${field }&orderDirection=DESC"> <c:choose>
					<c:when test="${field == 'c.name' }">
						<spring:message code="label.table.header.computer"></spring:message>
					</c:when>
					<c:when test="${field == 'c.introduced' }">
						<spring:message code="label.table.header.introduced"></spring:message>
					</c:when>
					<c:when test="${field == 'c.discontinued' }">
						<spring:message code="label.table.header.discontinued"></spring:message>
					</c:when>
					<c:when test="${field == 'f.name' }">
						<spring:message code="label.table.header.company"></spring:message>
					</c:when>
					<c:otherwise>
				${text}
			</c:otherwise>
				</c:choose> <span class="ui-icon ui-icon-carat-1-n"
				style="float: left; margin-right: .3em;"></span>
			</a>
		</c:if>
		<c:if test="${pageData.orderDirection == 'DESC'}">
			<a href="Home?orderBy=${field }&orderDirection=ASC"> <c:choose>
					<c:when test="${field == 'c.name' }">
						<spring:message code="label.table.header.computer"></spring:message>
					</c:when>
					<c:when test="${field == 'c.introduced' }">
						<spring:message code="label.table.header.introduced"></spring:message>
					</c:when>
					<c:when test="${field == 'c.discontinued' }">
						<spring:message code="label.table.header.discontinued"></spring:message>
					</c:when>
					<c:when test="${field == 'f.name' }">
						<spring:message code="label.table.header.company"></spring:message>
					</c:when>
					<c:otherwise>
				${text}
			</c:otherwise>
				</c:choose><span class="ui-icon ui-icon-carat-1-s"
				style="float: left; margin-right: .3em;"></span>
			</a>
		</c:if>
	</c:when>
	<c:otherwise>
		<a href="Home?orderBy=${field }"> <c:choose>
				<c:when test="${field == 'c.name' }">
					<spring:message code="label.table.header.computer"></spring:message>
				</c:when>
				<c:when test="${field == 'c.introduced' }">
					<spring:message code="label.table.header.introduced"></spring:message>
				</c:when>
				<c:when test="${field == 'c.discontinued' }">
					<spring:message code="label.table.header.discontinued"></spring:message>
				</c:when>
				<c:when test="${field == 'f.name' }">
					<spring:message code="label.table.header.company"></spring:message>
				</c:when>
				<c:otherwise>
				${text}
			</c:otherwise>
			</c:choose> <span class="ui-icon ui-icon-carat-2-n-s"
			style="float: left; margin-right: .3em;"></span>
		</a>
	</c:otherwise>
</c:choose>