package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Chico;

public class ChicoDaoImpl extends GenericDaoImpl<Chico, Integer> implements ChicoDao {

	private static ChicoDao instancia;
	
	public static ChicoDao getDAO() {
		if (instancia == null) {
			instancia = new ChicoDaoImpl();
		}
		return instancia;
	}
	
	private ChicoDaoImpl() {
		super();
	}


}
