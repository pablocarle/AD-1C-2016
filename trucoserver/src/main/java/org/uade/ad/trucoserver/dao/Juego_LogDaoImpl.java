package org.uade.ad.trucoserver.dao;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.uade.ad.trucoserver.business.RankingItem;
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
	public SortedSet<RankingItem> getAgrupadoJugadorTotales() {
		Session session = getSession();
		String queryStr = buildAgrupadoJugadorTotalesQuery();
		Query query = session.createQuery(queryStr);
		List<?> data = query.list();
		SortedSet<RankingItem> retSet = new TreeSet<>();
		if (data != null && !data.isEmpty()) {
			Object[] array = null;
			for (Object o : data) {
				if (o instanceof Object[] && ((Object[]) o).length == 4) {
					array = (Object[]) o;
					retSet.add(new RankingItem((Jugador) array[0], ((Number) array[3]).longValue(), ((Number) array[2]).longValue(), ((Number) array[1]).longValue()));
				} else {
					throw new RuntimeException("Se esperaba array de 4 elementos. Se obtuvo: " + o);
				}
			}
		}
		return retSet;
	}
	
	public SortedSet<RankingItem> getAgrupadoJugadorTotalesGrupo(int idGrupo) {
		Session session = getSession();
		String queryStr = buildAgrupadoJugadorTotalesGrupoQuery(idGrupo);
		Query query = session.createQuery(queryStr);
		query.setParameter("idGrupo", idGrupo);
		List<?> data = query.list();
		SortedSet<RankingItem> retSet = new TreeSet<>();
		if (data != null && !data.isEmpty()) {
			Object[] array = null;
			for (Object o : data) {
				if (o instanceof Object[] && ((Object[]) o).length == 4) {
					array = (Object[]) o;
					retSet.add(new RankingItem((Jugador) array[0], ((Number) array[3]).longValue(), ((Number) array[2]).longValue(), ((Number) array[1]).longValue()));
				} else {
					throw new RuntimeException("Se esperaba array de 4 elementos. Se obtuvo: " + o);
				}
			}
		}
		return retSet;
	}

	private String buildAgrupadoJugadorTotalesQuery() {
		StringBuilder str = new StringBuilder();
		str.append("select j.jugador, ");
		str.append("count(*) as cantidad_jugados, ");
		str.append("sum(case when victoria = true then 1 else 0 END) as cantidad_victorias, ");
		str.append("sum(j.puntos) as puntos ");
		str.append("from LogJuego j ");
		str.append("group by j.jugador ");
		str.append("order by puntos asc");
		return str.toString();
	}

	private String buildAgrupadoJugadorTotalesGrupoQuery(int idGrupo) {
		StringBuilder str = new StringBuilder();
		str.append("select j.jugador, ");
		str.append("count(*) as cantidad_jugados, ");
		str.append("sum(case when victoria = true then 1 else 0 END) as cantidad_victorias, ");
		str.append("sum(j.puntos) as puntos ");
		str.append("from LogJuego j, PartidaCerrada pc ");
		str.append("where pc = j.partida ");
		str.append("and pc.grupo.idGrupo = :idGrupo ");
		str.append("group by j.jugador ");
		str.append("order by puntos asc");
		return str.toString();
	}



	
}
