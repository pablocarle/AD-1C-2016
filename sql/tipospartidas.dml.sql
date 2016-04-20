-- Sistema selecciona la pareja y machea contra otra pareja logueada
insert into tipopartidas ( idTipoPartida, nombre )
values (1, 'Abierta Individual');

-- Usuario selecciona su pareja y el sistema machea con otra pareja logueada
insert into tipopartidas ( idTipoPartida, nombre )
values (2, 'Abierta en Pareja');

-- Usuario selecciona el grupo cerrado
insert into tipopartidas ( idTipoPartida, nombre )
values (3, 'Cerrada');