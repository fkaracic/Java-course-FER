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
<title>Insert title here</title>
</head>
<body>
	<h1>Invalid request</h1>
	Return to home:
	<a href="/webapp2">HOME</a>
</body>
</html>