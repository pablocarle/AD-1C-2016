package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.uade.ad.trucorepo.dtos.NotificacionDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.business.PartidaAbiertaIndividualMatcher;
import org.uade.ad.trucoserver.business.PartidaAbiertaParejaMatcher;
import org.uade.ad.trucoserver.business.PartidaMatcher;
import org.uade.ad.trucoserver.entities.Categoria;
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
	
	private static Map<String, Map<Date, NotificacionDTO>> notificaciones = new HashMap<>();
	
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
	
	protected static void addTestPlayers() {
		Categoria novato = new Categoria(1, 1, "novato", 0, 0, 0);
		Jugador b = new Jugador("b", "b", "b");
		b.setCategoria(novato);
		b.setIdJugador(2);
		Jugador c = new Jugador("c", "c", "c");
		c.setCategoria(novato);
		c.setIdJugador(2);
		Jugador d = new Jugador("d", "d", "d");
		d.setCategoria(novato);
		d.setIdJugador(2);
		if (!jugadoresDisponiblesModoLibre.contains(b))
			jugadoresDisponiblesModoLibre.add(b);
		if (!jugadoresDisponiblesModoLibre.contains(c))
			jugadoresDisponiblesModoLibre.add(c);
		if (!jugadoresDisponiblesModoLibre.contains(d))
			jugadoresDisponiblesModoLibre.add(d);
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
	
	protected static void eliminarParejaDisponible(Pareja p) {
		parejasDisponiblesModoLibre.remove(p);
	}
	
	public void agregarJuego(Partida juego) {
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
		Map<Date, NotificacionDTO> jugadorNotificaciones = null;
		NotificacionDTO notificacion = null;
		
		for (Jugador j : jugadoresNoAdmin) {
			jugadorNotificaciones = notificaciones.get(j.getApodo());
			if (jugadorNotificaciones == null) {
				jugadorNotificaciones = new HashMap<>();
				jugadorNotificaciones.put(Calendar.getInstance().getTime(), getPartidaNotificacion(idPartida));
				notificaciones.put(j.getApodo(), jugadorNotificaciones);
			} else {
				//Si ya tiene la notificacion ignora, sino agrega
				notificacion = getPartidaNotificacion(idPartida);
				if (!jugadorNotificaciones.containsValue(notificacion)) {
					jugadorNotificaciones.put(Calendar.getInstance().getTime(), notificacion);
				}
			}
		}
	}
	
	private NotificacionDTO getPartidaNotificacion(int idPartida) {
		NotificacionDTO dto = new NotificacionDTO();
		dto.setDescripcion("Nueva invitacion a partida");
		dto.setIdPartida(idPartida);
		dto.setUrl("/juegoMain.jsp?idPartida=" + idPartida);
		dto.setTipoNotificacion("nueva_partida");
		return dto;
	}

	/**
	 * Obtener invitaciones posteriores a ultimaFecha
	 * 
	 * @param apodoJugador El apodo del jugador para el que se quiere obtener invitaciones
	 * @param ultimaFecha
	 * @param idPartida 
	 * @return
	 */
	public static Map<Date, NotificacionDTO> getInvitaciones(String apodoJugador, Date ultimaFecha, Integer idPartida) {
		Map<Date, NotificacionDTO> retMap = new HashMap<>();
		if (ultimaFecha == null)
			ultimaFecha = new Date(0L);
		Map<Date, NotificacionDTO> map = notificaciones.get(apodoJugador);
		if (map != null && !map.isEmpty()) {
			Iterator<Map.Entry<Date, NotificacionDTO>> it = map.entrySet().iterator();
			Map.Entry<Date, NotificacionDTO> entry = null;
			while (it.hasNext()) {
				entry = it.next();
				if (entry.getKey().after(ultimaFecha) && (idPartida == null || idPartida.equals(entry.getValue().getIdPartida()))) {
					retMap.put(entry.getKey(), entry.getValue());
				} else if (entry.getKey().before(ultimaFecha)) {
					if (entry.getValue().getTipoNotificacion().equals("nueva_partida")) {
						it.remove();
					} else if (entry.getValue().getTipoNotificacion().equals("mensaje") && entry.getValue().getIdPartida() == idPartida) {
						it.remove();
					}
				}
			}
		}
		return retMap;
	}

	public void agregarJugadorAColaPartidaAbierta(Jugador jugador) {
		Context.agregarJugadorDisponibleModoLibre(jugador);
	}
	
	public void agregarParejaAColaPartidaAbierta(Pareja pareja) {
		Context.agregarParejaModoLibre(pareja);
	}
	
	public void agregarNotificacion(String mensaje, Integer idPartida, Jugador jugador) {
		Map<Date, NotificacionDTO> mapa = null;
		if (notificaciones.containsKey(jugador.getApodo())) {
			mapa = notificaciones.get(jugador.getApodo());
			Calendar c = Calendar.getInstance();
			Date now = c.getTime();
			boolean put = true;
			while (put) {
				if (mapa.containsKey(now)) {
					now = new Date(now.getTime() + 1);
				} else {
					mapa.put(now, new NotificacionDTO(mensaje, idPartida));
					put = false;
				}
			}
		} else {
			mapa = new HashMap<>();
			mapa.put(Calendar.getInstance().getTime(), new NotificacionDTO(mensaje, idPartida));
			synchronized (notificaciones) {
				notificaciones.put(jugador.getApodo(), mapa);
			}
		}
	}
	
	public void agregarNotificacion(String mensaje, Integer idPartida, List<Jugador> jugadores) {
		for (Jugador j : jugadores) {
			agregarNotificacion(mensaje, idPartida, j);
		}
	}
	
	public void agregarNotificacion(String mensaje, int idPartida) {
		Partida p = getPartida(idPartida);
		if (p == null) {
			throw new RuntimeException("No se encontro partida activa con id " + idPartida);
		}
		agregarNotificacion(mensaje, idPartida, p.getJugadores());
	}

	public void agregarNotificaciones(String[] mensajes, int idPartida) {
		Partida p = getPartida(idPartida);
		List<Jugador> jugadores = p.getJugadores();
		Map<Date, NotificacionDTO> mapa = null;
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		for (Jugador j : jugadores) {
			now = c.getTime();
			if (notificaciones.containsKey(j.getApodo())) {
				mapa = notificaciones.get(j.getApodo());
				boolean put = true;
				for (String mensaje : mensajes) {
					put = true;
					while (put) {
						if (mapa.containsKey(now)) {
							now = new Date(now.getTime() + 1);
						} else {
							mapa.put(now, new NotificacionDTO(mensaje, idPartida));
							put = false;
						}
					}
				}
			} else {
				mapa = new HashMap<>();
				synchronized(notificaciones) {
					boolean put = true;
					for (String mensaje : mensajes) {
						put = true;
						while (put) {
							if (mapa.containsKey(now)) {
								now = new Date(now.getTime() + 1);
							} else {
								mapa.put(now, new NotificacionDTO(mensaje, idPartida));
								put = false;
							}
						}
					}
				}
			}
		}
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
	
	public void agregarInvitacion(Partida partida, Jugador j) {
		Map<Date, NotificacionDTO> jugadorNotificaciones = notificaciones.get(j.getApodo());
		if (jugadorNotificaciones == null) {
			jugadorNotificaciones = new HashMap<>();
			jugadorNotificaciones.put(Calendar.getInstance().getTime(), getPartidaNotificacion(partida.getIdPartida()));
			notificaciones.put(j.getApodo(), jugadorNotificaciones);
		} else {
			//Si ya tiene la notificacion ignora, sino agrega
			NotificacionDTO notificacion = getPartidaNotificacion(partida.getIdPartida());
			if (!jugadorNotificaciones.containsValue(notificacion)) {
				jugadorNotificaciones.put(Calendar.getInstance().getTime(), notificacion);
			}
		}
	}

	public Partida matchearPartidaAbiertaPareja(Jugador jugadorCreador, Pareja pareja) {
		PartidaMatcher matcher = new PartidaAbiertaParejaMatcher(pareja, getParejasDisponiblesModoLibre());
		Pareja[] parejas = matcher.match();
		if (parejas.length > 0) {
			//Elimino las parejas de parejas disponibles
			eliminarParejaDisponible(parejas[0]);
			eliminarParejaDisponible(parejas[1]);
			Partida partida = new Partida();
			partida.setFechaInicio(Calendar.getInstance().getTime());
			partida.setParejas(Arrays.asList(parejas));
			partida.setTipoPartida(JuegoManager.getManager().getTipoPartida(JuegoManager.PARTIDA_ABIERTA_PAREJA));
			return partida;
		} else {
			return null;
		}
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

	public synchronized void actualizarPartida(Partida p) {
		Iterator<Partida> it = juegos.iterator();
		while (it.hasNext()) {
			if (it.next().equals(p)) {
				it.remove();
			}
		}
		juegos.add(p);
	}
}
