<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
	<h1>Ankete:</h1>

	<ol>
		<c:forEach var="poll" items="${polls}">
			<br><li><a href="/voting-app/servleti/glasanje?pollID=${poll.id}"><font size="5">${poll.title}</font></a></li>
		</c:forEach>
	</ol>

</body>

</body>
</html>