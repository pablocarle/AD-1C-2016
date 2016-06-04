<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Truco!</title>
<script type="text/javascript">
fechaNotificaciones = null;

checkNotificaciones = function() {
	//Buscamos novedades (notificaciones de invitaciones)
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			mostrarNotificaciones(request.responseXML);
		}
	};
	request.open("POST", "/trucoweb/NotificationServlet?fecha=" + fechaNotificaciones, true);
	request.send();
};

mostrarNotificaciones = function(xml) {
	var notificationElement = document.getElementById("notification");
	var html = "";
	if (notificationElement) {
		// xml.getElementsByTagName("notificaciones")[0].children[0].textContent
		//TODO Solo me deben interesar las notificaciones de nueva_partida en este punto
		var notificaciones = xml.getElementsByTagName("notificaciones"); //Obtener notificaciones
		var update = false;
		if (notificaciones) {
			for (var i = 0; i < notificaciones.length; i++) {
				var children = notificaciones[i].children;
				var tipoNotificacion = null;
				var descripcion = null;
				var url = null;
				
				if (children) {
					for (var j = 0; j < children.length; j++) {
						if (children[j]) {
							if (children[j].nodeName == "descripcion") {
								descripcion = children[j].textContent;
							} else if (children[j].nodeName == "fechaNotificacion") {
								if (!fechaNotificaciones) {
									fechaNotificaciones = children[j].textContent;
									update = true;
								} else if (children[j].textContent) {
									var d1 = Date.parse(fechaNotificaciones);
									var d2 = Date.parse(children[j].textContent);
									if (d2 > d1) {
										fechaNotificaciones = children[j].textContent;
										update = true;
									}
								}
							} else if (children[j].nodeName == "url") {
								url = children[j].textContent;
							} else if (children[j].nodeName == "tipoNotificacion") {
								tipoNotificacion = children[j].textContent;
							}
						}
					}
				}
				if (tipoNotificacion == "nueva_partida" && update) { //Ignoro cualquier otra notificacion
					html = '<p>' + descripcion + '&nbsp;' + '<a href="' + url + '">Aqui</a></p>';
				}
			}
		}
		if (html && html.length > 0) {
			//Ponemos la url que viene de la notificacion
			notificationElement.innerHTML = notificationElement.innerHTML + html;
		}
	}
};
setInterval(checkNotificaciones, 2000);
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
		<a href="/trucoweb/PartidaServlet/NuevaPartida?tipoPartida=abierta">Nueva partida abierta individual</a><br><br>
		<a href="/trucoweb/SeleccionPareja">Nueva partida abierta en pareja</a><br><br>
		<a href="/trucoweb/partidaCerrada.jsp">Nueva partida Cerrada</a>
	</div>
	
	<div>
		<br><br><br>
		<a href="/trucoweb/grupoReg.jsp">Nuevo Grupo</a>
	</div>
	<div>
		<br><br>
		<a href="/trucoweb/RankingServlet">Ver Ranking</a>
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