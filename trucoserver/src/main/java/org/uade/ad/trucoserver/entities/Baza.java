package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Baza {
	
	public enum BazaResultado {
		Parda, NoParda, Incompleta
	}
	
	private List<Carta> cartasJugadas;
	private int numJugadores;
	
	public Baza(int numJugadores) {
		super();
		this.numJugadores = numJugadores;
	}
	
	public void jugarCarta(Carta carta) {
		if (cartasJugadas == null) {
			cartasJugadas = new ArrayList<>();
		}
		if (cartasJugadas.contains(carta)) {
			throw new RuntimeException("Cheater");
		} else {
			cartasJugadas.add(carta);
		}
	}
	
	/**
	 * TODO Probablemente cambiar enum por una clase estatica que mantenga que cartas empardaron o que carta
	 * gano, etc.
	 * 
	 * @return
	 */
	public BazaResultado getResultado() {
		if (cartasJugadas != null && cartasJugadas.size() == numJugadores) {
			Comparator<Carta> valorTrucoComparator = new Carta.ValorTrucoComparador();
			Collections.sort(cartasJugadas, valorTrucoComparator);
			switch (valorTrucoComparator.compare(cartasJugadas.get(0), cartasJugadas.get(1))) {
				case 0:
					return BazaResultado.Parda;
				default:
					return BazaResultado.NoParda;
			}
			
		}
		return BazaResultado.Incompleta;
	}
	
	public List<Carta> getCartasJugadas() {
		return new ArrayList<>(cartasJugadas);
	}
	
	public int getNumJugadores() {
		return numJugadores;
	}
}
