package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uade.ad.trucoserver.entities.Carta;
import org.uade.ad.trucoserver.entities.Envite;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Mano;
import org.uade.ad.trucoserver.entities.Pareja;

/**
 * Implementaciones de esta clase son las formas de juego disponibles.
 * Determinada por eleccion de usuario
 * 
 * XXX: Imagino que todo lo referido a envites iria en esta clase base, delegando si hace falta
 * 
 * @author Grupo9
 *
 */
public abstract class JuegoStrategy {
	
	protected int idJuego;

	//Las parejas que participan del juego
	protected Pareja pareja1;
	protected Pareja pareja2;
	
	//Variables para acceso rapido
	protected int pareja1Score = 0;
	protected int pareja2Score = 0;
	
	protected List<Mano> manos;

	private final List<Jugador> primerOrdenJuego;
	private List<Jugador> ordenJuegoActual;
	
	public JuegoStrategy(Pareja pareja1, Pareja pareja2, List<Jugador> primerOrdenJuego) {
		super();
		this.pareja1 = pareja1;
		this.pareja2 = pareja2;
		this.primerOrdenJuego = primerOrdenJuego;
	}
	
	public void cantar(String apodo, Envite envite) {
		Mano manoActual = getManoActual();
		Jugador jugador = getJugador(apodo);
		manoActual.cantar(jugador, envite);
	}
	
	private Jugador getJugador(String apodo) {
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

	private Mano getManoActual() {
		if (manos != null && !manos.isEmpty()) {
			return manos.get(manos.size() - 1);
		} else if (manos == null) {
			manos = new ArrayList<>();
		}
		if (manos.isEmpty()) {
			manos.add(new Mano(pareja1, pareja2, primerOrdenJuego));
			return manos.get(0);
		}
		return null; //Dead
	}
	
	public void jugarCarta(Jugador jugador, Carta carta) {
		Mano manoActual = getManoActual();
		if (manoActual.terminada()) {
			circularOrdenJuego();
			manoActual = new Mano(pareja1, pareja2, ordenJuegoActual);
			manos.add(manoActual);
			manoActual.jugar(jugador, carta);
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
	
}
