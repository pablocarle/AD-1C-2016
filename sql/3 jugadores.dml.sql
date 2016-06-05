insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Pablo', 'Pablo', 'pablo.carle@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Leandro', 'Leandro', 'Leandro@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Daniel', 'Daniel', 'Daniel@gmail.com', 1, '123456');


insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Damian', 'Damian', 'Damian@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Nicolas', 'Nicolas', 'Nicolas@gmail.com', 1, '123456');


insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Facundo', 'Facundo', 'Facundo@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Matias', 'Matias', 'matias@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Alejandro', 'Alejandro', 'Alejandro@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Francisco', 'Francisco', 'Francisco@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Ignacio', 'Ignacio', 'Ignacio@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Alexis', 'Alexis', 'Alexis@gmail.com', 1, '123456');

insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Carlos', 'Carlos', 'Carlos@gmail.com', 1, '123456');
/*
select * from jugadores;

select * from categorias;

select * from juego_log;

select idJugador,
count(*) as cantidad_jugados,
sum(case when victoria = 1 then 1 else 0 END CASE;)
from juego_log
group by idJugador;

*/
