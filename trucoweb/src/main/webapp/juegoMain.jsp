<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--  Esta es la vista principal del juego (unica para todas las modalidades) -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Truco!</title>
<script type="text/javascript">

setInterval(function() {
	//Busca turno
	var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			//Leer el DOC para saver si es el turno del usuario
			if (isTurno()) {
				habilitarTurno(xhttp);
			}
		}
	};
	xhttp.open("GET", "NotificationServlet?checkTurno", true);
	xhttp.send();
}, 1000);

habilitarTurno = function(partidaDtoXML) {
	//Habilitar las funciones que llegan en el response
};

finTurno = function() {
	//Deshabilitar todos los elementos hasta que sea turno de nuevo
};

</script>
</head>
<body>

</body>
</html>