package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.LogJuego;

public interface Juego_LogDao extends GenericDao<LogJuego, Integer> {

	Jugador getPorApodo(String apodo);

}
