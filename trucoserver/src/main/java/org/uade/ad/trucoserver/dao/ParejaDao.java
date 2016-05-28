package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Pareja;

public interface ParejaDao extends GenericDao<Pareja, Integer> {
	
	/**
	 * Obtener si hay una pareja existente que este formada por jugador1 y jugador2
	 * sin importar el orden
	 * 
	 * @param idJugador1
	 * @param idJugador2
	 * @return
	 */
	public Pareja getParejaNoGrupo(int idJugador1, int idJugador2);
	
}
