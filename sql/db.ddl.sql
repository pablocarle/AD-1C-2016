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
    idCategoria int not null,
    constraint jugadores_pk primary key ( idJugador ),
    constraint jugadores_cat_fk foreign key ( idCategoria ) references categorias ( idCategoria )
);

-- Revisar logica de unicidad de jugador
create unique index jugadores_idx_u on jugadores ( apodo, email );

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
    constraint grupos_pk primary key ( idGrupo )
);

create unique index grupos_idx_u on grupos ( nombre );

-- drop table grupos_detail;

create table grupos_detail (
	idGrupoDetail int not null auto_increment,
	idGrupo int not null,
    idJugador int not null,
    parejaNum int not null,
    admin boolean not null,
    constraint grupos_detail_pk primary key ( idGrupoDetail ),
    constraint grupos_detail_grupo_fk foreign key ( idGrupo ) references grupos ( idGrupo ),
    constraint grupos_detail_j_fk foreign key ( idJugador ) references jugadores ( idJugador )
);