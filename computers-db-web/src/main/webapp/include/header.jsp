<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Excilys Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
<script src="resources/js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/style/jquery-ui.min.css">
<script src="resources/js/jquery-ui.custom.min.js"></script>
<script src="resources/js/form-validator/jquery.form-validator.min.js"></script>

</head>
<body>
	<header class="topbar">
		<div class="fill">
			<div class="pull-left">
				<h1>
					<a href="index.jsp"> Application - Computer Database </a>

				</h1>
			</div>
			<div class="pull-right">
				<a href="?lang=en"><button type="submit" class="btn btn-sm">
						EN</button></a>
				<a href="?lang=fr"><button type="submit" class="btn btn-sm">
						FR</button></a>
				<form method="POST" action="j_spring_security_logout">
					<button type="submit" class="btn btn-sm">
						<spring:message code="label.logout" />
					</button>
				</form>
			</div>
		</div>
	</header>
</body>