package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.TipoPartida;

public class TipoPartidaDaoImpl extends GenericDaoImpl<TipoPartida, Integer> implements TipoPartidaDao {

	private static TipoPartidaDao instancia = null;
	
	public static TipoPartidaDao getDao() {
		if (instancia == null) {
			instancia = new TipoPartidaDaoImpl();
		}
		return instancia;
	}
	
	private TipoPartidaDaoImpl() {
		super();
	}
}
