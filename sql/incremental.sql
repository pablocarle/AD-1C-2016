-- Incremental (posterior a todos los otros scripts)

update tipo_envites set enviteAnterior = null where enviteAnterior = -1;

alter table envites_pareja_mano modify column aceptado bit null;

alter table bazas modify column idBaza int not null auto_increment;

insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (43,'Re-Truco','T',-1,29);
insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (44,'Re-Truco_Querido','T',3,43);
insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (45,'Re-Truco_NoQuerido','T',2,43);

insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (46,'Vale Cuatro','T',-1,30);
insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (47,'Vale Cuatro_Querido','T',4,46);
insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (48,'Vale Cuatro_NoQuerido','T',3,46);

insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (49,'Vale Cuatro','T',-1,44);
insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (50,'Vale Cuatro_Querido','T',4,49);
insert into tipo_envites ( idtipoEnvite , nombreEnvite, tipoEnvite, puntaje, enviteAnterior) 
values (51,'Vale Cuatro_NoQuerido','T',3,49);