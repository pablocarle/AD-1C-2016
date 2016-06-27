package org.uade.ad.trucoserver.business;

import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Transaction;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.dao.Juego_LogDao;
import org.uade.ad.trucoserver.dao.Juego_LogDaoImpl;
import org.uade.ad.trucoserver.entities.Jugador;

public class RankingService implements PartidaTerminadaObserver {

	private static RankingService instance = null;
	
	public static RankingService getService() {
		if (instance == null) {
			instance = new RankingService();
		}
		return instance;
	}
	


	private Juego_LogDao logDao;
	{
		logDao = Juego_LogDaoImpl.getDAO();
	}
	
	public SortedSet<RankingItem> rankingItems;
	{
		rankingItems = cargaRanking();
	}
	
	private RankingService() {
		super();
	}

	private SortedSet<RankingItem> cargaRanking() {
		try {
			Transaction tr = logDao.getSession().beginTransaction();
			SortedSet<RankingItem> items = new TreeSet<>(logDao.getAgrupadoJugadorTotales());
			tr.commit();
			return items;
		} catch (Exception e) {
			throw new RuntimeException("No se pudo cargar ranking", e);
		}
	}
	
	public SortedSet<RankingItem> cargaRankingGrupo(int idGrupo) {
		try {
			Transaction tr = logDao.getSession().beginTransaction();
			SortedSet<RankingItem> items = new TreeSet<>(logDao.getAgrupadoJugadorTotalesGrupo(idGrupo));
			tr.commit();
			return items;
		} catch (Exception e) {
			throw new RuntimeException("No se pudo cargar ranking", e);
		}
	}
	public RankingItem getItem(int idJugador) {
		//Malisimo, pero es el tp, ya fue
		for (RankingItem ri : rankingItems) {
			if (ri.getJugador().getIdJugador() == idJugador) {
				return ri;
			}
		}
		return null;
	}
	
	@Override
	public void finPartida(PartidaTerminadaEvent partida) throws JuegoException {
		RankingItem item = null;
		int puntosObtenidos = 0;
		for (Jugador j : partida.getGanadores()) {
			puntosObtenidos = partida.getPartida().getPuntosObtenidos(j);
			item = getItem(j.getIdJugador());
			if (item == null) {
				item = new RankingItem(j, puntosObtenidos, 1, 1);
			} else {
				rankingItems.remove(item);
				item.agregarPartidaGanada(puntosObtenidos);
			}
			rankingItems.add(item);
		}
		for (Jugador j : partida.getPerdedores()) {
			item = getItem(j.getIdJugador());
			if (item == null) {
				item = new RankingItem(j, 0, 0, 1);
			} else {
				rankingItems.remove(item);
				item.agregarPartidaPerdida();
			}
			rankingItems.add(item);
		}
	}
}
