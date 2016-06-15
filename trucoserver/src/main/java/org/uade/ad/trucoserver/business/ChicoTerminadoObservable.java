package org.uade.ad.trucoserver.business;

public interface ChicoTerminadoObservable {

	public void agregarObserver(ChicoTerminadoObserver observer);
	
	public void eliminarObserver(ChicoTerminadoObserver observer);
	
}
