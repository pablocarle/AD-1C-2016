package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Jugador;

public interface JugadorDao extends GenericDao<Jugador, Integer> {

	Jugador getPorApodo(String apodo);

}
