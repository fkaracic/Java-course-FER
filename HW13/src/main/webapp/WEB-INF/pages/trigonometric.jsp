<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Trigonometric</title>
</head>
<body>
	<p><font size="5">Vrati na naslovnicu: <a href="/webapp2/">NASLOVNICA</a></font>
	
	<h1>Tablica trigonmetrijskih vrijednosti</h1>
	<table border="1">
		<thead>
			<tr>
				<th>Kut u stupnjevima</th>
				<th>Sinus</th>
				<th>Kosinus</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="angle" items="${angles}">
				<tr align="right">
					<td>${angle}</td>
					<td>${Math.sin(angle * Math.PI / 180)}</td>
					<td>${Math.cos(angle * Math.PI / 180)}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>