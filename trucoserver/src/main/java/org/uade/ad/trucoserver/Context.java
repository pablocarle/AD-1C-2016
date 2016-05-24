package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.uade.ad.trucorepo.dtos.NotificacionDTO;
import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.business.PartidaAbiertaIndividualMatcher;
import org.uade.ad.trucoserver.business.PartidaMatcher;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;

public abstract class Context extends UnicastRemoteObject {
	
	private static final long serialVersionUID = 1L;
	private static List<Partida> juegos;
	private static List<Jugador> jugadoresDisponibles;
	private static List<Pareja> libreParejasPending;
	private static List<Grupo> grupos;
	
	static {
		juegos = new Vector<>();
		jugadoresDisponibles = new Vector<>();
		libreParejasPending = new Vector<>();
		grupos = new Vector<>();
	}
	
	protected Context() throws RemoteException {
		super();
	}
	
	/**
	 * Agregar jugador disponible para partida abierta
	 * 
	 * @param jugador El jugador disponible
	 */
	protected static void agregarJugadorDisponible(Jugador jugador) {
		if (!jugadoresDisponibles.contains(jugador)) {
			synchronized (jugadoresDisponibles) {
				if (!jugadoresDisponibles.contains(jugador)) {
					jugadoresDisponibles.add(jugador);
				}
			}
		}
	}
	
	protected static void agregarParejaModoLibre(Pareja pareja) {
		if (!libreParejasPending.contains(pareja)) {
			synchronized(libreParejasPending) {
				if (!libreParejasPending.contains(pareja)) {
					libreParejasPending.add(pareja);
				}
			}
		}
	}
	
	protected static void eliminarJugadorDisponible(Jugador jugador) {
		jugadoresDisponibles.remove(jugador);
	}
	
	protected static void agregarJuego(Partida juego) {
		juegos.add(juego);
	}
	
	protected static Partida getPartida(int idPartida) {
		if (juegos == null) {
			return null;
		} else {
			for (Partida partida : juegos) {
				if (partida.getIdPartida() == idPartida) {
					return partida;
				}
			}
		}
		return null;
	}
	
	/**
	 * Obtener una copia de los jugadores disponibles para jugar en el sistema
	 * 
	 * @return
	 */
	protected static List<Jugador> getJugadoresOnline() {
		return new ArrayList<>(jugadoresDisponibles);
	}
	
	
	
	public void agregarInvitaciones(int idPartida, List<Jugador> jugadoresNoAdmin) {
		//Quiero notificar a los usuarios de la invitacion a la partida
		
		
	}
	
	public static List<NotificacionDTO> getInvitaciones(String apodoJugador) {
		return null;
	}

	public void agregarJugadorAColaPartidaAbierta(Jugador jugador) {
		Context.agregarJugadorDisponible(jugador);
	}

	/**
	 * Busca los jugadores para completar de acuerdo al matcher
	 * 
	 * @return
	 */
	public Partida matchearPartidaAbierta(Jugador jugador) {
		PartidaMatcher matcher = new PartidaAbiertaIndividualMatcher(jugador, getJugadoresOnline());
		Pareja[] parejas = matcher.match();
		if (parejas.length > 0) {
			//Elimino estos jugadores de jugadores disponibles
			eliminarJugadorDisponible(parejas[0].getJugador1());
			eliminarJugadorDisponible(parejas[0].getJugador2());
			eliminarJugadorDisponible(parejas[1].getJugador1());
			eliminarJugadorDisponible(parejas[1].getJugador2());
			Partida partida = new Partida();
			partida.setFechaInicio(Calendar.getInstance().getTime());
			partida.setParejas(Arrays.asList(parejas));
			partida.setTipoPartida(JuegoManager.getManager().getTipoPartida(JuegoManager.PARTIDA_ABIERTA_INDIVIDUAL));
			return partida;
		} else {
			return null;
		}
	}
}
