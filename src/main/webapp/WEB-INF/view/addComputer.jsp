<jsp:include page="../../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<section id="main">

	<h1>Add Computer</h1>
	<c:if test="${error == true }">

		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all">
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin-right: .3em;"></span> ${message}
			</div>
		</div>
	</c:if>
	<form action="AddComputer" class="formular" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="computerName" data-validation="required"
						data-validation-error-msg="Is required" /> <span
						class="help-inline">Required</span>
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input class="datepicker" type="text" name="introducedDate"
						data-validation="date" data-validation-format="yyyy-mm-dd"
						data-validation-optional="true"
						data-validation-error-msg="Please enter a valide format YYYY-MM-DD"
						value="2008-02-06" /> <span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input class="datepicker" type="text" name="discontinuedDate"
						data-validation="date" data-validation-format="yyyy-mm-dd"
						data-validation-optional="true"
						data-validation-error-msg="Please enter a valide format YYYY-MM-DD"
						value="2010-10-26" /> <span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
						<option value="inconnue">--</option>
						<c:forEach var="c" items="${companies}">
							<option value="${c.id}">${c.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Add" class="btn primary"> or <a
				href="Home" class="btn">Cancel</a>
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