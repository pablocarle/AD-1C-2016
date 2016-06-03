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

fechaNotificaciones = null;

setInterval(function() {
	//Busca notificaciones para mostrar de la partida (cualquier evento que se genere en el server)
	var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			procesarNotificacion(xhttp.responseXML);
		}
	};
	if (document) {
		var idPartidaElement = document.getElementById("idPartidaField");
		if (idPartidaElement && idPartidaElement.value) {
			//TODO Verificar
			xhttp.open("GET", "/trucoweb/NotificationServlet?idPartida=" + idPartidaElement.value + "&fecha=" + fechaNotificaciones , true);
			xhttp.send();
		}
	}
}, 2000);

procesarNotificacion = function(xml) {
	var juego = document.getElementById("juegoLog");
	if (xml) {
		var notificaciones = parseNotificaciones(xml);
		if (notificaciones.length) {
			for (var i = 0; i < notificaciones.length; i++) {
				var n = notificaciones[i];
				if (n.tipoNotificacion != "nueva_partida") {
					if (!fechaNotificaciones) {
						fechaNotificaciones = n.fechaNotificacionStr;
					} else {
						var d1 = Date.parse(fechaNotificaciones);
						if (notificacion.fechaNotificacion > d1) {
							fechaNotificaciones = notificacion.fechaNotificacionStr;
						}
					}
					juego.innerHTML = juego.innerHTML + html; //TODO Verificar en que caso no se tiene que hacer el append
					//Muestro la notificacion. Asumo en orden?
					
				}
			}
		}
	}
	var id = 1+1;
};

parseNotificaciones = function(xml) {
	var retArray = [];
	var notificaciones = xml.getElementsByTagName("notificaciones");
	if (notificaciones) {
		for (var i = 0; i < notificaciones.length; i++) {
			var children = notificaciones[i].children;
			var notificacion = {};
			if (children) {
				for (var j = 0; j < children.length; j++) {
					if (children[j]) {
						if (children[j].nodeName == "descripcion") {
							notificacion.descripcion = children[j].textContent;
						} else if (children[j].nodeName == "url") {
							notificacion.url = children[j].textContent;
						} else if (children[j].nodeName == "tipoNotificacion") {
							notificacion.tipoNotificacion = children[j].textContent;
						} else if (children[j].nodeName == "fechaNotificacion") {
							notificacion.fechaNotificacion = Date.parse(children[j].textContent);
							notificacion.fechaNotificacionStr = children[j].textContent;
						}
					}
				}
			}
			if (notificacion.tipoNotificacion) {
				retArray.push(notificacion);	
			}
		}
	}
	return retArray;
};

setInterval(function() {
	//Busca si es el turno
	var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			verificarTurno(xhttp.responseXML);
		}
	};
	if (document) {
		var idPartidaElement = document.getElementById("idPartidaField");
		if (idPartidaElement && idPartidaElement.value) {
			xhttp.open("GET", "/trucoweb/PartidaServlet/Partida?idPartida=" + idPartidaElement.value, true);
			xhttp.send();
		}
	}
}, 1000);

verificarTurno = function(xml) {
	var apodo = document.getElementById("apodoField");
	if (apodo && apodo.value) {
		var partida = parsePartida(xml);
		if (partida.turnoActual == apodo.value) {
			habilitarTurno(partida);
		} else {
			finTurno();	
		}
	}
};

habilitarTurno = function(partida) {
	//Habilitar las funciones que llegan en el response
	var envidos = partida.jugadorActual.envidosDisponibles;
	var trucos = partida.jugadorActual.trucosDisponibles;
	var cartas = partida.jugadorActual.cartasDisponibles;
	//Al mazo tiene que estar disponible porque esta en su turno
	var selectEnvidos = null;
	var selectTrucos = null;
	var selectCartas = null;
	var alMazoBtn = document.getElementById("alMazoBtn");
	//TODO Continuar
	alMazoBtn.disabled = null;
};

finTurno = function() {
	//Deshabilitar todos los elementos hasta que sea turno de nuevo
	var form = document.getElementById("juegoForm");
	form.disabled = "disabled";
};

parsePartida = function(xml) {
	var partida = {};
	var jugadorActual = {};
	
	//TODO Parsear PartidaDTO en objeto javascript
	//Lo que se ve en esta pagina es la información que hace falta enviar desde el servidor (lo que tiene que estar si o si en el DTO de partida)
	return partida;
};

validarForm = function() {
	var form = document.getElementById("juegoForm");
	if (form) {
		//TODO Verificar que solo se carga una accion
		return true;
	} else {
		return false;
	}
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
String title = "Juego de Test [Modalidad Partida Abierta Individual]";
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
	e.printStackTrace();	
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
						<input type="button" id="alMazoBtn" value="Ir al Mazo" onclick="irAlMazo();" disabled="disabled" />
						<input type="hidden" name="alMazo" id="alMazoField" value="false" />
					</td>
				</tr>
			</table>
			<input type="hidden" name="idPartida" id="idPartidaField" value=<%=request.getParameter("idPartida") %>/>
			<input type="hidden" name="apodo" id="apodoField" value=<%= session.getAttribute("uid").toString() %> />
		</form>
	</div>
	
	<div id="juegoLog" >
		<p>Inicio de juego</p>
		<p>Pepe jugo 1 de basto</p>
	</div>
</div>
</body>
</html>