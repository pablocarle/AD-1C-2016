insert into jugadores (nombre, apodo, email, idCategoria, password) 
values ('Pablo', 'Pablo', 'pablo.carle@gmail.com', 1, '123456');

select * from jugadores;

select * from categorias;

select * from juego_log;

select idJugador,
count(*) as cantidad_jugados,
sum(case when victoria = 1 then 1 else 0 END CASE;)
from juego_log
group by idJugador;
