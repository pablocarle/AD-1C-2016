<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ingreso a Juego Truco</title>
<script type="text/javascript">
	function validarApodoPass() {
		//var message = document.getElementById("");
		var messageRow = document.getElementById("messageRow");
		var apodo = document.getElementById("apodoField").value;
		var password = document.getElementById("passField").value;

		if (messageRow) {
			messageRow.style.display = 'none';
		}
		if (!apodo || !password) {
			//TODO Modificar el alert por algo mejor para el usuario. Preferentemente css.
			alert("Usuario y password son requeridos");
			return false;
		}
		return true;
	}
</script>
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap/bootstrap.min.css">
</head>
<body>
	<!-- Estamos usando el submit del form como viene. Se podria reemplazar por llamada ajax (ECMA o jquery) -->
	<form method="post" action="LoginServlet"
		onsubmit="return validarApodoPass();">
		<div class="body"></div>
		<div class="grad"></div>
		<div class="header">
			<div>
				Truco<span>UADE</span>
			</div>
		</div>
		<br>
		<div class="login">
			<input type="text" id="apodoField" placeholder="apodo" name="apodo"><br>
			<input type="password" id="passField" placeholder="password"
				name="pass"><br> <input type="submit" value="Entrar">
			<%
				if (session != null && session.getAttribute("loginResult") != null
						&& !((Boolean) session.getAttribute("loginResult"))) {
					session.removeAttribute("loginResult");
			%>
			<div>
				<span class="label label-danger"><strong>Error!</strong> Login incorrecto.</span>
			</div>
			<%
				}
			%>
			<div>
				<span class="label label-default">No tengo usuario! <a
					href="reg.jsp">REGISTRARME</a></span>
			</div>
		</div>
	</form>
</body>
</html>