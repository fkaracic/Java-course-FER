<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.concurrent.TimeUnit"%>
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
<title>Timer</title>
</head>
<body>

	<h1>Time elapsed from starting the application</h1>

	<%
		long currentTime = System.currentTimeMillis();
		long startingTime = (Long) request.getServletContext().getAttribute("start");

		long result = currentTime - startingTime;

		long days = TimeUnit.MILLISECONDS.toDays(result);
		result -= TimeUnit.DAYS.toMillis(days);

		long hours = TimeUnit.MILLISECONDS.toHours(result);
		result -= TimeUnit.HOURS.toMillis(hours);

		long minutes = TimeUnit.MILLISECONDS.toMinutes(result);
		result -= TimeUnit.MINUTES.toMillis(minutes);

		long seconds = TimeUnit.MILLISECONDS.toSeconds(result);
		result -= TimeUnit.SECONDS.toMillis(seconds);

		long millis = TimeUnit.MILLISECONDS.toMillis(result);

		String difference = String.format("%d DAYS - %d HOURS - %d MINUTES - %d SECONDS - %d MILLISECONDS", days,
				hours, minutes, seconds, millis);
	%>

	<p>
		<%=difference%>
	</p>

	<p>
		<font size="5">Return home: <a href="/webapp2/">HOME</a></font>
</body>
</html>