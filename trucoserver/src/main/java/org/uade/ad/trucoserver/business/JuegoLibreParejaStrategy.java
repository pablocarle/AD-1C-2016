package org.uade.ad.trucoserver.business;

import java.util.List;

import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;

public class JuegoLibreParejaStrategy extends JuegoLibreStrategy {

	public JuegoLibreParejaStrategy(Pareja pareja1, Pareja pareja2, List<Jugador> primerOrdenJuego) {
		super(pareja1, pareja2, primerOrdenJuego);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPuntosObtenidos(Jugador jugador) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


}
