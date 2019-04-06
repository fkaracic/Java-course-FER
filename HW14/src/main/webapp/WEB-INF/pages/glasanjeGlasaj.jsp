<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="hr.fer.zemris.java.hw14.model.Poll" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
	<%Poll poll = (Poll) request.getAttribute("poll"); %>
	<h1><%= poll.getTitle() %></h1>
	<br><%= poll.getMessage() %>
	<ol>
		<c:forEach var="option" items="${options}">
			<br><li><a href="glasanjeGlasaj?pollID=${poll.id}&ID=${option.ID}"><font size="5">${option.title}</font></a></li>
		</c:forEach>
	</ol>

<br><font size="5">Vrati na ankete: <a href="/voting-app/servleti/index.html">ANKETE</a></font>

</body>

</body>
</html>