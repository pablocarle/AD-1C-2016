package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;

public class JuegoLibreStrategy extends JuegoStrategy {

	public static final int PUNTOS_GANADO = 10;
	
	public JuegoLibreStrategy(Pareja pareja1, Pareja pareja2) {
		super(pareja1, pareja2);
	}

	@Override
	public int getPuntosObtenidos(Jugador jugador) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
