<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
body {
	background-color: 
	<%if (request.getSession().getAttribute("pickedBgCol") == null) {%>   
  		<%="#FFFFFF"%>   	
	<%} else {%> 
		<%=request.getSession().getAttribute("pickedBgCol")%> <%}%>
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report</title>
</head>
<body>
	<h1>OS usage</h1>

	<h2>Here are the results of OS usage in survey that we completed</h2>

	<img alt="Os usage" src="/webapp2/reportImage" width="500" height="400" />
	
	<p><font size="5">Return home: <a href="/webapp2/">HOME</a></font>
	

</body>
</html>