<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*" %>  
<%
try
{	
	Class.forName("com.mysql.jdbc.Driver");  //load driver 
	
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_lords?autoReconnect=true&useSSL=false","root","admin");  //create connection 

	if(request.getParameter("delete")!=null)
	{
		int id=Integer.parseInt(request.getParameter("delete"));
		
		PreparedStatement pstmt=null; //create statement
		
		pstmt=con.prepareStatement("delete from books where book_id=? "); //sql delete query
		pstmt.setInt(1,id);
		pstmt.executeUpdate(); //execute query
		
		con.close(); //close connection
	}
}
catch(Exception e)
{
	out.println(e);
}
%>	
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
		
		<table>
		
			<tr>
				<th>Title</th>
				<th>ISBN</th>
				<th>Update</th>
				<th>Delete</th>
			</tr>
		<%
		
		try
		{	
			Class.forName("com.mysql.jdbc.Driver");  //load driver 
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_lords?autoReconnect=true&useSSL=false","root","admin");  //creat connection 

			PreparedStatement pstmt=null; //create statement
		
			pstmt=con.prepareStatement("select * from books"); //sql select query  
			
			ResultSet rs=pstmt.executeQuery(); //execute query and set in resultset object rs.  
		
			while(rs.next())
			{	
		%>
				<tr>
					<td><%=rs.getString(2)%></td>
					<td><%=rs.getString(3)%></td>
					
					<td><a href="http://localhost:8080/FKSTLibrary/UpdateBook?edit=<%=rs.getInt(1)%> ">Edit</a></td>
					<td><a href="?delete=<%=rs.getInt(1)%> ">Delete</a></td>
					
				</tr>
		<%
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