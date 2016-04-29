<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ingreso a Juego Truco</title>
<script type="text/javascript">
	function validarApodoPass() {
		//var message = document.getElementById("");
		
		var apodo = document.getElementById("apodoField").value;
		var password = document.getElementById("passField").value;
		if (!apodo || !password) {
			alert("Usuario y password son requeridos");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form method="post" action="LoginServlet" onsubmit="return validarApodoPass();" >
		<table border="1" >
			<thead>
				<tr>
					<th colspan="2">Ingreso a Truco</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Apodo</td>
					<td><input type="text" id="apodoField" name="apodo" value="" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" id="passField" name="pass" value="" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Ingresar" /></td>
					<td><input type="reset" value="Reset" /></td>
				</tr>
				<tr>
					<td id="message"></td>
				</tr>
				<tr>
					<td colspan="2">No tengo usuario! <a href="reg.jsp">Nuevo Usuario</a></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>