package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Mano;

public class ManoDaoImpl extends GenericDaoImpl<Mano, Integer> implements ManoDao {
	
	private static ManoDao instancia = null;
	
	public static ManoDao getDAO() {
		if (instancia == null)
			instancia = new ManoDaoImpl();
		return instancia;
	}
	
	private ManoDaoImpl() {
		super();
	}
	
}
