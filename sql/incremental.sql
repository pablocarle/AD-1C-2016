-- Incremental (posterior a todos los otros scripts)

update tipo_envites set enviteAnterior = null where enviteAnterior = -1;

alter table envites_pareja_mano modify column aceptado bit null;

alter table bazas modify column idBaza int not null auto_increment;