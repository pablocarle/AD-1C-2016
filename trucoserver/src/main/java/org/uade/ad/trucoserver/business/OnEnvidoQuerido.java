package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Jugador;

public interface OnEnvidoQuerido {

	void ejecutar(Jugador jugadorGanador, int puntaje);

}
