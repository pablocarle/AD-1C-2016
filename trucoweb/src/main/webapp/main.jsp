<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Truco!</title>
<script type="text/javascript">
setInterval(function() {
	//Buscamos novedades (notificaciones de invitaciones)
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			mostrarNotificaciones(request.responseXML);
		}
	};
	request.open("POST", "/trucoweb/NotificationServlet", true);
	request.send();
}, 5000);

mostrarNotificaciones = function(xml) {
	var notificationElement = document.getElementById("notification");
	if (notificationElement) {
		var nl = xml.getElementsByTagName("tagName"); //Obtener notificaciones
		//Ponemos la url que viene de la notificacion
		notificacionElement.innerHTML = "";
	}
};
</script>
</head>
<body>
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
	<br>
	<br>
	<br>
	<div id="notificationArea" >
		<table>
			<tr>
				<td id="notification">No hay notificaciones</td>
			</tr>
		</table>
	</div>
</body>
</html>