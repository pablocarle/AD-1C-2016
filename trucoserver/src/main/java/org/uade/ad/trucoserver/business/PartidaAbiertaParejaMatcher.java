package org.uade.ad.trucoserver.business;

import java.util.List;

import org.uade.ad.trucoserver.entities.Pareja;

public class PartidaAbiertaParejaMatcher extends PartidaMatcher {

	private Pareja pareja;
	private List<Pareja> parejasDisponibles;

	public PartidaAbiertaParejaMatcher(Pareja pareja, List<Pareja> parejasDisponibles) {
		super();
		this.pareja = pareja;
		this.parejasDisponibles = parejasDisponibles;
	}

	@Override
	public Pareja[] match() {
		// TODO Auto-generated method stub
		return null;
	}
}
