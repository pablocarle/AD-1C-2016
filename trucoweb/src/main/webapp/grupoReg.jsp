<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nuevo Grupo</title>
<script type="text/javascript">
	
</script>
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="css/font-awesome/font-awesome.min.css">
</head>
<body>
	<div class="row" style="margin-top: 50px; color: white">
		<div class="col-md-offset-2 col-md-8">
			<form class="form-horizontal" method="post" action="GrupoServlet"
				onsubmit="return validarApodoPass();">
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-2 control-label">Nombre</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="nombreGrupoField"
							name="nombreGrupo" placeholder="Nombre">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">Administrador</label>
					<div class="col-sm-10">
						<input type="text" class="form-control"
							value="<%=session.getAttribute("uid")%>" readonly>
					</div>
				</div>
				<div class="form-group">
					<label for="juagdor1" class="col-sm-2 control-label">Jugador
						1 (Pareja de Administrador)</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="jugador1Field"
							name="jugador1" value="" placeholder="Jugador 1" />
					</div>
				</div>
				<div class="form-group">
					<label for="jugador2" class="col-sm-2 control-label">Jugador
						2 (Pareja de Jugador 3)</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="jugador2Field"
							name="jugador2" value="" placeholder="Jugador 2" />
					</div>
				</div>
				<div class="form-group">
					<label for="jugador3" class="col-sm-2 control-label">Jugador
						3 (Pareja de Jugador 2)</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="jugador3Field"
							name="jugador3" value="" placeholder="Jugador 3" />
					</div>
				</div>
				<%
					String regResult = (String) session.getAttribute("regResult");
					if (regResult != null && regResult.length() > 0) {
						session.removeAttribute("regResult");
				%>
				<div class="alert alert-danger" role="alert"><%=regResult%></div>
				<%
					}
				%>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">Registrar
							Grupo</button>
						<button onclick="location.href='/trucoweb/main.jsp'" type="button"
							class="btn btn-default">Volver</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>