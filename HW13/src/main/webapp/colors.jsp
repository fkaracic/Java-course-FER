<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
body {
	background-color: <%if (request.getSession().getAttribute("pickedBgCol") == null) {%>    
  		<%="#FFFFFF"%>    	
	<%}

			else {%> <%=request.getSession().getAttribute("pickedBgCol")%> <%}%>
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Colors</title>
</head>
<body>

	<p>
		<font size="5">Choose background color: <a
			href="/webapp2/setcolor?color=white">WHITE</a> <a
			href="/webapp2/setcolor?color=red">RED</a> <a
			href="/webapp2/setcolor?color=green">GREEN</a> <a
			href="/webapp2/setcolor?color=cyan">CYAN</a></font>
	</p>

	<p>
		<font size="5">Return home: <a href="/webapp2/">HOME</a></font>
</body>
</html>