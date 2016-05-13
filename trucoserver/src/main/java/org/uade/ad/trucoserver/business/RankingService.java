package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Transaction;
import org.uade.ad.trucoserver.dao.Juego_LogDao;
import org.uade.ad.trucoserver.dao.Juego_LogDaoImpl;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;

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
	
	private SortedSet<RankingItem> rankingItems;
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
	
	@Override
	public void finPartida(Partida partida) throws Exception {
		Pareja parejaGanadora = partida.getParejaGanadora();
		Pareja parejaPerdedora = partida.getParejaPerdedora();
		List<Jugador> jugadoresGanadores = new ArrayList<>(2);
		jugadoresGanadores.add(parejaGanadora.getJugador1());
		jugadoresGanadores.add(parejaGanadora.getJugador2());
		List<Jugador> jugadoresPerdedores = new ArrayList<>(2);
		jugadoresPerdedores.add(parejaPerdedora.getJugador1());
		jugadoresPerdedores.add(parejaPerdedora.getJugador2());
		int puntosObtenidos = 0;
		RankingItem item = null;
		for (Jugador j : jugadoresGanadores) {
			puntosObtenidos = partida.getPuntosObtenidos(j);
			item = getItem(j.getIdJugador());
			if (item == null) {
				item = new RankingItem(j, puntosObtenidos, 1, 1);
			} else {
				rankingItems.remove(item);
				item.agregarPartidaGanada(puntosObtenidos);
			}
			rankingItems.add(item);
		}
		for (Jugador j : jugadoresPerdedores) {
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
	
	public RankingItem getItem(int idJugador) {
		//Malisimo, pero es el tp, ya fue
		for (RankingItem ri : rankingItems) {
			if (ri.getJugador().getIdJugador() == idJugador) {
				return ri;
			}
		}
		return null;
	}
}
