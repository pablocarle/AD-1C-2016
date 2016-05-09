<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registro de nuevo usuario</title>
<script type="text/javascript">
	validaForm = function() {
		var apodoField = document.getElementById("apodoField").value;
		var passField = document.getElementById("passField").value;
		var emailField = document.getElementById("emailField").value;
		var messageRow = document.getElementById("messageRow");
		messageRow.style.display = "none";
		
		if (!apodoField || !passField || !emailField) {
			//alert("Faltan campos obligatorios");
			messageRow.style.display = null;
			return false;
		}
		return true;
	};

// 	submitForm = function() {
// 		if (validaForm()) {
// 			var form = document.getElementById("regForm");
// 			var apodoField = document.getElementById("apodoField").value;
// 			var passField = document.getElementById("passField").value;
// 			var emailField = document.getElementById("emailField").value;
// 			var xhttp = new XMLHttpRequest();
// 			xhttp.onreadystatechange = function() {
// 				if (xhttp.readyState == 4 && xhttp.status == 200) {
// 					//Dar OK
// 				} else if (xhttp.readyState == 4) {
// 					//Error HTTP
// 				}
// 			};
// 			xhttp.open(form.method, form.action, true);
// 			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
// 			xhttp.send("apodo=" + encodeURIComponent(apodoField) + "&pass=" + encodeURIComponent(passField) + "&email=" + encodeURIComponent(emailField));
// 		}
// 	};
</script>
</head>
<body>
	<form id="regForm" method="post" action="RegisterServlet" onsubmit="return validaForm();">
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
				<%
					String regResult = "Falta completar campos"; //Malisimo: Por default falta algun campo
					boolean showMessage = false;
					if (session != null) {
						boolean missingValues = false;
						if (session.getAttribute("missingRegValues") != null) {
							missingValues = (Boolean)session.getAttribute("missingRegValues");
							session.removeAttribute("missingRegValues");
							showMessage = true;
						} 
						if (session.getAttribute("regResult") != null) {
							regResult = (String)session.getAttribute("regResult");
							session.removeAttribute("regResult");
							showMessage = true;
						}
				%>
				<tr id="messageRow" <% if (!showMessage) { %> style="display: none" <% } %>>
					<td colspan="2"><%= regResult%></td>
				</tr>
				<%
					}				
				%>
				<tr>
					<td colspan="2">Ya tiene usuario? <a href="index.jsp">Ingrese aqu&iacute;</a></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>