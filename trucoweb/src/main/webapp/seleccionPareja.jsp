<%@page import="org.uade.ad.trucorepo.dtos.JugadorDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Seleccione pareja</title>
</head>
<body>

<%
Boolean error = (Boolean)request.getAttribute("error");
String errorMessage = (String)request.getAttribute("errorMessage");
List<JugadorDTO> jugadoresDisponibles = (List<JugadorDTO>)request.getAttribute("jugadoresDisponibles");
%>
<form id="seleccionJugadoresForm" method="post" action="" onsubmit="" >
	<table border="1" name="tblJugadores" >
		<thead>
			<tr>
				<th>Apodo</th>
				<th>Categoria</th>
				<th>Seleccion</th>
			</tr>
		</thead>
<%
if (error != null && error) {	
%>
		<tr>
			<td colspan="*" >
				Error: <%=errorMessage %>
			</td>
		</tr>
<%
} else {
%>

<%
for (JugadorDTO jugador : jugadoresDisponibles) {
%>
		<tr>
			<td><%=jugador.getApodo()%></td>
			<td><%=jugador.getCategoria().getNombre() %></td>
			<td>
				<input type="checkbox" name="seleccion" />
			</td>
		</tr>
<%
}
%>
	<!-- TODO agregar botones para aceptar, seleccionar todo, desmarcar todo, etc -->
	</table>
</form>
<%
}
%>

<!-- Debe seleccionar entre los jugadores que esten online -->
<!-- Armar tabla con JSP -->
</body>
</html>