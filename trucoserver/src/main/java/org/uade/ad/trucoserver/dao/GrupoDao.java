package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Grupo;

public interface GrupoDao extends GenericDao<Grupo, Integer> {

	Grupo getPorNombreGrupo(String nombreGrupo);

}
