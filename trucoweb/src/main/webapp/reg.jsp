<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registro de nuevo usuario</title>
<script type="text/javascript">
	function validaForm() {
		var apodoField = document.getElementById("apodoField").value;
		var passField = document.getElementById("passField").value;
		var emailField = document.getElementById("emailField").value;

		if (!apodoField || !passField || !emailField) {
			//TODO Modificar el alert por algo mejor para avisar al usuario (preferentemente css)
			alert("Faltan campos obligatorios");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form method="post" action="RegisterServlet" onsubmit="return validaForm();">
		<table border="1">
			<thead>
				<tr>
					<th colspan="2">Ingrese informaci&oacute;n de registro</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Apodo</td>
					<td><input type="text" id="apodoField" name="apodo" value="" /></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" id="emailField" name="email" value="" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" id="passField" name="pass" value="" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Registrar" /></td>
				</tr>
				<tr>
					<td colspan="2">Ya tiene usuario? <a href="index.jsp">Ingrese aqu&iacute;</a></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>