package org.uade.ad.trucoserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="envites")
public abstract class Envite {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEnvite;
	protected transient int puntosJuego;
	protected transient int puntosNoAceptado;
	
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
