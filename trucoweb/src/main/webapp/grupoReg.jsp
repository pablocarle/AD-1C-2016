<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nuevo Grupo</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form method="post" action="GrupoServlet" onsubmit="return validarApodoPass();" >
		<table border="1" >
			<thead>
				<tr>
					<th colspan="2">Ingrese los datos del Grupo</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Nombre</td>
					<td><input type="text" id="nombreGrupoField" name="nombreGrupo" value="" /></td>
				</tr>
				<tr>
					<td>Administrador</td>
					<td><%= session.getAttribute("uid")%></td>
				</tr>
				<tr>
					<td>Jugador 1 (Pareja de Administrador):</td>
					<td><input type="text" id="jugador1Field" name="jugador1" value="" /></td>
				</tr>
				<tr>
					<td>Jugador 2 (Pareja de Jugador 3):</td>
					<td><input type="text" id="jugador2Field" name="jugador2" value="" /></td>
				</tr>
				<tr>
					<td>Jugador 3 (Pareja de Jugador 2):</td>
					<td><input type="text" id="jugador3Field" name="jugador3" value="" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Registrar Grupo" /></td>
					<td><input type="reset" value="Reset" /></td>
				</tr>
				<%
					String regResult = (String) session.getAttribute("regResult");
					if (regResult != null && regResult.length() > 0) {
						session.removeAttribute("regResult");						
				%>
					<tr id="messageRow">
						<td id="message" colspan="2"><%=regResult %></td>
					</tr>
				<%
					}
				%>
				<tr>
					<td colspan="2">Volver al <a href="main.jsp">menu principal</a></td>
				</tr>
			</tbody>
		</table>
	</form>	
</body>
</html>