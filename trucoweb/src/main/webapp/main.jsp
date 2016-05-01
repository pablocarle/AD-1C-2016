<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Truco!</title>
<script type="text/javascript">
setInterval(function() {
	//Buscamos novedades (notificaciones de invitaciones)
	XMLHttpRequest request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			mostrarNotificaciones(request);
		}
	};
	request.open("POST", "NotificationServlet", true);
	request.send();
}, 1500);

mostrarNotificaciones = function(xml) {
	//Esperamos obtener xml con notificaciones
};
</script>
</head>
<body>

</body>
</html>