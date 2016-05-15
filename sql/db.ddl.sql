-- use VentaDirecta;
-- drop database trucodb;

create database trucodb;

use trucodb;

-- drop table cartas;

create table cartas(
	idCarta int not null auto_increment,
    palo varchar(10) not null,
    pesoEnvido int not null,
    pesoTruco int not null,
    numero int not null,
    constraint cartas_pk primary key ( idCarta )
);

create unique index cartas_idx_u on cartas ( numero, palo );

create table categorias (
	idCategoria int not null auto_increment,
    ordenCategoria int not null,
    nombre varchar(50) not null,
    puntajeMin int not null,
    partidosMin int not null,
    promedioVictoriasMin int not null,
    constraint categorias_pk primary key ( idCategoria )
);

create unique index categorias_idx_u on categorias ( ordenCategoria );

create table jugadores (
	idJugador int not null auto_increment,
    nombre varchar(50) not null,
    apodo varchar(50) not null,
    email varchar(100) not null,
    password varchar(50) not null,
    idCategoria int not null,
    constraint jugadores_pk primary key ( idJugador ),
    constraint jugadores_cat_fk foreign key ( idCategoria ) references categorias ( idCategoria )
);

-- Revisar logica de unicidad de jugador
create unique index jugadores_idx_u on jugadores ( apodo, email );

create table parejas (
	idPareja int not null auto_increment,
    idJugador1 int not null,
    idJugador2 int not null,
    constraint parejas_pk primary key ( idPareja ),
    constraint parejas_jugador1_fk foreign key ( idJugador1 ) references jugadores ( idJugador ),
    constraint parejas_jugador2_fk foreign key ( idJugador2 ) references jugadores ( idJugador )
);

-- Tabla de ranking. En realidad registra log de partidas ganadas / perdidas
-- Armar vista para obtener los datos reales del ranking ?

-- drop table juego_log;

create table juego_log (
	idLog int not null auto_increment,
    idJugador int not null,
    fecha date not null,
    victoria boolean not null,
    puntos int not null,
    constraint juego_log primary key ( idLog ),
    constraint juego_log_jugador_fk foreign key ( idJugador ) references jugadores ( idJugador )
);

create table grupos (
	idGrupo int not null auto_increment,
    nombre varchar(100) not null,
    idJugadorAdmin int not null,
    idPareja1 int not null,
    idPareja2 int not null,
    constraint grupos_pk primary key ( idGrupo ),
    constraint grupos_pareja1_fk foreign key ( idPareja1 ) references parejas ( idPareja ),
    constraint grupos_pareja2_fk foreign key ( idPareja2 ) references parejas ( idPareja ),
    constraint grupos_jug_adm_fk foreign key ( idJugadorAdmin ) references jugadores ( idJugador )
);

create unique index grupos_idx_u on grupos ( nombre );

create table tipopartidas (
	idTipoPartida int not null,
    nombre varchar(20) not null,
    constraint tipopartidas_pk primary key ( idTipoPartida )
);

create unique index tipopartidas_idx_u on tipopartidas ( nombre );

create table partidas_abiertas (
	idPartida int not null auto_increment,
    idTipoPartida int not null,
    fechaInicio date not null,
    fechaFin date null,
    constraint partidas_abiertas_pk primary key ( idPartida ),
    constraint partidas_a_tipopartida_fk foreign key ( idTipoPartida ) references tipopartidas ( idTipoPartida )
);

create table partidas_cerradas (
	idPartida int not null auto_increment,
    idTipoPartida int not null,
    idGrupo int not null,
    fechaInicio date not null,
    fechaFin date not null,
    constraint partidas_cerradas_pk primary key ( idPartida ),
    constraint partidas_c_tipopartida_fk foreign key ( idTipoPartida ) references tipopartidas ( idTipoPartida ),
    constraint partidas_c_grupos_fk foreign key ( idGrupo ) references grupos ( idGrupo )
);

create table partidas_parejas (
	idPartida int not null,
    idPareja int not null,
    constraint partidas_parejas_fk1 foreign key ( idPartida ) references partidas ( idPartida ),
    constraint partidas_parejas_fk2 foreign key ( idPareja ) references parejas ( idPareja )
);

create table manos (
	idMano int not null auto_increment,
    idPartida int not null,
    fechaInicio date not null,
    fechaFin date null,
    constraint manos_pk primary key ( idMano ),
    constraint manos_partida_fk foreign key ( idPartida ) references partidas ( idPartida )
);

create table envites (
	idEnvite int not null,
    idMano int not null,
    constraint envites_pk primary key ( idEnvite ),
    constraint envites_manos_fk foreign key ( idMano ) references manos ( idMano )
);

create table bazas (
	idBaza int not null,
    idMano int not null,
    rondaBaza int not null,
    fechaInicio date not null,
    fechaFin date null,
    constraint bazas_pk primary key ( idBaza ),
    constraint bazas_mano_fk foreign key ( idMano ) references manos ( idMano )
);

create unique index bazas_idx_u on bazas ( idMano, rondaBaza );

create table bazas_cartas (
	idBazasCartas int not null,
    idBaza int not null,
    idJugador int not null,
    idCarta int not null,
    constraint bazas_cartas_pk primary key ( idBazasCartas ),
    constraint bazas_cartas_baz_fk foreign key ( idBaza ) references bazas ( idBaza ),
    constraint bazas_cartas_jug_fk foreign key ( idJugador ) references jugadores ( idJugador )
);

create unique index bazas_cartas_idx_u on bazas_cartas ( idBaza, idJugador );