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