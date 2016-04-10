package org.uade.ad.trucoserver.entities;

public class CartaJugada {
	
	private Jugador jugador;
	private Carta carta;
	
	public CartaJugada(Jugador jugador, Carta carta) {
		super();
		this.jugador = jugador;
		this.carta = carta;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public Carta getCarta() {
		return carta;
	}
}
