package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;

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
}
