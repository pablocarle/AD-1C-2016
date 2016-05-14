package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

public class ParejaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private JugadorDTO jugador1;
	private JugadorDTO jugador2;
	
	public ParejaDTO() {
		super();
	}

	public JugadorDTO getJugador1() {
		return jugador1;
	}

	public void setJugador1(JugadorDTO jugador1) {
		this.jugador1 = jugador1;
	}

	public JugadorDTO getJugador2() {
		return jugador2;
	}

	public void setJugador2(JugadorDTO jugador2) {
		this.jugador2 = jugador2;
	}
}
