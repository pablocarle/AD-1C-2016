use jbossews;

drop database trucodb;

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
    constraint grupos_pk primary key ( idGrupo ),
    constraint grupos_jug_adm_fk foreign key ( idJugadorAdmin ) references jugadores ( idJugador )
);

create table grupos_detalle (
	idPareja int not null,
    idGrupo int not null,
    eliminado boolean not null,
    fechaCreacion date not null,
    fechaModificacion date not null,
    constraint grupos_detalle_pk primary key ( idPareja, idGrupo ),
    constraint grupos_detalle_par_fk foreign key ( idPareja ) references parejas ( idPareja ),
    constraint grupos_detalle_gru_fk foreign key ( idGrupo ) references grupos ( idGrupo )
);

create unique index grupos_idx_u on grupos ( nombre );

create table tipopartidas (
	idTipoPartida int not null,
    nombre varchar(20) not null,
    puntosVictoria int not null,
    constraint tipopartidas_pk primary key ( idTipoPartida )
);

create unique index tipopartidas_idx_u on tipopartidas ( nombre );

create table partidas (
	idPartida int not null auto_increment,
    idTipoPartida int not null,
    fechaInicio date not null,
    fechaFin date null,
    constraint partidas_pk primary key ( idPartida ),
    constraint partidas_tipopartida_fk foreign key ( idTipoPartida ) references tipopartidas ( idTipoPartida )
);

create table partidas_cerradas (
	idPartida int not null,
    idGrupo int not null,
    constraint partidas_cerradas_pk primary key ( idPartida ),
    constraint partidas_cerradas_p_fk foreign key ( idPartida ) references partidas ( idPartida ),
    constraint partidas_cerradas_g_fk foreign key ( idGrupo ) references grupos ( idGrupo )
);

-- Crear tabla de partidas cerradas?: Para mantener relacion con grupo

create table chicos (
	idChico int not null auto_increment,
	idPartida int not null,
    fechaInicio date not null,
    fechaFin date null,
    constraint chicos_pk primary key ( idChico ),
    constraint chicos_partidas_fk foreign key ( idPartida ) references partidas ( idPartida )
);

create table partidas_parejas (
	idPartida int not null,
    idPareja int not null,
    constraint partidas_parejas_fk1 foreign key ( idPartida ) references partidas ( idPartida ),
    constraint partidas_parejas_fk2 foreign key ( idPareja ) references parejas ( idPareja )
);

create table tipo_envites (
	idTipoEnvite int not null,
	nombreEnvite varchar(30) not null,
	tipoEnvite varchar(1) not null,
    puntaje int not null,
	enviteAnterior int,
    constraint tipoEnvites_pk primary key (idTipoEnvite)
);

create table manos (
	idMano int not null auto_increment,
    idChico int not null,
    fechaInicio date not null,
    fechaFin date null,
    idEnviteTruco int,
    idEnviteEnvido int,
    constraint manos_pk primary key ( idMano ),
    constraint manos_chicos_fk foreign key ( idChico ) references chicos ( idChico ),
    constraint manos_envites_fk1 foreign key ( idEnviteTruco ) references tipo_envites ( idTipoEnvite ),
    constraint manos_envites_fk2 foreign key ( idEnviteEnvido ) references tipo_envites ( idTipoEnvite )
);

create table envites_pareja_mano (
	idEnviteParejaMano int not null auto_increment,
    idTipoEnvite int not null,
    idMano int not null,
    aceptado bit not null,
    puntosObtenidos int not null,
    idPareja int not null,
    constraint envites_pk primary key ( idEnviteParejaMano),
    constraint envites_manos_fk foreign key ( idMano ) references manos ( idMano ),
    constraint envites_manos_tipoEnvite foreign key (idTipoEnvite) references tipo_envites (idTipoEnvite)
);

create unique index envites_parejas_idx on envites_pareja_mano ( idMano, idTipoEnvite );

create table bazas (
	idBaza int not null auto_increment,
    idMano int not null,
    rondaBaza int not null,
    fechaInicio date not null,
    fechaFin date null,
    constraint bazas_pk primary key ( idBaza ),
    constraint bazas_mano_fk foreign key ( idMano ) references manos ( idMano )
);

create unique index bazas_idx_u on bazas ( idMano, rondaBaza );

create table bazas_cartas (
	idBazasCartas int not null auto_increment,
    idBaza int not null,
    idJugador int not null,
    idCarta int not null,
    constraint bazas_cartas_pk primary key ( idBazasCartas ),
    constraint bazas_cartas_baz_fk foreign key ( idBaza ) references bazas ( idBaza ),
    constraint bazas_cartas_jug_fk foreign key ( idJugador ) references jugadores ( idJugador ),
    constraint bazas_cartas_car_fk foreign key ( idCarta ) references cartas ( idCarta )
);

create unique index bazas_cartas_idx_u on bazas_cartas ( idBaza, idJugador );
