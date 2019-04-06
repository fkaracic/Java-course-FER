<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Glasanje za omiljeni bend:</h1>
	<br><font size="5">Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</font>

	<ol>
		<c:forEach var="band" items="${names}">
			<br><li><a href="glasanje-glasaj?id=${band.key}"><font size="5">${band.value}</font></a></li>
		</c:forEach>
	</ol>

<br><font size="5">Vrati na naslovnicu: <a href="/webapp2/">NASLOVNICA</a></font>

</body>

</body>
</html>