<jsp:include page="../../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">

	<h1>
		<spring:message code="label.header.login"></spring:message>
	</h1>
	
	<c:if test="${errorCode != null}">
	<div class="ui-state-error ui-corner-all"> <spring:message code="error.${errorCode}" /></div>
	</c:if>

	<form method="POST" action="j_spring_security_check">
		<table>
			<tr>
				<td><spring:message code="label.login"></spring:message></td>
				<td><INPUT type="text" name="j_username" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.password"></spring:message></td>
				<td><INPUT type="password" name="j_password" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input name="submit" type="submit"/> </td>
			</tr>

		</table>
	</form>

</section>

<jsp:include page="../../include/footer.jsp" />