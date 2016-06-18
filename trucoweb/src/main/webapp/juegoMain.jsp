<%@page import="java.util.List"%>
<%@page import="org.uade.ad.trucorepo.dtos.PartidaDTO"%>
<%@page import="org.uade.ad.trucorepo.dtos.JugadorDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<!--  Esta es la vista principal del jue//Parsea el DOM xml y genera un objeto javascript con la informacion "cocinada"
go (unica para todas las modalidades) -->
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

checkNotificaciones = function() {
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
};

notifCheckId = setInterval(checkNotificaciones, 2000);

procesarNotificacion = function(xml) {
	var juego = document.getElementById("juegoLog");
	if (xml) {
		var notificaciones = parseNotificaciones(xml);
		if (notificaciones.length) {
			notificaciones.sort(function(a, b) {
				return b.fechaNotificacion - a.fechaNotificacion;
			});
			for (var i = 0; i < notificaciones.length; i++) {
				var n = notificaciones[i];
				if (n.tipoNotificacion != "nueva_partida") {
					var html = "<p>";
					if (!fechaNotificaciones) {
						fechaNotificaciones = n.fechaNotificacionStr;
					} else {
						var d1 = Date.parse(fechaNotificaciones);
						if (n.fechaNotificacion > d1) {
							fechaNotificaciones = n.fechaNotificacionStr;
						} else if (n.fechaNotificacion < d1) {
							continue;
						}
					}
					html = html + "<b>" + n.fechaNotificacionStr + ":</b> " + n.descripcion + "</p>";
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

asyncCheckTurno = function() {
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
};

turnoCheckId = setInterval(asyncCheckTurno, 2000);
restoreTimerTurno = false;

verificarTurno = function(xml) {
	var apodo = document.getElementById("apodoField");
	if (apodo && apodo.value && xml) {
		var partida = parsePartida(xml);
		if (partida.estado == "terminada") {
			finTurno();
		} else {
			if (partida.jugadorActual.apodo == apodo.value) {
				habilitarTurno(partida);
			} else {
				actualizarCartasSelect(partida);
				finTurno();	
			}
		}
	}
};

actualizarCartasSelect = function(partida) {
	var selectCartas = document.getElementById("cartaSelect");
	removeOptions(selectCartas);
	if (partida && partida.jugadorActual) {
		for (var i = 0; i < partida.jugadorActual.cartas.length; i++) {
			var option = document.createElement("option");
			option.value = partida.jugadorActual.cartas[i].idCarta;
			option.text = partida.jugadorActual.cartas[i].nombreCarta;
			selectCartas.appendChild(option);
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

	var jugarCartaBtn = document.getElementById("btnSubmitCarta");
	var cantarEnvidoBtn = document.getElementById("btnSubmitEnvido");
	var cantarTrucoBtn = document.getElementById("btnSubmitTruco");
	var alMazoBtn = document.getElementById("alMazoBtn");
	var repartirCartasBtn = document.getElementById("btnRepartir");

	removeOptions(selectEnvidos);
	removeOptions(selectTrucos);
	removeOptions(selectCartas);

	if (partida.jugadorActual.cartas) {
		for (var i = 0; i < partida.jugadorActual.cartas.length; i++) {
			var option = document.createElement("option");
			option.value = partida.jugadorActual.cartas[i].idCarta;
			option.text = partida.jugadorActual.cartas[i].nombreCarta;
			selectCartas.appendChild(option);
		}
	}
	if (partida.jugadorActual.trucos) {
		for (var i = 0; i < partida.jugadorActual.trucos.length; i++) {
			var option = document.createElement("option");
			option.value = partida.jugadorActual.trucos[i].idEnvite;
			option.text = partida.jugadorActual.trucos[i].nombreEnvite;
		}
	}
	if (partida.jugadorActual.envidos) {
		for (var i = 0; i < partida.jugadorActual.envidos.length; i++) {
			var option = document.createElement("option");
			option.value = partida.jugadorActual.envidos[i].idEnvite;
			option.text = partida.jugadorActual.envidos[i].nombreEnvite;
		}
	}
	
	jugarCartaBtn.setAttribute("disabled", "disabled");
	cantarEnvidoBtn.setAttribute("disabled", "disabled");
	cantarTrucoBtn.setAttribute("disabled", "disabled");
	alMazoBtn.setAttribute("disabled", "disabled");
	repartirCartasBtn.setAttribute("disabled", "disabled");

	selectEnvidos.setAttribute("disabled", "disabled");
	selectTrucos.setAttribute("disabled", "disabled");
	if (jActual.repartirCartas) {
		repartirCartasBtn.removeAttribute("disabled");
	} else if (partida.envidoEnCurso === "true") {
		cantarEnvidoBtn.removeAttribute("disabled");
		selectEnvidos.removeAttribute("disabled");
	} else if (partida.trucoEnCurso === "true") {
		cantarTrucoBtn.removeAttribute("disabled");
		selectTrucos.removeAttribute("disabled");
	} else {
		alMazoBtn.removeAttribute("disabled");
		if (partida.jugadorActual.cartas.length > 0) {
			jugarCartaBtn.removeAttribute("disabled");
			selectCartas.removeAttribute("disabled");
		}
		if (partida.jugadorActual.envidos.length > 0) {
			cantarEnvidoBtn.removeAttribute("disabled");
			selectEnvidos.removeAttribute("disabled");
		}
		if (partida.jugadorActual.trucos.length > 0) {
			cantarTrucoBtn.removeAttribute("disabled");
			selectTrucos.removeAttribute("disabled");
		}
	}
	clearInterval(turnoCheckId);
	restoreTimerTurno = true;
};

function removeOptions(selectbox)
{
	if (selectbox && selectbox.options) {
	    var i;
	    for(i=selectbox.options.length-1;i>=0;i--)
	    {
	        selectbox.remove(i);
	    }
	}
}

finTurno = function() {
	var selectEnvidos = document.getElementById("envidoSelect");
	var selectTrucos = document.getElementById("trucoSelect");
	var selectCartas = document.getElementById("cartaSelect");

	var jugarCartaBtn = document.getElementById("btnSubmitCarta");
	var cantarEnvidoBtn = document.getElementById("btnSubmitEnvido");
	var cantarTrucoBtn = document.getElementById("btnSubmitTruco");
	var alMazoBtn = document.getElementById("alMazoBtn");
	var repartirCartasBtn = document.getElementById("btnRepartir");

	jugarCartaBtn.setAttribute("disabled", "disabled");
	cantarEnvidoBtn.setAttribute("disabled", "disabled");
	cantarTrucoBtn.setAttribute("disabled", "disabled");
	alMazoBtn.setAttribute("disabled", "disabled");
	repartirCartasBtn.setAttribute("disabled", "disabled");

	selectEnvidos.setAttribute("disabled", "disabled");
	selectTrucos.setAttribute("disabled", "disabled");
	if (restoreTimerTurno) {
		turnoCheckId = setInterval(asyncCheckTurno, 2000);
		restoreTimerTurno = false;
	}
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
			jugarCartas: false
		};
		var partidaElement = xml.getElementsByTagName("Partida")[0];
		
		partida.idPartida = partidaElement.getAttribute("idPartida");
		partida.estado = partidaElement.getAttribute("estado");
		partida.envidoEnCurso = partidaElement.getAttribute("envidoEnCurso");
		partida.trucoEnCurso = partidaElement.getAttribute("trucoEnCurso");
		
		var turnoActualElement = xml.getElementsByTagName("turnoActual")[0];
		jugadorActual.apodo = turnoActualElement.getAttribute("apodo");
		var envidosTurnoActualElements = xml.getElementsByTagName("turnoActualEnvidos");
		if (envidosTurnoActualElements) {
			for (var i = 0; i < envidosTurnoActualElements.length; i++) {
				jugadorActual.envidos.push({
					idEnvite: envidosTurnoActualElements[i].getAttribute("idEnvite"),
					nombreEnvite: envidosTurnoActualElements[i].getAttribute("nombre")
				});
			}
		}
		var trucosTurnoActualElements = xml.getElementsByTagName("turnoActualTrucos");
		if (trucosTurnoActualElements) {
			for (var i = 0; i < trucosTurnoActualElements.length; i++) {
				jugadorActual.trucos.push({
					idEnvite: trucosTurnoActualElements[i].getAttribute("idEnvite"),
					nombreEnvite: envidosTurnoActualElements[i].getAttribute("nombre")
				});
			}
		}
		var cartasTurnoActualElements = xml.getElementsByTagName("turnoActualCartasDisponibles");
		var cartasJugadorElements = xml.getElementsByTagName("cartasDisponibles");
		if (cartasTurnoActualElements && cartasTurnoActualElements.length > 0) {
			for (var i = 0; i < cartasTurnoActualElements.length; i++) {
				jugadorActual.cartas.push({
					idCarta: cartasTurnoActualElements[i].getAttribute("idCarta"),
					nombreCarta: cartasTurnoActualElements[i].getAttribute("numero") + " de " + cartasTurnoActualElements[i].getAttribute("palo")
				});
			}
		} else if (cartasJugadorElements && cartasJugadorElements.length > 0) {
			var children = cartasJugadorElements[i].children;
			if (children) {
				for (var j = 0; j < children.length; j++) {
					if (children[j].tagName == "cartas") {
						jugadorActual.cartas.push({
							idCarta: children[j].getAttribute("idCarta"),
							nombreCarta: children[j].getAttribute("numero") + " de " + children[j].getAttribute("palo")
						});
					}
				}
			}
		}

		//Para repartir cartas, no debe tener cartas disponibles, ni envites
		if ((!cartasTurnoActualElements || cartasTurnoActualElements.length == 0) 
				&& (!trucosTurnoActualElements || trucosTurnoActualElements.length == 0) 
				&& (!envidosTurnoActualElements || envidosTurnoActualElements.length == 0)) {
			jugadorActual.repartirCartas = true;
		} else {
			if (partida.trucoEnCurso === "false" && partida.envidoEnCurso === "false") {
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

//TODO Quitar todos los submit. Todos deben ser onclicks ajax, sino tengo que hacer forward a la vista y es un bardo que se refresque la pantalla

irAlMazo = function() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status != 200) {
			handleError(xhttp.responseXML);
		}
	};
	if (document) {
		var idPartidaElement = document.getElementById("idPartidaField");
		if (idPartidaElement && idPartidaElement.value) {
			xhttp.open("POST", "/trucoweb/PartidaServlet/Jugar?idPartida=" + idPartidaElement.value + "&alMazo=true", true);
			xhttp.send();
			finTurno();
		}
	}
};

repartircartas = function() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status != 200) {
			handleError(xhttp.responseXML);
		}
	};
	if (document) {
		var idPartidaElement = document.getElementById("idPartidaField");
		if (idPartidaElement && idPartidaElement.value) {
			xhttp.open("POST", "/trucoweb/PartidaServlet/Jugar?idPartida=" + idPartidaElement.value + "&repartirCartas=true", true);
			xhttp.send();
			finTurno();
		}
	}
};

jugarcarta = function() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status != 200) {
			handleError(xhttp.responseXML);
		}
	};
	if (document) {
		var cartaSelect = document.getElementById("cartaSelect");
		if (cartaSelect && cartaSelect.value) {
			var idPartidaElement = document.getElementById("idPartidaField");
			if (idPartidaElement && idPartidaElement.value) {
				xhttp.open("POST", "/trucoweb/PartidaServlet/Jugar?idPartida=" + idPartidaElement.value + "&idCarta=" + cartaSelect.value, true);
				xhttp.send();
				finTurno();
			}
		} else if (cartaSelect && !cartaSelect.value) {
			alert("Debe seleccionar una carta");
		}
	}
};

cantarenvido = function() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status != 200) {
			handleError(xhttp.responseXML);
		}
	};
	if (document) {
		var enviteSelect = document.getElementById("envidoSelect");
		if (enviteSelect && enviteSelect.value) {
			var idPartidaElement = document.getElementById("idPartidaField");
			if (idPartidaElement && idPartidaElement.value) {
				xhttp.open("POST", "/trucoweb/PartidaServlet/Jugar?idPartida=" + idPartidaElement.value + "&idEnvite=" + enviteSelect.value, true);
				xhttp.send();
				finTurno();
			}
		} else if (cartaSelect && !cartaSelect.value) {
			alert("Debe seleccionar un envido");
		}
	}
};

cantartruco = function() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status != 200) {
			handleError(xhttp.responseXML);
		}
	};
	if (document) {
		var enviteSelect = document.getElementById("trucoSelect");
		if (enviteSelect && enviteSelect.value) {
			var idPartidaElement = document.getElementById("idPartidaField");
			if (idPartidaElement && idPartidaElement.value) {
				xhttp.open("POST", "/trucoweb/PartidaServlet/Jugar?idPartida=" + idPartidaElement.value + "&idEnvite=" + enviteSelect.value, true);
				xhttp.send();
				finTurno();
			}
		} else if (cartaSelect && !cartaSelect.value) {
			alert("Debe seleccionar un truco");
		}
	}
};

</script>
</head>
<body>
<%
JugadorDTO user = (JugadorDTO)session.getAttribute("user");
String title = "Juego de Test [Modalidad Partida Abierta Individual]";
try {
	int idPartida = Integer.parseInt(request.getParameter("idPartida").toString());
	List<PartidaDTO> partidas = (List<PartidaDTO>)session.getAttribute("partidas");
	if (partidas == null || partidas.isEmpty()) {
		throw new Exception("No hay partidas en sesion de usuario");
	}
	PartidaDTO partida = null;
	for (PartidaDTO p : partidas) {
		if (p.getIdPartida() == idPartida) {
			partida = p;
			break;
		}
	}
	
	if (partida == null)
		throw new Exception("No se encontro la partida solicitada del usuario");
	title = "Juego de " + user.getApodo() + " [" + "Modalidad " + partida.getTipoPartida().getNombre() + "]";
} catch (Exception e) {
	e.printStackTrace();
	throw e;
}
%>
<h1><%=title %></h1>
<div id="contenedor" >
	<div id="formDiv" >
		<form id="juegoForm" method="post" action="PartidaServlet/Jugar" onsubmit="return validarForm();" >
			<table>
				<tr>
					<td colspan="3"><input type="button" id="btnRepartir" value="Repartir Cartas" disabled="disabled" onclick="repartircartas();" />
				</tr>
				<tr>
					<td>Jugar carta</td>
					<td><select name="idCarta" id="cartaSelect" ></select></td>
					<td><input type="button" id="btnSubmitCarta" value="Jugar" disabled="disabled" onclick="jugarcarta();" /></td>
				</tr>
				<tr>
					<td>Cantar Envido</td>
					<td><select name="idEnvite" id="envidoSelect" disabled="disabled" ></select></td>
					<td><input type="button" id="btnSubmitEnvido" value="Cantar" disabled="disabled" onclick="cantarenvido();" /></td>
				</tr>
				<tr>
					<td colspan="1">Cantar Truco</td>
					<td colspan="1"><select name="idEnvite" id="trucoSelect" disabled="disabled" ></select></td>
					<td><input type="button" id="btnSubmitTruco" value="Cantar" disabled="disabled" onclick="cantartruco();" /></td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="button" id="alMazoBtn" value="Ir al Mazo" disabled="disabled" onclick="irAlMazo();" />
					</td>
				</tr>
			</table>
			<input type="hidden" name="idPartida" id="idPartidaField" value=<%=request.getParameter("idPartida") %> />
			<input type="hidden" name="apodo" id="apodoField" value=<%= session.getAttribute("uid").toString() %> />
		</form>
	</div>
	
	<div id="juegoLog" >
	</div>
</div>
</body>
</html>