package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.List;

import org.uade.ad.trucoserver.entities.Categoria;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;

public class PartidaAbiertaIndividualMatcher extends PartidaMatcher {

	private Jugador jugadorMatch;
	private List<Jugador> disponibles;

	public PartidaAbiertaIndividualMatcher(Jugador jugador, List<Jugador> disponibles) {
		super();
		this.jugadorMatch = jugador;
		if (disponibles == null) {
			throw new RuntimeException("initialization exception");
		}
		this.disponibles = disponibles;
	}

	@Override
	public Pareja[] match() {
		Categoria jugadorMatchCategoria = jugadorMatch.getCategoria();
		Pareja[] parejas = new Pareja[2];
		Jugador[] jugadores = new Jugador[]{ jugadorMatch, null, null, null };
		List<Jugador> matcheados = getMatch(disponibles, jugadorMatchCategoria);
		if (matcheados.size() > 3) {
			jugadores[1] = matcheados.get(0);
			jugadores[2] = matcheados.get(1);
			jugadores[3] = matcheados.get(2);
		} else {
			//TODO Obtener de categoria superior
			return new Pareja[0];
		}
		parejas[0] = new Pareja(jugadores[0], jugadores[1]);
		parejas[1] = new Pareja(jugadores[2], jugadores[4]);
		return parejas;
	}

	private List<Jugador> getMatch(List<Jugador> disponibles, Categoria categoria) {
		List<Jugador> retList = new ArrayList<>();
		for (Jugador d : disponibles) {
			if (d.getCategoria().equals(categoria)) {
				retList.add(d);
			}
		}
		return retList;
	}
	
	private List<Jugador> getMatchCategoriaSuperior(List<Jugador> disponibles, Categoria categoriaReferencia) {
		List<Jugador> retList = new ArrayList<>();
		for (Jugador d : disponibles) {
			if (d.getCategoria().getOrdenCategoria() == categoriaReferencia.getOrdenCategoria() + 1) {
				//TODO Revisar esto con la consigna
			}
		}
		return retList;
	}
}
