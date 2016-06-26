<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
</script>
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap/bootstrap.min.css">
</head>
<body>
	<form id="regForm" method="post" action="RegisterServlet"
		onsubmit="return validaForm();">
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
			<input type="text" style="margin-top: 10px;" id="emailField"
				placeholder="email" name="email"><br> <input
				type="password" id="passField" placeholder="password" name="pass"><br>
			<input type="submit" value="Registrar">
			<%
				String regResult = "Falta completar campos"; //Malisimo: Por default falta algun campo
				boolean showMessage = false;
				if (session != null) {
					boolean missingValues = false;
					if (session.getAttribute("missingRegValues") != null) {
						missingValues = (Boolean) session
								.getAttribute("missingRegValues");
						session.removeAttribute("missingRegValues");
						showMessage = true;
					}
					if (session.getAttribute("regResult") != null) {
						regResult = (String) session.getAttribute("regResult");
						session.removeAttribute("regResult");
						showMessage = true;
					}
			%>
			<div id="messageRow" <%if (!showMessage) {%> style="display: none"
				<%}%>>
				<span><%=regResult%></span>
			</div>
			<%
				}
			%>
			<div>
				<span class="label label-default">Ya tengo usuario! <a
					href="index.jsp">INGRESAR</a></span>
			</div>
		</div>
	</form>
</body>
</html>