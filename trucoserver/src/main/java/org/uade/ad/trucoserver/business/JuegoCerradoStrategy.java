package org.uade.ad.trucoserver.business;

import java.util.List;

import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;

public class JuegoCerradoStrategy extends JuegoStrategy {

	public static final int PUNTOS_GANADO = 5;
	
	//Guarda por si hace falta por alguna razon algo que solo nos da el grupo y no las parejas
	private Grupo grupo;
	
	public JuegoCerradoStrategy(Grupo grupo, List<Jugador> primerOrdenJuego) throws Exception {
		super(grupo.getParejaNum(0), grupo.getParejaNum(1), primerOrdenJuego);
		this.grupo = grupo;
	}

	@Override
	public int getPuntosObtenidos(Jugador jugador) throws Exception {
		if (pareja1.contieneJugador(jugador)) {
			//TODO Como calcular los puntos
		} else if (pareja2.contieneJugador(jugador)) {
			
		}
		return 0;
	}

}
