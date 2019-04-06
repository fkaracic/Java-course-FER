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
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${results}">
				<tr>
					<td>${result.key}</td>
					<td>${result.value}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="/webapp2/glasanje-grafika" width="500"
		height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="/webapp2/glasanje-xls">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="song" items="${songs}">
			<li><a href="${song.value}" target="_blank">${song.key}</a></li>
		</c:forEach>
	</ul>
	
	<font size="5">Vrati na naslovnicu: <a href="/webapp2/">NASLOVNICA</a></font>
	
</body>
</html>