package org.uade.ad.trucoserver.dao;

import org.hibernate.Query;
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

	@Override
	public Pareja getParejaNoGrupo(int idJugador1, int idJugador2) {
		Query q = getSession().createQuery("from Pareja p where "
				+ "								((p.jugador1.idJugador = :idJugador1 and p.jugador2.idJugador = :idJugador2)"
				+ "								or (p.jugador1.idJugador = :idJugador2 and p.jugador2.idJugador = :idJugador1))"
				+ "								and p.idPareja not in (select distinct pk.pareja.idPareja from GrupoDetalle)");
		q.setParameter("idJugador1", idJugador1);
		q.setParameter("idJugador2", idJugador2);
		return getUnico(q);
	}
}
