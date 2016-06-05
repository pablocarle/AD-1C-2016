-- Sistema selecciona la pareja y machea contra otra pareja logueada
insert into tipopartidas ( idTipoPartida, nombre, puntosVictoria )
values (1, 'Abierta Individual', 10);

-- Usuario selecciona su pareja y el sistema machea con otra pareja logueada
insert into tipopartidas ( idTipoPartida, nombre, puntosVictoria )
values (2, 'Abierta en Pareja', 10);

-- Usuario selecciona el grupo cerrado
insert into tipopartidas ( idTipoPartida, nombre, puntosVictoria )
values (3, 'Cerrada', 5);