package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Partida;

public class PartidaTerminadaEvent {

	private Partida partida;
	private Jugador[] ganadores;
	private Jugador[] perdedores;
	
	public PartidaTerminadaEvent(Partida partida, Jugador[] ganadores, Jugador[] perdedores) {
		super();
		this.partida = partida;
		this.ganadores = ganadores;
		this.perdedores = perdedores;
	}

	public Partida getPartida() {
		return partida;
	}

	public Jugador[] getGanadores() {
		return ganadores;
	}

	public Jugador[] getPerdedores() {
		return perdedores;
	}
}
