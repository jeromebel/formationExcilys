<jsp:include page="../../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">

	<h1>
		<spring:message code="label.header.confirmdelete"></spring:message>
	</h1>

	<c:choose>
		<c:when test="${computerDelete != null }">
			<form action="DeleteComputer" class="formular" method="POST">
				<spring:message code="message.askdelete"></spring:message>
				${computerDelete.name}
				<div class="actions">
					<button type="submit" name="computerId"
						value="${computerDelete.id}" class="btn primary">
						<spring:message code="label.buttonyes"></spring:message>
					</button>
					<a href="Home" class="btn"><spring:message
							code="label.buttoncancel"></spring:message></a>
				</div>
			</form>
		</c:when>
		<c:when test="${delete != null }">
			<spring:message code="message.confirmdelete"></spring:message>
		</c:when>

	</c:choose>
</section>

<jsp:include page="../../include/footer.jsp" />