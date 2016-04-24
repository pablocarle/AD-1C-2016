package org.uade.ad.trucoserver.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.LogJuego;

public class Juego_LogDaoImpl extends GenericDaoImpl<LogJuego, Integer> implements Juego_LogDao {

	private static Juego_LogDao instancia = null;
	
	public static Juego_LogDao getDAO() {
		if (instancia == null)
			instancia = new Juego_LogDaoImpl();
		return instancia;
	}
	
	private Juego_LogDaoImpl() {
		super();
	}
	
	@Override
	public Jugador getPorApodo(String apodo) {
//		Session session = getSession();
//		Query query = session.createQuery("from Jugador where apodo = :apodo");
//		query.setParameter("apodo", apodo);
//		return getUnico(query);
		return null;
	}

}
