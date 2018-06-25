<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<c:choose>
					<c:when test="${sessionScope.loggedUser != null && sessionScope.loggedUser.getIsAdmin() == 1}">
					<c:set var="user" scope="session" value="${sessionScope.loggedUser}"/>
					<li class="dropdown"><a style="cursor: pointer;">Books</a>		
					<div class="dropdown-content">
												<a href="http://localhost:8080/FKSTLibrary/CreateBook">Add Book</a>
												<a href="http://localhost:8080/FKSTLibrary/BooksAll">All Books</a>
											</div>
											
					</li>
					<li class="dropdown"><a style="cursor: pointer;">Genres</a>
					<div class="dropdown-content">
												<a href="http://localhost:8080/FKSTLibrary/AddGenre">Add Genre</a>
												<a href="http://localhost:8080/FKSTLibrary/Genres">All Genres</a>
											</div>
					</li>
					<li><a href="http://localhost:8080/FKSTLibrary/Reports">Reports</a></li>
					</c:when></c:choose>
					<li class="dropdown"><a style="cursor: pointer;">My Shelves</a>
					<c:choose>
					<c:when test="${sessionScope.loggedUser != null}">
					<c:set var="user" scope="session" value="${sessionScope.loggedUser}"/>
					<div class="dropdown-content">
												<c:forEach items="${user.bookshelves}" var="bookshelf">
													<a href="http://localhost:8080/FKSTLibrary/MyBooks?bookshelfId=${bookshelf.id}"><c:out
															value="${bookshelf.name}"/></a>
												</c:forEach>
												<a href="http://localhost:8080/FKSTLibrary/MyBooks">Add Bookshelf</a>
											</div>
											</c:when></c:choose>
					</li>
					
					<li class="dropdown"><a class="dropbtn" style="cursor: pointer;">Community</a>
						<div class="dropdown-content">
							<a href="http://localhost:8080/FKSTLibrary/SearchPeople">People</a>
						</div></li>
							<li class="dropdown"><a class="dropbtn" style="cursor: pointer;">My Profile</a>
						<div class="dropdown-content">
							<a href="http://localhost:8080/FKSTLibrary/ViewProfile">View Profile</a>
							<a href="http://localhost:8080/FKSTLibrary/MyComments">My Comments</a> <a href="http://localhost:8080/FKSTLibrary/SignOut">Sign Out</a>
						</div></li>
				</ul>
			</nav>
			<div id="hgroup" style="position: absolute; right: 60px; top: 0">
				<h2>Welcome ${sessionScope.loggedUser.getName()}</h2>
			</div>
		</header>
	</div>
	<div><jsp:include page="searchingBar.jsp" /></div>
</body>
</html>