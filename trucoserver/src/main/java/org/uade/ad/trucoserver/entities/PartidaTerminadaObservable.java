package org.uade.ad.trucoserver.entities;

/**
 * Si alguien desea notificarse cuando una partida termina (como el servicio de ranking)
 * 
 * @author Grupo9
 *
 */
public interface PartidaTerminadaObservable {

	public void agregarObserver(PartidaTerminadaObserver observer);
	
}
