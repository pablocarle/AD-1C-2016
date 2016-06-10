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
			xhttp.open("GET", "/trucoweb/NotificationServlet?idPartida=" + idPartidaElement.value + "&fecha=" + fechaNotificaciones , true);
			xhttp.send();
		}
	}
}, 10000);

procesarNotificacion = function(xml) {
	var juego = document.getElementById("juegoLog");
	if (xml) {
		var notificaciones = parseNotificaciones(xml);
		if (notificaciones.length) {
			for (var i = 0; i < notificaciones.length; i++) {
				var n = notificaciones[i];
				if (n.tipoNotificacion != "nueva_partida") {
					var html = "<p>";
					if (!fechaNotificaciones) {
						fechaNotificaciones = n.fechaNotificacionStr;
					} else {
						var d1 = Date.parse(fechaNotificaciones);
						if (notificacion.fechaNotificacion > d1) {
							fechaNotificaciones = notificacion.fechaNotificacionStr;
						}
					}
					html = html + "<b>" + n.fechaNotificacion + ":</b> " + n.descripcion + "</p>";
					juego.innerHTML = html + juego.innerHTML; 
				}
			}
		}
	}
	var id = 1+1;
};

//Parsea el DOM xml y genera un objeto javascript con la informacion "cocinada"
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
}, 10000);

verificarTurno = function(xml) {
	var apodo = document.getElementById("apodoField");
	if (apodo && apodo.value && xml) {
		var partida = parsePartida(xml);
		if (partida.estado == "terminada") {
			//TODO Que hacer si la partida terminó
		} else {
			if (partida.jugadorActual.apodo == apodo.value) {
				habilitarTurno(partida);
			} else {
				finTurno();	
			}
		}
	}
};

habilitarTurno = function(partida) {
	var jActual = partida.jugadorActual;
	//Habilitar las funciones que llegan en el response
	var envidos = jActual.envidos;
	var trucos = jActual.trucos;
	var cartas = jActual.cartasDisponibles;
	//Al mazo tiene que estar disponible porque esta en su turno
	var selectEnvidos = document.getElementById("envidoSelect");
	var selectTrucos = document.getElementById("trucoSelect");
	var selectCartas = document.getElementById("cartaSelect");
	var alMazoBtn = document.getElementById("alMazoBtn");
	var repartirCartasBtn = document.getElementById("btnRepartir");

	if (jActual.repartirCartas) {
		repartirCartasBtn.removeAttribute("disabled");
	}
};

finTurno = function() {
	//Deshabilitar todos los elementos hasta que sea turno de nuevo
	var form = document.getElementById("juegoForm");
	form.disabled = "disabled";
};

parsePartida = function(xml) {
	if (xml) {
		var partida = {};
		var jugadorActual = {
			cartas: [],
			envidos: [],
			trucos: [],
			alMazo: false,
			repartirCartas: false,
			jugarCartas: false,
			envidoEnCurso: false,
			trucoEnCurso: false
		};
		var partidaElement = xml.getElementsByTagName("Partida")[0];
		
		partida.idPartida = partidaElement.getAttribute("idPartida");
		partida.estado = partidaElement.getAttribute("estado");
		
		var turnoActualElement = xml.getElementsByTagName("turnoActual")[0];
		jugadorActual.apodo = turnoActualElement.getAttribute("apodo");
		var envidosTurnoActualElement = xml.getElementsByTagName("turnoActualEnvidos")[0];
		if (envidosTurnoActualElement) {
			for (var i = 0; i < envidosTurnoActualElement.children.length; i++) {
				jugadorActual.envidos.push({
					idEnvite: envidosTurnoActualElement.children[i].getAttribute("idEnvite"),
					nombreEnvite: envidosTurnoActualElement.children[i].getAttribute("nombre")
				});
			}
		}
		var trucosTurnoActualElement = xml.getElementsByTagName("turnoActualTrucos")[0];
		if (trucosTurnoActualElement) {
			for (var i = 0; i < trucosTurnoActualElement.children.length; i++) {
				jugadorActual.trucos.push({
					idEnvite: trucosTurnoActualElement.children[i].getAttribute("idEnvite"),
					nombreEnvite: envidosTurnoActualElement.children[i].getAttribute("nombre")
				});
			}
		}
		var cartasTurnoActualElement = xml.getElementsByTagName("turnoActualCartasDisponibles")[0];
		if (cartasTurnoActualElement) {
			for (var i = 0; i < cartasTurnoActualElement.children.length; i++) {
				jugadorActual.cartas.push({
					idCarta: cartasTurnoActualElement.children[i].getAttribute("idCarta"),
					nombreCarta: cartasTurnoActualElement.children[i].getAttribute("numero") + " de " + cartasTurnoActualElement.children[i].getAttribute("palo")
				});
			}
		}

		//Para repartir cartas, no debe tener cartas disponibles, ni envites
		if (!cartasTurnoActualElement && !trucosTurnoActualElement && !envidosTurnoActualElement) {
			jugadorActual.repartirCartas = true;
		} else {
			if (partida.estado == "truco_curso") {
				jugadorActual.trucoEnCurso = true;
			} else if (partida.estado == "envido_curso") {
				jugadorActual.envidoEnCurso = true;
			} else {
				jugadorActual.alMazo = true;
				jugadorActual.jugarCartas = true;
			}
		}
		
		partida.jugadorActual = jugadorActual;
		return partida;
	}
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

repartirCartas = function() {
	var form = document.getElementById("juegoForm");
	var repartir = document.getElementById("repartirField");

	if (form) {
		
		form.submit();
	} else {
		alert("No form");
	}
};

</script>
</head>
<body>
<%
JugadorDTO user = (JugadorDTO)session.getAttribute("user");
String title = "Juego de Test [Modalidad Partida Abierta Individual]";
try {
	int idPartida = Integer.parseInt(request.getAttribute("idPartida").toString());
	List<PartidaDTO> partidas = (List<PartidaDTO>)session.getAttribute("partidas");
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
					<td colspan="3"><input type="button" id="btnRepartir" value="Repartir Cartas" disabled="disabled" onclick="repartirCartas();" />
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
					</td>
				</tr>
			</table>
			<input type="hidden" name="alMazo" id="alMazoField" value="false" />
			<input type="hidden" name="repartirCartas" id="repartirField" value="false" />
			<input type="hidden" name="idPartida" id="idPartidaField" value=<%=request.getAttribute("idPartida") %> />
			<input type="hidden" name="apodo" id="apodoField" value=<%= session.getAttribute("uid").toString() %> />
		</form>
	</div>
	
	<div id="juegoLog" >
	</div>
</div>
</body>
</html>