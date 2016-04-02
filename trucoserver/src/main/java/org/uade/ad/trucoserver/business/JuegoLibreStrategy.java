package org.uade.ad.trucoserver.business;

import java.util.List;

import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;

/**
 * Clase base de los modos de juego Libres (individual o pareja)
 * 
 * @author Grupo9
 *
 */
public abstract class JuegoLibreStrategy extends JuegoStrategy {

	public static final int PUNTOS_GANADO = 10;
	
	public JuegoLibreStrategy(Pareja pareja1, Pareja pareja2, List<Jugador> primerOrdenJuego) {
		super(pareja1, pareja2, primerOrdenJuego);
	}

}
