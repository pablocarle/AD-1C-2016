<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Truco!</title>
<script type="text/javascript">
fechaNotificaciones = null;

setInterval(function() {
	//Buscamos novedades (notificaciones de invitaciones)
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			mostrarNotificaciones(request.responseXML);
		}
	};
	request.open("POST", "/trucoweb/NotificationServlet?fecha=" + fechaNotificaciones, true);
	request.send();
}, 5000);

mostrarNotificaciones = function(xml) {
	var notificationElement = document.getElementById("notification");
	var html = "";
	if (notificationElement) {
		// xml.getElementsByTagName("notificaciones")[0].children[0].textContent
		//TODO Solo me deben interesar las notificaciones de nueva_partida en este punto
		var notificaciones = xml.getElementsByTagName("notificaciones"); //Obtener notificaciones
		if (notificaciones) {
			for (var i = 0; i < notificaciones.length; i++) {
				var children = notificaciones[i].children;
				if (children) {
					for (var j = 0; i < children.length; j++) {
						if (children[j].nodeName == "descripcion") {
							html = "<p>" + children[j].textContent + "</p>";
						} else if (children[j].nodeName == "fechaNotificacion") {
							if (!fechaNotificaciones) {
								fechaNotificaciones = children[j].textContent;
							} else if (children[j].textContent > fechaNotificaciones) {
								fechaNotificaciones = children[j].textContent;
							}
						}
					}
				}
			}
		}
		//Ponemos la url que viene de la notificacion
		notificacionElement.innerHTML = html;
	}
};
</script>
</head>
<body>
	<%
	String mensaje = "No hay notificaciones";
	if (request.getAttribute("mensaje") != null) {
		mensaje = (String)request.getAttribute("mensaje");
	}
	%>
	<!-- TODO Cambiar la ubicacion por algo basico en css -->
	<div>
		<a href="PartidaServlet/NuevaPartida?tipoPartida=abierta">Nueva partida abierta individual</a><br><br>
		<a href="SeleccionPareja">Nueva partida abierta en pareja</a><br><br>
		<a href="partidaCerrada.jsp">Nueva partida Cerrada</a>
	</div>
	
	<div>
		<br><br><br>
		<a href="grupoReg.jsp">Nuevo Grupo</a>
	</div>
	<div>
		<br><br>
		<a href="RankingServlet">Ver Ranking</a>
	</div>
	<br>
	<br>
	<br>
	<div id="notificationArea" >
		<table>
			<tr>
				<td id="notification"><%=mensaje %></td>
			</tr>
		</table>
	</div>
</body>
</html>