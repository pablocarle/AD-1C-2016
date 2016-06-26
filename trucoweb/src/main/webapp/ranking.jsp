<%@page import="org.uade.ad.trucorepo.dtos.RankingItemDTO"%>
<%@page import="org.uade.ad.trucorepo.dtos.RankingDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ranking</title>
</head>
<body>
<%
RankingDTO ranking = (RankingDTO)request.getAttribute("ranking");
if (ranking == null) {
%>
<h1>Error: No hay ranking</h1>
<%} else { %>
<!-- Aca va el ranking, tabla comun y corriente -->
<table>
	<thead>
		<tr>
			<th>Apodo</th>
			<th>Puntos</th>
			<th>Partidas Ganadas</th>
			<th>Partidas Jugadas</th>
			<th>Promedio</th>
		</tr>
	</thead>
	<tbody>
		<% for (RankingItemDTO item : ranking.getItems()) { %>
			<tr>
				<td><%= item.getJugador().getApodo() %></td>
				<td><%= item.getPuntos() %></td>
				<td><%= item.getPartidasGanadas() %></td>
				<td><%= item.getPartidasJugadas() %>
				<td><%= item.getPromedioGanadas() %></td>
			</tr>
		<%} %>
	</tbody>
</table>

<%} %>
</body>
</html>