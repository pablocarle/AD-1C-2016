package org.uade.ad.trucoserver.entities;

public class Pareja extends Jugador {

	private Jugador jugador1;
	private Jugador jugador2;

	public Pareja(Jugador jugador1, Jugador jugador2) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}
	
}
