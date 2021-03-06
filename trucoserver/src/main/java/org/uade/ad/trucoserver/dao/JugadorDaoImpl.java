package org.uade.ad.trucoserver.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.uade.ad.trucoserver.entities.Jugador;


public class JugadorDaoImpl extends GenericDaoImpl<Jugador, Integer> implements JugadorDao {

	private static JugadorDao instancia = null;
	
	public static JugadorDao getDAO() {
		if (instancia == null)
			instancia = new JugadorDaoImpl();
		return instancia;
	}
	
	private JugadorDaoImpl() {
		super();
	}
	
	@Override
	public Jugador getPorApodo(String apodo) {
		Session session = getSession();
		Query query = session.createQuery("from Jugador where apodo = :apodo");
		query.setParameter("apodo", apodo);
		return getUnico(query);
	}
}
