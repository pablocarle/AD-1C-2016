<%@page import="java.util.List"%>
<%@page import="org.uade.ad.trucorepo.dtos.PartidaDTO"%>
<%@page import="org.uade.ad.trucorepo.dtos.JugadorDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<!--  Esta es la vista principal del juego (unica para todas las modalidades) -->
<head>

<style type="text/css">
#contenedor {height: 100%; width: 100%; font-size: 0;}
#formDiv, #juegoLog { display: inline-block; *display: inline; zoom: 1; vertical-align: top; font-size: 12px;}
#formDiv { width: 35%; border: medium;}
#juegoLog {width: 65%; border: medium; background: gray; height: 300px; overflow: scroll;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Truco!</title>
<script type="text/javascript">

ultimoIdxNotificacion = 0;

setInterval(function() {
	//Busca turno
	var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			procesarNotificacion(xhttp.responseXML);
		}
	};
	xhttp.open("GET", "/trucoweb/NotificationServlet", true);
	xhttp.send();
}, 5000);

procesarNotificacion = function(xml) {
	if (xml) {
		
	}
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
<%
JugadorDTO user = (JugadorDTO)session.getAttribute("user");
String title = "Juego de pepe";
try {
int idPartida = Integer.parseInt(request.getParameter("idPartida"));
List<PartidaDTO> partidas = (List<PartidaDTO>)session.getAttribute("");
PartidaDTO partida = null;
for (PartidaDTO p : partidas) {
	if (p.getIdPartida() == idPartida) {
		partida = p;
		break;
	}
}

title = "Juego de " + user.getApodo() + "[" + "Modalidad " + partida.getTipoPartida().getNombre() + "]";
} catch (Exception e) {
	
}
%>
<h1><%=title %></h1>
<div id="contenedor" >
	<div id="formDiv" >
		<form id="juegoForm" method="post" action="PartidaServlet/Jugar" onsubmit="return validarForm();" >
			<table>
				<tr>
					<td colspan="3"><input type="button" id="btnRepartir" value="Repartir Cartas" disabled="disabled" />
				</tr>
				<tr>
					<td>Jugar carta</td>
					<td><select name="idCarta" id="cartaSelect" disabled="disabled"></select></td>
					<td><input type="submit" id="btnSubmitCarta" value="Jugar" disabled="disabled"/></td>
				</tr>
				<tr>
					<td>Cantar Envido</td>
					<td><select name="idEnvite" id="envidoSelect" disabled="disabled"></select></td>
					<td><input type="submit" id="btnSubmitEnvido" value="Cantar" disabled="disabled"/></td>
				</tr>
				<tr>
					<td colspan="1">Cantar Truco</td>
					<td colspan="1"><select name="idEnvite" id="trucoSelect" disabled="disabled"></select></td>
					<td><input type="submit" id="btnSubmitTruco" value="Cantar" disabled="disabled"/></td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="button" value="Ir al Mazo" onclick="irAlMazo();" disabled="disabled" />
						<input type="hidden" name="alMazo" id="alMazoField" value="false" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="juegoLog" >
		<p>Inicio de juego</p>
		<p>Pepe jugo 1 de basto</p>
	</div>
</div>
</body>
</html>