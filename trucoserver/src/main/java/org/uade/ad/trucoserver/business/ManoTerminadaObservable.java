package org.uade.ad.trucoserver.business;

public interface ManoTerminadaObservable {
	
	public void agregarObserver(ManoTerminadaObserver observer);
	
	public void eliminarObserver(ManoTerminadaObserver observer);
	
}
