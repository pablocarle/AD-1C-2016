package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Carta;

public class CartaDaoImpl extends GenericDaoImpl<Carta, Integer> implements CartaDao {

	private static CartaDao instancia = null;
	
	public static CartaDao getDAO() {
		if (instancia == null)
			instancia = new CartaDaoImpl();
		return instancia;
	}
	
	private CartaDaoImpl() {
		super();
	}
}
