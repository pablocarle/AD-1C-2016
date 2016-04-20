package org.uade.ad.trucoserver.entities;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value="3")
public class PartidaCerrada extends Partida {

	public static final int PUNTOS_GANADO = 5;
	
	//Guarda por si hace falta por alguna razon algo que solo nos da el grupo y no las parejas
	@Transient
	private Grupo grupo;
	
	public PartidaCerrada(Grupo grupo, List<Jugador> primerOrdenJuego) throws Exception {
		super(grupo.getParejaNum(0), grupo.getParejaNum(1), primerOrdenJuego);
		this.grupo = grupo;
	}

	@Override
	public int getPuntosObtenidos(Jugador jugador) throws Exception {
		Pareja pareja1 = parejas.get(0);
		Pareja pareja2 = parejas.get(1);
		if (pareja1.contieneJugador(jugador)) {
			//TODO Como calcular los puntos
		} else if (pareja2.contieneJugador(jugador)) {
			
		}
		return 0;
	}

}
