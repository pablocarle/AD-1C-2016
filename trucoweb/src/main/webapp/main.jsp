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
	XMLHttpRequest request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			mostrarNotificaciones(request);
		}
	};
	request.open("POST", "NotificationServlet", true);
	request.send();
}, 1500);

mostrarNotificaciones = function(xml) {
	var notificationElement = document.getElementById("notification");
	//Esperamos obtener xml con notificaciones
};
</script>
</head>
<body>
	<!-- TODO Cambiar la ubicacion por algo basico en css -->
	<div>
		<a href="">Nueva partida abierta individual</a><br><br>
		<a href="">Nueva partida abierta en pareja</a><br><br>
		<a href="">Nueva partida Cerrada</a>
	</div>
	
	<div>
		<br><br><br>
		<a href="">Nuevo Grupo</a>
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
<!-- Agregar links a acciones -->
<!-- Nueva partida abierta individual -->
<!-- Nueva partida abierta en pareja -->
<!-- Nueva partida cerrada -->
<!-- Nuevo grupo -->
<!-- Area de notificaciones -->
</body>
</html>