<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
    /* Define custom CSS by the user here */
    body{
        background-color: <% if(request.getSession().getAttribute("pickedBgCol") == null){  %>
        				  <%= "#FFFFFF" %>
        				  <% }else{ %>
        				  <%= request.getSession().getAttribute("pickedBgCol") %>
        				  <% } %>
    }
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Funny story</title>
</head>
<body>
<%
	String[] colors = {"#ff0000", "#ffff00", "#00ff00", "#0080ff", "#ff00bf"};
	String color = colors[(int)(Math.random() * colors.length)];
%>
	<h1><font color=<%= color %>>Wrong email address</font></h1>
	
	<font size="5" color=<%= color %>>A couple going on vacation but his wife was on a business trip so he went to the destination first and his wife would meet him the next day.

When he reached his hotel, he decided to send his wife a quick email.

Unfortunately, when typing her address, he mistyped a letter and his note was directed instead to an elderly preacher’s wife whose husband had passed away only the day before.

When the grieving widow checked her email, she took one look at the monitor, let out a piercing scream, and fell to the floor in a dead faint.

At the sound, her family rushed into the room and saw this note on the screen:

Dearest Wife,
Just got checked in. Everything prepared for your arrival tomorrow.

P.S. Sure is hot down here.</font>

<p><font size="5" color=<%= color %>>Return home: <a href="/webapp2/">HOME</a></font>

	</body>
</html>