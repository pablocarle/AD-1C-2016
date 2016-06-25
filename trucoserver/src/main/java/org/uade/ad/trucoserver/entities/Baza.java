package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="bazas")
public class Baza {
	
	public enum Resultado {
		Parda, NoParda, Incompleta
	}

	@Id
	@Column(name="idBaza")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBaza;
	@ManyToOne
	@JoinColumn(name="idMano")
	private Mano mano;
	@Column
	private int rondaBaza;
	@Column
	private Date fechaInicio = Calendar.getInstance().getTime();
	@Column
	private Date fechaFin;
	@OneToMany(mappedBy="baza", cascade=CascadeType.ALL)
	private List<CartaJugada> cartasJugadas;
	@Transient
	private List<Jugador> ordenJuego;
	@Transient
	private int numJugadores;
	
	public Baza() {
		super();
	}
	
	Baza(Mano mano, int numJugadores, int rondaBaza, List<Jugador> ordenJuego) {
		super();
		assert(numJugadores == ordenJuego.size());
		this.mano = mano;
		this.rondaBaza = rondaBaza;
		this.numJugadores = numJugadores;
		this.ordenJuego = ordenJuego;
	}
	
	Baza(Mano mano) {
		super();
		this.mano = mano;
	}
	
	void jugarCarta(Jugador jugador, Carta carta) {
		if (cartasJugadas == null) {
			cartasJugadas = new ArrayList<>();
		}
		if (ordenJuego.get(cartasJugadas.size()).equals(jugador)) {
			cartasJugadas.add(new CartaJugada(this, jugador, carta));
		} else {
			throw new RuntimeException("No es el turno del jugador " + jugador);
		}
	}
	
	/**
	 * Obtener el resultado de esta baza. Incluye estado de baza en curso
	 * 
	 * @return
	 */
	public BazaResultado getResultado() {
		if (cartasJugadas != null && cartasJugadas.size() == numJugadores) {
			Comparator<Carta> valorTrucoComparator = new Carta.ValorTrucoComparador(false);
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
					return new BazaResultado(Resultado.NoParda, getJugadorPorCartaJugada(cartas.get(1)));
				default:
					return new BazaResultado(Resultado.NoParda, getJugadorPorCartaJugada(cartas.get(0)));
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
	
	public Carta getCartaJugada(Jugador j) {
		if (cartasJugadas != null) {
			for (CartaJugada cj : cartasJugadas) {
				if (cj.getJugador().equals(j)) {
					return cj.getCarta();
				}
			}
		}
		return null;
	}
	
	public int getIdBaza() {
		return idBaza;
	}

	public void setIdBaza(int idBaza) {
		this.idBaza = idBaza;
	}

	public Mano getMano() {
		return mano;
	}

	public void setMano(Mano mano) {
		this.mano = mano;
	}

	public int getRondaBaza() {
		return rondaBaza;
	}

	public void setRondaBaza(int rondaBaza) {
		this.rondaBaza = rondaBaza;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public void setCartasJugadas(List<CartaJugada> cartasJugadas) {
		this.cartasJugadas = cartasJugadas;
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

	public int getNumCartasJugadas() {
		if (cartasJugadas != null) {
			return cartasJugadas.size();
		}
		return 0;
	}
}
