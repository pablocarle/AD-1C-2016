package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Partida;

public interface PartidaTerminadaObserver {

	void finPartida(Partida partida) throws Exception;
	
}
