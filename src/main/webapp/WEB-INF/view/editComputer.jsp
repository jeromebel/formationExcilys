<jsp:include page="../../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">

	<h1><spring:message code="label.header.editcomputer"></spring:message></h1>

	<form:form commandName="computerEdit" action="EditComputer" method="POST">
		<form:errors path="*" cssClass="ui-state-error ui-corner-all" element="div" />
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="label.table.header.computer"></spring:message>:</label>
				<div class="input">
					<form:input path="name" value='${computerEdit.name}'
					data-validation="required" 
					data-validation-error-msg="Is required"/>
					<span class="help-inline">Required</span>
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced"><spring:message code="label.table.header.introduced"></spring:message>:</label>
				<div class="input">
					<form:input path="introduced"
						value='${computerEdit.introduced}' 
						data-validation="date"
						data-validation-format="yyyy-mm-dd"
						data-validation-optional="true"
						data-validation-error-msg="Please enter a valide format YYYY-MM-DD"
						/> <span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message code="label.table.header.discontinued"></spring:message>:</label>
				<div class="input">
					<form:input path="discontinued"
						value='${computerEdit.discontinued}' 
						data-validation="date"
						data-validation-format="yyyy-mm-dd"
						data-validation-optional="true"
						data-validation-error-msg="Please enter a valide format YYYY-MM-DD"
						/> <span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>

			<div class="clearfix">
				<label for="company"><spring:message code="label.table.header.company"></spring:message>:</label>
				<div class="input">
					<form:select path="company.id">
						<option value="0">--</option>						
						<c:forEach var="c" items="${companies}">
							<c:choose>
								<c:when test="${c.id == companyId}">
								    <option value="${c.id}" selected>${c.name }</option>
								</c:when>
								<c:otherwise>
								    <option value="${c.id}">${c.name }</option>
								</c:otherwise>
							</c:choose>							
						</c:forEach>
					</form:select>
				</div>
			</div>

		</fieldset>
		<div class="actions">
			<button type="submit" name="computerId" value="${computerEdit.id}"
				class="btn primary">
				<spring:message code="label.buttonedit"></spring:message>
			</button>
			<a href="Home" class="btn"><spring:message code="label.buttoncancel"></spring:message></a>
		</div>
	</form:form>
</section>

<script type="text/javascript">
	$('.datepicker').datepicker();
	$('.datepicker').datepicker("option", "dateFormat", "yy-mm-dd");
	$('.datepicker').datepicker("option", "changeYear", true);
	
// 	$.validate({
// 		addValidClassOnAll : true
// 	});
</script>

<jsp:include page="../../include/footer.jsp" />