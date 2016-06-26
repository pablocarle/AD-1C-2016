<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ocurrio un error</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css">
</head>
<body>

<%
Boolean error = (Boolean)request.getAttribute("error");
String errorMessage = (String)request.getAttribute("errorMessage");

if (error != null && error) {
%>
<h1><%=errorMessage %></h1>
<%
}
%>
<br>
<br>
<p>
<a href="main.jsp">Volver a pagina principal</a>
</p>
</body>
</html>