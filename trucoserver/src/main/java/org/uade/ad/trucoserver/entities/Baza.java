package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Baza {
	
	public enum Resultado {
		Parda, NoParda, Incompleta
	}
	
	private Map<Jugador, Carta> cartasJugadas; //TODO Cambiar, no puede ser mapa porque pierde registro de a quien le toca jugar
	private int numJugadores;
	private List<Jugador> ordenJuego;
	
	public Baza(int numJugadores, List<Jugador> ordenJuego) {
		super();
		assert(numJugadores == ordenJuego.size());
		this.numJugadores = numJugadores;
		this.ordenJuego = ordenJuego;
	}
	
	void jugarCarta(Jugador jugador, Carta carta) {
		if (cartasJugadas == null) {
			cartasJugadas = new HashMap<>();
		}
		if (cartasJugadas.containsKey(jugador)) {
			throw new RuntimeException("El jugador " + jugador + " ya jugo en la baza");
		} else {
			cartasJugadas.put(jugador, carta);
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
			List<Carta> cartas = new ArrayList<>(cartasJugadas.values());
			Collections.sort(cartas, valorTrucoComparator);
			switch (valorTrucoComparator.compare(cartas.get(0), cartas.get(1))) {
				case 0:
					return new BazaResultado(Resultado.Parda, getJugadoresPorCartaJugada(cartas.get(0)));
				case 1:
					return new BazaResultado(Resultado.NoParda, getJugadoresPorCartaJugada(cartas.get(0))); //TODO Verificar que contenga la carta ganadora
				default:
					return new BazaResultado(Resultado.NoParda, getJugadoresPorCartaJugada(cartas.get(1)));
			}
			
		}
		return new BazaResultado(Resultado.Incompleta, null);
	}
	
	private List<Jugador> getJugadoresPorCartaJugada(Carta carta) {
		List<Jugador> retList = new ArrayList<>();
		for (Map.Entry<Jugador, Carta> cartaJugada : cartasJugadas.entrySet()) {
			if (cartaJugada.getValue().equals(carta)) {
				retList.add(cartaJugada.getKey());
			}
		}
		return retList;
	}
	
	public Map<Jugador, Carta> getCartasJugadas() {
		return new HashMap<>(cartasJugadas);
	}
	
	public int getNumJugadores() {
		return numJugadores;
	}
	
	public boolean esCompleta() {
		return cartasJugadas != null && numJugadores == cartasJugadas.size();
	}
	
	public static class BazaResultado {
		
		private Resultado resultado;
		private List<Jugador> jugadores;
		
		private BazaResultado(Resultado resultado, List<Jugador> jugadores) {
			super();
			this.resultado = resultado;
			this.jugadores = jugadores;
		}
		
		public Resultado getResultado() {
			return resultado;
		}
		
		public List<Jugador> getJugadores() {
			return new ArrayList<>(jugadores);
		}
		
		public boolean isResultadoDeterminado() {
			return !resultado.equals(Resultado.Incompleta);
		}
		
		public boolean isParda() {
			return resultado.equals(Resultado.Parda);
		}
	}
}
