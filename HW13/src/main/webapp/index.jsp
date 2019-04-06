<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>

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
<title>Home</title>
</head>

<body>
	<h1 align="center">HOME</h1>
	<font size="5"><a href="/webapp2/colors.jsp">Background
			color chooser</a> <br> <br> Izračunaj za: <a
		href="/webapp2/trigonometric?a=0&b=90">kut -> [0,90]</a></font>
	<br>
	<br>
	<font size="7">Izračunaj za odabrano:</font>


	<form action="trigonometric" method="GET">
		<font size="5">Početni kut:<br> <input type="number"
			name="a" min="0" max="360" step="1" value="0"><br>
			Završni kut:<br> <input type="number" name="b" min="0" max="360"
			step="1" value="360"><br> <input type="submit"
			value="Tabeliraj"><input type="reset" value="Reset"></font>
	</form>

	<font size="5">
	<br>
	<br>Read:
	<a href="/webapp2/stories/funny.jsp">Funny story</a>
	<br>
	<br>Check:
	<a href="/webapp2/appinfo.jsp">Time elapsed from starting
		application</a><br>

	<br>OS usage chart:	<a href="/webapp2/report.jsp">OS USAGE</a>

	<br>
	<br>Download: Microsoft Excel document ->
	<a href="/webapp2/powers?a=1&b=100&n=3">POWERS(1,100,3)</a>
	<br>

	<br>Glasuj za omiljeni bend:
	<a href="/webapp2/glasanje">GLASANJE</a>
	</font>


</body>
</html>