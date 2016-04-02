package org.uade.ad.trucoserver.entities;

public abstract class Envite {

	protected int puntosJuego;
	protected int puntosNoAceptado;
	
	public Envite(int puntosJuego, int puntosNoAceptado) {
		super();
		this.puntosJuego = puntosJuego;
		this.puntosNoAceptado = puntosNoAceptado;
	}

	public int getPuntosNoAceptado() {
		return puntosNoAceptado;
	}

	public void setPuntosNoAceptado(int puntosNoAceptado) {
		this.puntosNoAceptado = puntosNoAceptado;
	}

	public int getPuntosJuego() {
		return puntosJuego;
	}

	public void setPuntosJuego(int puntosJuego) {
		this.puntosJuego = puntosJuego;
	}
}
