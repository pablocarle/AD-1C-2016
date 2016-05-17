<%@page import="org.uade.ad.trucorepo.dtos.GrupoDTO"%>
<%@page import="java.util.List"%>
<%@page import="org.uade.ad.trucorepo.dtos.JugadorDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nueva partida Cerrada</title>
</head>
<body>
<!-- El usuario debe seleccionar el grupo que desea utilizar (se muestran los grupos de los cuales es administrador) -->

<%
//Obtener los grupos del usuario
JugadorDTO dto = (JugadorDTO)session.getAttribute("user");
List<GrupoDTO> grupos = dto.getGrupos();
%>
<form method="post" action="JuegoServlet" onsubmit="return validarApodoPass();" >
		<table border="1" >
			<thead>
				<tr>
					<th colspan="2">Nueva partida cerrada</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Seleccione Grupo</td>
					<td>
						<select>
							<%
								for (GrupoDTO g : grupos) {
							%>
								<option value="" />
							<%
								}
							%>
						</select>
						<input type="text" id="apodoField" name="apodo" value="" />
						<input type="hidden" name="tipoPartida" value="cerrada" />
					</td>
				</tr>
				<tr>
					<td><input type="submit" value="Ingresar" /></td>
					<td><input type="reset" value="Reset" /></td>
				</tr>
				<%
					if (session != null && session.getAttribute("loginResult") != null && !((Boolean)session.getAttribute("loginResult"))) {
						session.removeAttribute("loginResult");
				%>
					<tr id="messageRow">
						<td colspan="2" id="message">Login incorrecto</td>
					</tr>
				<%
					}
				%>
				<tr>
					<td colspan="2">No tengo usuario! <a href="reg.jsp">Nuevo Usuario</a></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>