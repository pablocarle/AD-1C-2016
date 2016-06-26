<%@page import="java.util.ArrayList"%>
<%@page import="org.uade.ad.trucorepo.dtos.GrupoDTO"%>
<%@page import="java.util.List"%>
<%@page import="org.uade.ad.trucorepo.dtos.JugadorDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nueva partida Cerrada</title>
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="css/font-awesome/font-awesome.min.css">
</head>
<body>
	<!-- El usuario debe seleccionar el grupo que desea utilizar (se muestran los grupos de los cuales es administrador) -->

	<%
		//Obtener los grupos del usuario
		JugadorDTO dto = (JugadorDTO) session.getAttribute("user");
		List<GrupoDTO> grupos = dto.getGrupos();
		if (grupos == null)
			grupos = new ArrayList<GrupoDTO>();
		System.out.println("Grupos size: " + grupos.size());
	%>
	<div class="page-header" style="color: white">
		<h1>
			NUEVA PARTIDA <small style="border: 1px solid; color: white">CERRADA</small>
		</h1>
	</div>
	<div class="row" style="margin-top: 50px; color: white">
		<div class="col-md-offset-2 col-md-8">
			<form class="form-horizontal" method="get"
				action="/trucoweb/PartidaServlet/NuevaPartida"
				onsubmit="return validarApodoPass();">
				<div class="form-group">
					<label for="idGrupo" class="col-sm-2 control-label">Seleccione
						Grupo</label>
					<div class="col-sm-10">
						<select class="form-control" name="idGrupo" id="grupoSelectField">
							<%
								for (GrupoDTO g : grupos) {
							%>
							<option value=<%=g.getIdGrupo()%>>
								<%=g.getNombre()%>
							</option>
							<%
								}
							%>
						</select> <input type="hidden" name="tipoPartida" value="cerrada" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default" value="Iniciar">Iniciar</button>
						<button onclick="location.href='/trucoweb/main.jsp'" type="button"
							class="btn btn-default">Volver</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>