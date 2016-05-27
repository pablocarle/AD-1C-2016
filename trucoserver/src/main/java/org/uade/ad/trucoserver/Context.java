package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.uade.ad.trucorepo.dtos.NotificacionDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.business.PartidaAbiertaIndividualMatcher;
import org.uade.ad.trucoserver.business.PartidaAbiertaParejaMatcher;
import org.uade.ad.trucoserver.business.PartidaMatcher;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;

public abstract class Context extends UnicastRemoteObject {
	
	private static final long serialVersionUID = 1L;
	private static List<Partida> juegos;
	
	private static List<Jugador> jugadoresDisponibles; //Jugadores logueados en el sistema
	private static List<Jugador> jugadoresDisponiblesModoLibre;
	private static List<Pareja> parejasDisponiblesModoLibre;
	private static List<Grupo> grupos;
	
	static {
		juegos = new Vector<>();
		jugadoresDisponibles = new Vector<>();
		parejasDisponiblesModoLibre = new Vector<>();
		jugadoresDisponiblesModoLibre = new Vector<>();
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
	
	protected static void agregarJugadorDisponibleModoLibre(Jugador jugador) {
		if (!jugadoresDisponiblesModoLibre.contains(jugador)) {
			synchronized (jugadoresDisponiblesModoLibre) {
				if (!jugadoresDisponiblesModoLibre.contains(jugador)) {
					jugadoresDisponiblesModoLibre.add(jugador);
				}
			}
		}
	}
	
	protected static void agregarParejaModoLibre(Pareja pareja) {
		if (!parejasDisponiblesModoLibre.contains(pareja)) {
			synchronized(parejasDisponiblesModoLibre) {
				if (!parejasDisponiblesModoLibre.contains(pareja)) {
					parejasDisponiblesModoLibre.add(pareja);
				}
			}
		}
	}
	
	protected static void agregarGrupoEnJuego(Grupo grupo) {
		if (!grupos.contains(grupo)) {
			synchronized(grupos) {
				if (!grupos.contains(grupo)) {
					grupos.add(grupo);
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
	
	public Partida getPartida(int idPartida) {
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
	
	protected static List<Pareja> getParejasDisponiblesModoLibre() {
		return new ArrayList<>(parejasDisponiblesModoLibre);
	}
	
	protected static List<Jugador> getJugadoresDisponiblesModoLibre() {
		return new ArrayList<>(jugadoresDisponiblesModoLibre);
	}
	
	public void agregarInvitaciones(int idPartida, List<Jugador> jugadoresNoAdmin) {
		//Quiero notificar a los usuarios de la invitacion a la partida
		
		
	}
	
	public static List<NotificacionDTO> getInvitaciones(String apodoJugador) {
		return null;
	}

	public void agregarJugadorAColaPartidaAbierta(Jugador jugador) {
		Context.agregarJugadorDisponibleModoLibre(jugador);
	}
	
	public void agregarParejaAColaPartidaAbierta(Pareja pareja) {
		Context.agregarParejaModoLibre(pareja);
	}

	/**
	 * Busca los jugadores para completar de acuerdo al matcher
	 * 
	 * @return
	 */
	public Partida matchearPartidaAbierta(Jugador jugador) {
		PartidaMatcher matcher = new PartidaAbiertaIndividualMatcher(jugador, jugadoresDisponiblesModoLibre);
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
	
	public Partida matchearPartidaAbiertaPareja(Pareja pareja) {
		//TODO Invocar matcher
		PartidaMatcher matcher = new PartidaAbiertaParejaMatcher(pareja, getParejasDisponiblesModoLibre());
		return null;
	}

	public void agregarPartida(Partida partida) {
		
	}
	
	/**
	 * Verificar si un jugador pertenece a una partida
	 * 
	 * @param idJugador
	 * @param idPartida
	 * @return
	 */
	protected boolean assertJugadorPartida(String apodoJugador, int idPartida) throws JuegoException {
		for (Partida p : juegos) {
			if (p.contieneJugador(apodoJugador)) {
				return true;
			}
		}
		throw new JuegoException("La partida con ID " + idPartida + " no existe o el juegador con id " + apodoJugador + " no pertenece a la partida");
	}

	public void actualizarPartida(Partida p) {
		//TODO Actualiza ultimo estado de la partida p
	}
}
