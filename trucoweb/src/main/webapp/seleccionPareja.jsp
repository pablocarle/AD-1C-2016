<%@page import="org.uade.ad.trucorepo.dtos.JugadorDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Seleccione pareja</title>
<script type="text/javascript">
validarForm = function() {
	
};
</script>
<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css">
</head>
<body>

<%
Boolean error = (Boolean)request.getAttribute("error");
String errorMessage = (String)request.getAttribute("errorMessage");
List<JugadorDTO> jugadoresDisponibles = (List<JugadorDTO>)request.getAttribute("jugadoresDisponibles");
%>
<form id="seleccionJugadoresForm" method="post" action="" onsubmit="return validarForm();" >
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
} else if (jugadoresDisponibles != null) {
for (JugadorDTO jugador : jugadoresDisponibles) {
%>
		<tr>
			<td><%=jugador.getApodo()%></td>
			<td><%=jugador.getCategoria().getNombre() %></td>
			<td>
				<input type="checkbox" name=<%= jugador.getApodo() + "Field" %> />
			</td>
		</tr>
<%
}
} else {
%>
		<tr>
			<td colspan="3">No hay jugadores online</td>
		</tr>
<%
}
%>
	<!-- TODO agregar botones para aceptar, seleccionar todo, desmarcar todo, etc -->
		<tr>
			<td colspan="2"><input type="submit" value="Aceptar seleccion" /></td>
			<td colspan="1"><input type="button" value="Reload" /></td>
		</tr>
	</table>
</form>
</body>
</html>