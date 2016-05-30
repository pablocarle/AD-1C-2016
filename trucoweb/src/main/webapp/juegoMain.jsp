<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--  Esta es la vista principal del juego (unica para todas las modalidades) -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Truco!</title>
<script type="text/javascript">

ultimoIdxNotificacion = 0;

setInterval(function() {
	//Busca turno
	var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			//Leer el DOC para saver si es el turno del usuario
			procesarNotificacion(xhttp.responseXML);
		}
		//En el pedido a notificacionServlet se ignoran las invitaciones a nuevas partidas
	};
	xhttp.open("GET", "/trucoweb/NotificationServlet", true);
	xhttp.send();
}, 5000);

procesarNotificacion = function(xml) {
	//Ver las notificaciones de la partida (Vendrían notificaciones?. O solo en la pregunta de cada turno?)
	var id = 1+1;
	
};

setInterval(function() {
	//Busca novedades de la partida
}, 1000);

habilitarTurno = function(xml) {
	//Habilitar las funciones que llegan en el response
};

finTurno = function() {
	//Deshabilitar todos los elementos hasta que sea turno de nuevo
};

validarForm = function() {
	return true;
};

irAlMazo = function() {
	var form = document.getElementById("juegoForm");
	var alMazo = document.getElementById("alMazoField");
	
	if (form) {
		//TODO Pedir confirmacion, modificar el hidden si hace falta y disparar el submit
		form.submit();
	} else {
		alert('No form');
	}
};

</script>
</head>
<body>
<h1>Juego de Pepe [Modalidad Abierta]</h1>
<form id="juegoForm" method="post" action="PartidaServlet/Jugar" onsubmit="return validarForm();" >
	<table border="0">
		<tr>
			<td>Jugar carta</td>
			<td><select name="idCarta" id="cartaSelect"></select></td>
			<td><input type="submit" id="btnSubmitCarta" value="Jugar"/></td>
		</tr>
		<tr>
			<td>Cantar Envido</td>
			<td><select name="idEnvite" id="envidoSelect"></select></td>
			<td><input type="submit" id="btnSubmitEnvido" value="Cantar"/></td>
		</tr>
		<tr>
			<td colspan="1">Cantar Truco</td>
			<td colspan="1"><select name="idEnvite" id="trucoSelect"></select></td>
			<td><input type="submit" id="btnSubmitTruco" value="Cantar"/></td>
		</tr>
		<tr>
			<td colspan="3">
				<input type="button" value="Ir al Mazo" onclick="irAlMazo();" />
				<input type="hidden" name="alMazo" id="alMazoField" value="false" />
			</td>
		</tr>
	</table>
</form>

<div id="juegoLog" style="width: 500px; height: 200px; border: medium;" >
<!-- Area de juego (ver novedades) -->
</div>
</body>
</html>