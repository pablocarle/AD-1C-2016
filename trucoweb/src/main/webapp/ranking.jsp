<%@page import="org.uade.ad.trucorepo.dtos.RankingItemDTO"%>
<%@page import="org.uade.ad.trucorepo.dtos.JugadorDTO"%>
<%@page import="org.uade.ad.trucorepo.dtos.RankingDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ranking</title>
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="css/font-awesome/font-awesome.min.css">
</head>
<body>
	<div class="page-header" style="color: white">
		<h1>RANKING</h1>
	</div>
	<div class="row" style="margin-top: 50px; color: white">
		<div class="col-md-offset-2 col-md-8">
			<%
				RankingDTO ranking = (RankingDTO) request.getAttribute("ranking");
				if (ranking == null) {
			%>
			<h1>Error: No hay ranking</h1>
			<%
				} else {
			%>
			<div class="table-responsive">
				<table class="table table-inverse">
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
						<%
							JugadorDTO user = (JugadorDTO)session.getAttribute("user");
							System.out.println("apodo usuario:" + user.getApodo());	
							for (RankingItemDTO item : ranking.getItems()) {
						%>
						<tr><%

						System.out.println("apodo jugador:" + item.getJugador().getApodo());
						if(item.getJugador().getApodo()==user.getApodo()) { %>
							<td Style="background-color: green !important;"><%=item.getJugador().getApodo()%></td>
							<td Style="background-color: green !important;"><%=item.getPuntos()%></td>
							<td Style="background-color: green !important;"><%=item.getPartidasGanadas()%></td>
							<td Style="background-color: green !important;"><%=item.getPartidasJugadas()%></td>
							<td Style="background-color: green !important;"><%=item.getPromedioGanadas()%></td>
							<% 
							}else{
							%>
							<td><%=item.getJugador().getApodo()%></td>
							<td><%=item.getPuntos()%></td>
							<td><%=item.getPartidasGanadas()%></td>
							<td><%=item.getPartidasJugadas()%></td>
							<td><%=item.getPromedioGanadas()%></td>
							<%
							}
							%>						
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
				<%
					}
				%>
				<button onclick="location.href='/trucoweb/main.jsp'" type="button"
					class="btn btn-default">Volver</button>
			</div>
		</div>
	</div>
</body>
</html>