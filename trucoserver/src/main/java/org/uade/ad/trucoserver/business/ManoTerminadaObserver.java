package org.uade.ad.trucoserver.business;

import org.uade.ad.trucorepo.exceptions.JuegoException;

public interface ManoTerminadaObserver {
	
	public void manoTerminada(ManoTerminadaEvent event) throws JuegoException;
	
}
