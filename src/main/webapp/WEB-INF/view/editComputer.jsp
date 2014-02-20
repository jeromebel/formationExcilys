<jsp:include page="../../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">

	<h1>Edit Computer</h1>
	<c:if test="${error == true }">

		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all">
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin-right: .3em;"></span> ${message}
			</div>
		</div>
	</c:if>
	<form action="EditComputer" class="formular" method="POST">
		<fieldset>
			<%-- 			<input type="text" name="computerId" value='${computerEdit.id}' style="visibility:hidden"> --%>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="computerName" value='${computerEdit.name}'
					data-validation="required" 
					data-validation-error-msg="Is required">
					<span class="help-inline">Required</span>
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="text" name="introducedDate"
						value='${computerEdit.introduced}' 
						data-validation="date"
						data-validation-format="yyyy-mm-dd"
						data-validation-optional="true"
						data-validation-error-msg="Please enter a valide format YYYY-MM-DD"
						/> <span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input  type="text" name="discontinuedDate"
						value='${computerEdit.discontinued}' 
						data-validation="date"
						data-validation-format="yyyy-mm-dd"
						data-validation-optional="true"
						data-validation-error-msg="Please enter a valide format YYYY-MM-DD"
						/> <span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>

			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
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
					</select>
				</div>
			</div>

		</fieldset>
		<div class="actions">
			<button type="submit" name="computerId" value="${computerEdit.id}"
				class="btn primary">
				<i class="icon icon-cross"></i>Edit
			</button>
			<!-- 			<input type="submit" name="computerId" value='Edit' class="btn primary"> -->
			or <a href="Home" class="btn">Cancel</a>
		</div>
	</form>
</section>

<script type="text/javascript">
	$('.datepicker').datepicker();
	$('.datepicker').datepicker("option", "dateFormat", "yy-mm-dd");
	$('.datepicker').datepicker("option", "changeYear", true);
	
	$.validate({
		addValidClassOnAll : true
	});
</script>

<jsp:include page="../../include/footer.jsp" />