<jsp:include page="../../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">

	<h1>Delete Computer</h1>

	<c:choose>
		<c:when test="${computerDelete.name != null }">
			<form action="DeleteComputer" class="formular" method="POST">
				Are you sure to delete ${computerDelete.name}
				<div class="actions">
					<button type="submit" name="computerId"
						value="${computerDelete.id}" class="btn primary">
						<i class="icon icon-cross"></i>Yes
					</button>
					or <a href="Home" class="btn">Cancel</a>
				</div>
			</form>
		</c:when>
		<c:when test="${message != null }">
			${message}
		</c:when>

	</c:choose>
</section>

<jsp:include page="../../include/footer.jsp" />