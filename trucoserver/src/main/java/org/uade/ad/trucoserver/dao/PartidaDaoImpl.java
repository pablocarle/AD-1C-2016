package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Partida;

public class PartidaDaoImpl extends GenericDaoImpl<Partida, Integer> implements PartidaDao {

	private static PartidaDao instancia;
	
	public static PartidaDao getDAO() {
		if (instancia == null) {
			instancia = new PartidaDaoImpl();
		}
		return instancia;
	}
	
	private PartidaDaoImpl() {
		super();
	}


}
