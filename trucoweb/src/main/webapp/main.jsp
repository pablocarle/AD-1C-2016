
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
		request.open("POST", "/trucoweb/NotificationServlet?fecha="
				+ fechaNotificaciones, true);
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
										var d1 = Date
												.parse(fechaNotificaciones);
										var d2 = Date
												.parse(children[j].textContent);
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
						html = '<p>' + descripcion + '&nbsp;'
								+ '<a href="' + url + '">Aqui</a></p>';
					}
				}
			}
			if (html && html.length > 0) {
				//Ponemos la url que viene de la notificacion
				notificationElement.innerHTML = notificationElement.innerHTML
						+ html;
			}
		}
	};
	setInterval(checkNotificaciones, 2000);
</script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/global.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/buttons.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/font-awesome/font-awesome.min.css">
</head>

<body>
	<div class="row" style="margin-top: 50px;">
		<div class="col-md-offset-2 col-md-8">
			<button
				onclick="location.href='/trucoweb/PartidaServlet/NuevaPartida?tipoPartida=abierta'"
				class="button button--wapasha button--round-s">
				<i class="fa fa-user"
					style="font-size: 24px; vertical-align: middle" aria-hidden="true"></i><span>
					ABIERTA INDIVIDUAL</span>
			</button>
			<button onclick="location.href='/trucoweb/SeleccionPareja'"
				class="button button--wapasha button--round-s">
				<i class="fa fa-users"
					style="font-size: 24px; vertical-align: middle" aria-hidden="true"></i><span>
					ABIERTA GRUPAL</span>
			</button>
			<button onclick="location.href='/trucoweb/partidaCerrada.jsp'"
				class="button button--wapasha button--round-s">
				<i class="fa fa-user-times"
					style="font-size: 24px; vertical-align: middle" aria-hidden="true"></i><span>
					CERRADA</span>
			</button>
		</div>
		<div class="col-md-offset-2 col-md-8">
			<button onclick="location.href='/trucoweb/RankingServlet'"
				class="button button--wapasha button--round-s">
				<i class="fa fa-trophy"
					style="font-size: 24px; vertical-align: middle" aria-hidden="true"></i><span>
					RANKING GENERAL</span>
			</button>
			<button onclick="location.href='/trucoweb/rankingGrupo.jsp'"
				class="button button--wapasha button--round-s">
				<i class="fa fa-trophy"
					style="font-size: 24px; vertical-align: middle" aria-hidden="true"></i><span>
					RANKING GRUPO</span>
			</button>
			<button onclick="location.href='/trucoweb/grupoReg.jsp'"
				class="button button--wapasha button--round-s">
				<i class="fa fa-user-plus"
					style="font-size: 24px; vertical-align: middle" aria-hidden="true"></i><span>
					NUEVO GRUPO</span>
			</button>
		</div>
		<div class="col-md-offset-2 col-md-8">
			<button onclick="location.href='/trucoweb/index.jsp'"
				class="button button--wapasha button--round-s">
				<i class="fa fa-sign-out"
					style="font-size: 24px; vertical-align: middle" aria-hidden="true"></i><span>
					SALIR</span>
			</button>
		</div>
	</div>
	<div class="row">
		<div class="col-md-offset-2 col-md-8" style="margin-top: 35px;">
			<div class="panel panel-default"">
				<div class="panel-heading">Notificaciones</div>
				<div class="panel-body">
					<%
						String mensaje = "No hay notificaciones";
						if (request.getAttribute("mensaje") != null) {
							mensaje = (String) request.getAttribute("mensaje");
						}
					%>
					<div id="notificationArea">
						<table>
							<tr>
								<td id="notification"><%=mensaje%></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>