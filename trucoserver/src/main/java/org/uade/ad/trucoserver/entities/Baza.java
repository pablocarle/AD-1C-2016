package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Baza {
	
	public enum Resultado {
		Parda, NoParda, Incompleta
	}
	
	private List<CartaJugada> cartasJugadas;
	private List<Jugador> ordenJuego;
	private int numJugadores;
	
	public Baza(int numJugadores, List<Jugador> ordenJuego) {
		super();
		assert(numJugadores == ordenJuego.size());
		this.numJugadores = numJugadores;
		this.ordenJuego = ordenJuego;
	}
	
	void jugarCarta(Jugador jugador, Carta carta) {
		if (cartasJugadas == null) {
			cartasJugadas = new ArrayList<>();
		}
		if (ordenJuego.get(cartasJugadas.size()).equals(jugador)) {
			cartasJugadas.add(new CartaJugada(jugador, carta));
		} else {
			throw new RuntimeException("No es el turno del jugador");
		}
	}
	
	public BazaResultado getResultado() {
		if (cartasJugadas != null && cartasJugadas.size() == numJugadores) {
			Comparator<Carta> valorTrucoComparator = new Carta.ValorTrucoComparador();
			List<Carta> cartas = getCartasJugadas();
			Collections.sort(cartas, valorTrucoComparator);
			switch (valorTrucoComparator.compare(cartas.get(0), cartas.get(1))) {
				case 0: {
					//Gano el que sea jugador mano
					Jugador jugador0 = getJugadorPorCartaJugada(cartas.get(0));
					Jugador jugador1 = getJugadorPorCartaJugada(cartas.get(1));
					int indexJ0 = ordenJuego.indexOf(jugador0);
					int indexJ1 = ordenJuego.indexOf(jugador1);
					if (indexJ0 < indexJ1) {
						return new BazaResultado(Resultado.Parda, jugador0);
					} else {
						return new BazaResultado(Resultado.Parda, jugador1);
					}
				}
				case 1:
					return new BazaResultado(Resultado.NoParda, getJugadorPorCartaJugada(cartas.get(0)));
				default:
					return new BazaResultado(Resultado.NoParda, getJugadorPorCartaJugada(cartas.get(1)));
			}
			
		}
		return new BazaResultado(Resultado.Incompleta, null);
	}
	
	private List<Carta> getCartasJugadas() {
		List<Carta> cartas = new ArrayList<>();
		for (CartaJugada cartaJugada : cartasJugadas) {
			cartas.add(cartaJugada.getCarta());
		}
		return cartas;
	}

	private Jugador getJugadorPorCartaJugada(Carta carta) {
		Jugador jugador = null;
		for (CartaJugada cartaJugada : cartasJugadas) {
			if (cartaJugada.getCarta().equals(carta)) {
				jugador = cartaJugada.getJugador();
				break;
			}
		}
		return jugador;
	}
	
	public int getNumJugadores() {
		return numJugadores;
	}
	
	public boolean esCompleta() {
		return cartasJugadas != null && numJugadores == cartasJugadas.size();
	}
	
	public static class BazaResultado {
		
		private Resultado resultado;
		private Jugador jugador;
		
		private BazaResultado(Resultado resultado, Jugador jugador) {
			super();
			this.resultado = resultado;
			this.jugador = jugador;
		}
		
		public Resultado getResultado() {
			return resultado;
		}
		
		public Jugador getJugador() {
			return jugador;
		}
		
		public boolean isResultadoDeterminado() {
			return !resultado.equals(Resultado.Incompleta);
		}
		
		public boolean isParda() {
			return resultado.equals(Resultado.Parda);
		}
	}
	
	private static class CartaJugada {
		
		private Jugador jugador;
		private Carta carta;
		
		private CartaJugada(Jugador jugador, Carta carta) {
			super();
			this.jugador = jugador;
			this.carta = carta;
		}

		public Jugador getJugador() {
			return jugador;
		}

		public Carta getCarta() {
			return carta;
		}
	}
}
