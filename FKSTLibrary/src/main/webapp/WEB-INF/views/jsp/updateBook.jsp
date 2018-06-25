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

.beautiful3 {
	width: 300%;
	height:200px;
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
</style>
<script>
		
			function validate()
			{
				var name = document.myform.txt_name;
				
				if (name.value == "")
				{
					window.alert("please enter name of the Genre");
					name.focus();
					return false;
			}
			
		</script>
</head>
<body>
	<div><jsp:include page="logedHeader.jsp" /></div>
	<div class="absolute">
		<form method="post" name="myform"  onsubmit="return validate();">
		<table>		
		<%
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); //load driver  
		
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_lords?autoReconnect=true&useSSL=false","root","admin"); // create connection  
	
			if(request.getParameter("edit")!=null) 
			{
				int id=Integer.parseInt(request.getParameter("edit"));
		
				String title, author, isbn, poster, source, description;
				
				int pages;
		
				PreparedStatement pstmt=null; // create statement
				
				pstmt=con.prepareStatement("select * from books where book_id=?"); // sql select query
				pstmt.setInt(1,id);
				ResultSet rs=pstmt.executeQuery(); // execute query store in resultset object rs.
				
				while(rs.next()) 
				{
					id=rs.getInt(1);
					title=rs.getString(2);
					isbn=rs.getString(3);
					poster=rs.getString(4);
					source=rs.getString(16);
					description=rs.getString(5);
					pages=rs.getInt(6);
			%>
				<tr>
					<td>Title</td>
					<td><input class="beautiful" type="text" name="txt_title" value="<%=title%>"/></td>
				</tr>
				<tr>
					<td>ISBN</td>
					<td><input class="beautiful" type="text" name="txt_isbn" value="<%=isbn%>"/></td>
				</tr>
				<tr>
					<td>Poster</td>
					<td><input class="beautiful" type="text" name="txt_poster" value="<%=poster%>"/></td>
				</tr>
				<tr>
					<td>Pages</td>
					<td><input class="beautiful" type="text" name="txt_pages" value="<%=pages%>"/></td>
				</tr>
				<tr>
					<td>Source</td>
					<td><input class="beautiful" type="text" name="txt_source" value="<%=source%>"/></td>
				</tr>
				<tr>
					<td>Description</td>
					<td><input class="beautiful3" type="text" name="txt_desc" value="<%=description%>"/></td>
				</tr>	
				<tr>
				<td><input type="submit" name="btn_update" value="Update"></td>	
			</tr>
				
				<input type="hidden" name="txt_hide" value="<%=id%>">
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
		</form>
	</div>
</body>
</html>