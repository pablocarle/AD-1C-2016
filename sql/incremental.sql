-- Incremental

update tipo_envites set enviteAnterior = null where enviteAnterior = -1;

alter table envites_pareja_mano modify column aceptado bit null;
