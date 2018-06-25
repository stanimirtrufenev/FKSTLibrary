<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*" %>  
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FKST Library</title>
<style type="text/css">
div.absolute {
	position: absolute;
	top: 180px;
	left: 200px;
	width: 600px;
	height: 100px;
	border: none;
	z-index: -1;
}

input[type=text].beautiful {
	width: 70%;
	box-sizing: border-box;
	border: none;
	border-radius: 4px;
	font-size: 16px;
	background-color: white;
	background-position: 10px 10px;
	background-repeat: no-repeat;
	padding: 12px 20px 12px 40px;
}

input[type=submit].shiny {
	background-color: #4CAF50;
	border: none;
	color: white;
	padding: 16px 32px;
	text-decoration: none;
	margin: 4px 2px;
	cursor: pointer;
}

input.new {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 10px 25px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
}
.main
		{
			width:700px;;
			margin-left:250px;
			padding: 10px;
			border: 5px solid grey;
			background-color: white;
			
		}
		table
		{
			font-family: arial, sans-serif;
			border-collapse: collapse;
			width: 600px;
			background-color: white;
		}
		td
		{
			border: 5px solid silver;
			text-align: center;
			padding: 8px;
		}
</style>
<script>
		
			function validate()
			{
				var name = document.myform.txt_name;
				
				if (name.value == "")
				{
					window.alert("please enter name of the Book");
					name.focus();
					return false;
			}
			
		</script>
</head>
<body>
	<div><jsp:include page="logedHeader.jsp" /></div>
	<div class="main">	
	<form method="post" name="myform">
	<table>	

	<tr>
					<td>From Date:</td>
					<td><input class="beautiful" type="date" name="txt_fromdate"/></td>
				</tr>
				<tr>
					<td>To Date</td>
					<td><input class="beautiful" type="date" name="txt_todate"/></td>
				</tr>
				<tr>
				<td><input type="submit" name="btn_report" value="Make Report"></td>	
			</tr>
				
					
	</table>
	</form>
		<br>
		<br>
		<br>
		<table>
		
			<tr>
				<th>Title</th>
				<th>ISBN</th>
				<th>Date</th>
				<th>Update</th>
				<th>Delete</th>
			</tr>
		<%
		
		try
		{	
			Class.forName("com.mysql.jdbc.Driver");  //load driver 
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_lords?autoReconnect=true&useSSL=false","root","admin");  //creat connection 
			
			PreparedStatement pstmt=null; //create statement
			
			if(request.getParameter("btn_report")!=null)
			{
					if(request.getParameter("txt_fromdate")!=null && request.getParameter("txt_fromdate").equals("")){
						if(request.getParameter("txt_todate")!=null && request.getParameter("txt_todate").equals("")){
							pstmt=con.prepareStatement("SELECT * from books");
						}else{
							pstmt=con.prepareStatement("SELECT * from books WHERE date <= \'" + request.getParameter("txt_todate") + "\'");
						}
					}else{
						if(request.getParameter("txt_todate")!=null && request.getParameter("txt_todate").equals("")){
							pstmt=con.prepareStatement("SELECT * from books WHERE date >= \'" + request.getParameter("txt_fromdate") + "\'");
						}else{
							pstmt=con.prepareStatement("SELECT * from books WHERE date >= \'" + request.getParameter("txt_fromdate") +  "\' AND date <= \'" + request.getParameter("txt_todate") + "\'");
						}
					}
			
				
				ResultSet rs=pstmt.executeQuery(); //execute query and set in resultset object rs.  
				
				while(rs.next())
				{	
			%>
					<tr>
						<td><%=rs.getString(2)%></td>
						<td><%=rs.getString(3)%></td>
						<td><%=rs.getString(15)%></td>
						
						<td><a href="http://localhost:8080/FKSTLibrary/UpdateBook?edit=<%=rs.getInt(1)%> ">Edit</a></td>
						<td><a href="?delete=<%=rs.getInt(1)%> ">Delete</a></td>
						
					</tr>
			<%
				}

			}

			
		}
		catch(Exception e)
		{
			out.println(e);
		}
		
		%>
		
		</table>
		
		</div>
</body>
</html>