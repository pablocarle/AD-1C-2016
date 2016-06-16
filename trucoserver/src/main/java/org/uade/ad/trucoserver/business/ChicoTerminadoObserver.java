package org.uade.ad.trucoserver.business;

import org.uade.ad.trucorepo.exceptions.JuegoException;

public interface ChicoTerminadoObserver {

	public void chicoTerminado(ChicoTerminadoEvent event) throws JuegoException;
	
}
