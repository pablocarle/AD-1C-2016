package org.uade.ad.trucoserver.entities;

import java.util.List;

/**
 * Clase base de los modos de juego Libres (individual o pareja)
 * 
 * @author Grupo9
 *
 */
public abstract class PartidaLibre extends Partida {

	public static final int PUNTOS_GANADO = 10;
	
	public PartidaLibre(Pareja pareja1, Pareja pareja2, List<Jugador> primerOrdenJuego) {
		super(pareja1, pareja2, primerOrdenJuego);
	}

}
