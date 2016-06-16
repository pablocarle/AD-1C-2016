package org.uade.ad.trucoserver.business;

import org.uade.ad.trucorepo.exceptions.JuegoException;

public interface PartidaTerminadaObserver {

	void finPartida(PartidaTerminadaEvent partida) throws JuegoException;
	
}
