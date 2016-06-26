package org.uade.ad.trucoserver.dao;

import java.util.SortedSet;

import org.uade.ad.trucoserver.business.RankingItem;
import org.uade.ad.trucoserver.entities.LogJuego;

public interface Juego_LogDao extends GenericDao<LogJuego, Integer> {

	SortedSet<RankingItem> getAgrupadoJugadorTotales();

	SortedSet<RankingItem> getAgrupadoJugadorTotalesGrupo(int idGrupo);

}
