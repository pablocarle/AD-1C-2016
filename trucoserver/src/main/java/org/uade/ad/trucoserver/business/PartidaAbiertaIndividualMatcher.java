package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.List;

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
		Pareja[] parejas = new Pareja[2];
		Jugador[] jugadores = new Jugador[]{ jugadorMatch, null, null, null };
		List<Jugador> matcheados = getMatch(disponibles, jugadorMatch);
		if (matcheados.size() >= 3) {
			jugadores[1] = matcheados.get(0);
			jugadores[2] = matcheados.get(1);
			jugadores[3] = matcheados.get(2);
		} else {
			int i = 0;
			for (i = 1; i < jugadores.length && (i-1) < matcheados.size(); i++) {
				jugadores[i] = matcheados.get(i-1);
			}
			matcheados = getMatchCategoriaSuperior(disponibles, jugadorMatch);
			for (; i < jugadores.length && (i-1) < matcheados.size(); i++) {
				jugadores[i] = matcheados.get(i-1);
			}
			if (i < jugadores.length) {
				return new Pareja[0];
			}
		}
		parejas[0] = new Pareja(jugadores[0], jugadores[1]);
		parejas[1] = new Pareja(jugadores[2], jugadores[3]);
		return parejas;
	}

	private List<Jugador> getMatch(List<Jugador> disponibles, Jugador jugadorReferencia) {
		List<Jugador> retList = new ArrayList<>();
		for (Jugador d : disponibles) {
			if (!d.equals(jugadorReferencia) && d.getCategoria().equals(jugadorReferencia.getCategoria())) {
				retList.add(d);
			}
		}
		return retList;
	}
	
	private List<Jugador> getMatchCategoriaSuperior(List<Jugador> disponibles, Jugador jugadorReferencia) {
		List<Jugador> retList = new ArrayList<>();
		for (Jugador d : disponibles) {
			if (!d.equals(jugadorReferencia) && d.getCategoria().getOrdenCategoria() == jugadorReferencia.getCategoria().getOrdenCategoria() + 1) {
				retList.add(d);
			}
		}
		return retList;
	}
}
