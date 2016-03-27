package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Pareja;

/**
 * Implementaciones de esta clase son las formas de juego disponibles.
 * Determinada por eleccion de usuario
 * 
 * @author Grupo9
 *
 */
public abstract class JuegoStrategy {

	//Las parejas que participan del juego
	protected Pareja pareja1;
	protected Pareja pareja2;
	
	
	public JuegoStrategy(Pareja pareja1, Pareja pareja2) {
		super();
		this.pareja1 = pareja1;
		this.pareja2 = pareja2;
	}
	
	
	
}
