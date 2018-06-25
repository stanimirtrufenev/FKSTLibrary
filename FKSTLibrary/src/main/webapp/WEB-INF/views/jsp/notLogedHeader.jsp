<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<title>FKST Library</title>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="css/styles/layout.css" type="text/css">
<link rel="stylesheet" href="css/styles/dropdownButton.css"
	type="text/css">
<!--[if lt IE 9]><script src="scripts/html5shiv.js"></script><![endif]-->
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
</head>

<body>

	<div class="wrapper row1">
		<header id="header" class="clear">
			<div id="hgroup">
			<img src="images/tulogo.jpg" height="100" width="100" style="position: absolute; left: 60px; top: 0">
				<h1>
					<a href="http://localhost:8080/FKSTLibrary/">FKST Library</a>
				</h1>
				<h2>TU-Sofia</h2>
			</div>
			<nav>
				<ul>
					<li><a href="http://localhost:8080/FKSTLibrary/">Home</a></li>
					<li><a href="http://localhost:8080/FKSTLibrary/Login">My Books</a></li>
					<li><a href="http://localhost:8080/FKSTLibrary/Login">Sign
							in</a></li>
					<li><a href="http://localhost:8080/FKSTLibrary/Register">Sign
							up</a></li>
				</ul>
			</nav>
		</header>
	</div>
	<div><jsp:include page="searchingBar.jsp" /></div>
</body>
</html>