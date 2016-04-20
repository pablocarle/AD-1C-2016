package org.uade.ad.trucoserver.entities;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="2")
public class PartidaLibrePareja extends PartidaLibre {

	public PartidaLibrePareja(Pareja pareja1, Pareja pareja2, List<Jugador> primerOrdenJuego) {
		super(pareja1, pareja2, primerOrdenJuego);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPuntosObtenidos(Jugador jugador) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


}
