package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Grupo;

public class GrupoDaoImpl extends GenericDaoImpl<Grupo, Integer> implements GrupoDao {

	private static GrupoDao instancia = null;
	
	public static GrupoDao getDAO() {
		if (instancia == null) {
			instancia = new GrupoDaoImpl();
		}
		return instancia;
	}
	
	private GrupoDaoImpl() {
		super();
	}

}
