<jsp:include page="../../include/header.jsp" />

<section id="main">

	<h1>Delete Computer</h1>
	
	<form action="DeleteComputer" class="formular" method="POST">
		Are you sure to delete ${computerDelete.name}
		<div class="actions">
			<button type="submit" name="computerId" value="${computerDelete.id}" class="btn primary">
				<i class="icon icon-cross"></i>Yes
			</button>
			or <a href="Home" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="../../include/footer.jsp" />