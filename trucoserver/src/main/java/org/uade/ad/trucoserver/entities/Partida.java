package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.uade.ad.trucoserver.business.CartasManager;

/**
 * Implementaciones de esta clase son las formas de juego disponibles.
 * Determinada por eleccion de usuario
 * 
 * XXX: Imagino que todo lo referido a envites iria en esta clase base, delegando si hace falta
 * 
 * @author Grupo9
 *
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.INTEGER, name="idTipoPartida")
public abstract class Partida {
	
	private static CartasManager cartasManager = CartasManager.getManager();
	
	@Id
	protected int idPartida;
	@ManyToOne
	@JoinColumn(name="idTipoPartida")
	protected TipoPartida tipoPartida;
	@OneToMany(mappedBy="partida")
	protected List<Mano> manos;

	//Las parejas que participan del juego
	//XXX: Se asume que por BBDD son maximo 2 parejas
	@ManyToMany
	@JoinTable(name="partidas_parejas")
	protected List<Pareja> parejas;
	
	//Variables para acceso rapido
	@Transient
	protected int pareja1Score = 0;
	@Transient
	protected int pareja2Score = 0;
	
	@Transient
	private List<Jugador> primerOrdenJuego = new ArrayList<>();
	@Transient
	private List<Jugador> ordenJuegoActual;
	
	public Partida() {
		super();
	}
	
	public Partida(Pareja pareja1, Pareja pareja2, List<Jugador> primerOrdenJuego) {
		super();
		this.parejas = new ArrayList<>(2);
		this.parejas.add(pareja1);
		this.parejas.add(pareja2);
		this.primerOrdenJuego = primerOrdenJuego;
	}
	
	public void cantar(String apodo, Envite envite) throws Exception {
		Mano manoActual = getManoActual();
		Jugador jugador = getJugador(apodo);
		manoActual.cantar(jugador, envite);
	}
	
	private Jugador getJugador(String apodo) {
		Pareja pareja1 = parejas.get(0);
		Pareja pareja2 = parejas.get(1);
		if (pareja1.getJugador1().getApodo().equals(apodo))
			return pareja1.getJugador1();
		if (pareja1.getJugador2().getApodo().equals(apodo))
			return pareja1.getJugador2();
		if (pareja2.getJugador1().getApodo().equals(apodo))
			return pareja2.getJugador1();
		if (pareja2.getJugador2().getApodo().equals(apodo))
			return pareja2.getJugador2();
		return null;
	}

	/**
	 * Obtener la mano actual o null si no arranco el juego
	 * 
	 * @return
	 * @throws Exception 
	 */
	private Mano getManoActual() throws Exception {
		if (manos != null && !manos.isEmpty()) {
			return manos.get(manos.size() - 1);
		} else if (manos == null) {
			manos = new ArrayList<>();
		}
		return null; //Dead
	}
	
	public void jugarCarta(Jugador jugador, Carta carta) throws Exception {
		Mano manoActual = getManoActual();
		if (manoActual == null || manoActual.terminada()) {
			circularOrdenJuego();
			throw new Exception("Mano terminada");
		} else {
			manoActual.jugar(jugador, carta);
		}
	}

	private void circularOrdenJuego() {
		if (ordenJuegoActual == null) {
			ordenJuegoActual = new ArrayList<>(primerOrdenJuego);
		}
		Collections.rotate(ordenJuegoActual, 1);
	}

	/**
	 * Obtener puntos obtenidos por jugador en el juego
	 * 
	 * @param jugador El jugador a consultar
	 * @return Puntos obtenidos o 0 si no aplica
	 * @throws Exception
	 */
	public abstract int getPuntosObtenidos(Jugador jugador) throws Exception;

	/**
	 * Repartir las cartas para la mano actual. Verifica el estado correcto del juego
	 * No se pueden repartir cartas si hay una mano en progreso
	 * 
	 * TODO
	 * 
	 * @return Mapa K: Jugador V: Set de cartas asignadas
	 * @throws Exception Si no se pueden repartir las cartas por tener mano en progreso
	 */
	public Map<Jugador, Set<Carta>> repartirCartas() throws Exception {
		Map<Jugador, Set<Carta>> cartas = null;
		Mano manoActual = getManoActual();
		if (manoActual == null || manoActual.terminada() || !manoActual.tieneMovimientos()) {
			Map<Integer, Set<Carta>> r = cartasManager.getRandomSets(parejas.size()*2);
			cartas = new HashMap<>(parejas.size()*2);
			Jugador jugador = null;
			for (int i = 0; i < r.size(); i++) {
				//FIXME Cambiar esto:
				if (i == 0) {
					jugador = parejas.get(0).getJugador1();
				} else if (i == 1) {
					jugador = parejas.get(0).getJugador2();
				} else if (i == 2) {
					jugador = parejas.get(1).getJugador1();
				} else if (i == 3) {
					jugador = parejas.get(1).getJugador2();
				}
				cartas.put(jugador, new HashSet<>(r.get(i)));
			}
			manos.add(new Mano(parejas.get(0), parejas.get(1), cartas, (ordenJuegoActual == null || ordenJuegoActual.isEmpty()) ? primerOrdenJuego : ordenJuegoActual));
		}
		
		return cartas == null ? getManoActual().getCartasAsignadas() : cartas;
	}
	
}
