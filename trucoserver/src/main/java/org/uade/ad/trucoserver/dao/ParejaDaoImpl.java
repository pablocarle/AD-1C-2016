package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Pareja;

public class ParejaDaoImpl extends GenericDaoImpl<Pareja, Integer> implements ParejaDao {

	private static ParejaDao instancia = null;
	
	public static ParejaDao getDAO() {
		if (instancia == null) {
			instancia = new ParejaDaoImpl();
		}
		return instancia;
	}
	
	private ParejaDaoImpl() {
		super();
	}

}
