package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.NotificacionesDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucorepo.interfaces.JuegoService;
import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.entities.Carta;
import org.uade.ad.trucoserver.entities.Chico;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Partida;

/**
 * Entry point servicios de juego
 * 
 * @author Grupo9
 *
 */
public class JuegoServiceImpl extends Context implements JuegoService {

	private JuegoManager manager = JuegoManager.getManager();
	
	protected JuegoServiceImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	private Map<JugadorDTO, Set<CartaDTO>> convertToDTO(Map<Jugador, Set<Carta>> cartasAsignadas) {
		Map<JugadorDTO, Set<CartaDTO>> retMap = new HashMap<>(cartasAsignadas.size());
		Set<CartaDTO> cartaSet = null;
		for (Map.Entry<Jugador, Set<Carta>> entry : cartasAsignadas.entrySet()) {
			cartaSet = new HashSet<>(entry.getValue().size());
			for (Carta carta : entry.getValue()) {
				cartaSet.add(carta.getDTO());
			}
			retMap.put(entry.getKey().getDTO(), cartaSet);
		}
		return retMap;
	}

	@Override
	public List<EnviteDTO> getEnvitesDisponibles(int idJuego, JugadorDTO jugador) throws RemoteException, JuegoException {
		/**
		 * TODO
		 * 
		 */
		return null;
	}

	@Override
	public List<JugadorDTO> getJugadoresDisponibles() throws RemoteException, JuegoException {
		List<Jugador> jugadoresOnline = getJugadoresOnline();
		return convertToDTO(jugadoresOnline);
	}

	private List<JugadorDTO> convertToDTO(List<Jugador> jugadores) {
		List<JugadorDTO> retList = new ArrayList<JugadorDTO>(jugadores.size());
		for (Jugador j : jugadores) {
			retList.add(j.getDTO());
		}
		return retList;
	}

	@Override
	public PartidaDTO crearPartidaAbiertaIndividual(JugadorDTO jugador) throws RemoteException, JuegoException {
		Partida partida = manager.crearPartidaAbiertaIndividual(jugador.getApodo(), this);
		return partida.getDTO();
	}

	@Override
	public PartidaDTO crearPartidaAbiertaPareja(JugadorDTO user, int idPareja) throws RemoteException, JuegoException {
		Partida partida = manager.crearPartidaAbiertaPareja(user.getApodo(), idPareja, this);
		return partida.getDTO();
	}
	
	@Override
	public PartidaDTO crearPartidaCerrada(JugadorDTO user, GrupoDTO grupo) throws RemoteException, JuegoException {
		Partida partida = manager.crearPartidaCerrada(user.getApodo(), grupo.getIdGrupo(), this);
		return partida.getDTO();
	}

	@Override
	public NotificacionesDTO getNotificaciones(JugadorDTO jugador, Date fechaReferencia) throws RemoteException, JuegoException {
		return new NotificacionesDTO(new ArrayList<>(Context.getInvitaciones(jugador.getApodo(), fechaReferencia).values()));
	}

	@Override
	public PartidaDTO cantarEnvite(int idJuego, JugadorDTO jugador, int idEnvite)
			throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idJuego);
		return manager.cantarEnvite(idJuego, jugador.getApodo(), idEnvite, this).getDTO();
	}

	@Override
	public PartidaDTO jugarCarta(int idJuego, JugadorDTO jugador, int idCarta) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idJuego);
		return manager.jugarCarta(idJuego, jugador.getApodo(), idCarta, this).getDTO();
	}

	@Override
	public PartidaDTO irAlMazo(int idPartida, JugadorDTO jugador) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idPartida);
		return manager.irAlMazo(idPartida, jugador.getApodo(), this).getDTO();
	}

	@Override
	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas(int idJuego, JugadorDTO jugador) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idJuego);
		try {
			Chico partida = getPartida(idJuego).getChicoActual();
			if (partida == null) {
				throw new RemoteException("No se encontro la partida con id " + idJuego);
			}
			Map<Jugador, Set<Carta>> cartasAsignadas = partida.repartirCartas();
			return convertToDTO(cartasAsignadas);
		} catch (Exception e) {
			throw new RemoteException("Ocurrio un problema al repartir cartas", e);
		}
	}

	@Override
	public PartidaDTO getPartida(int idPartida, JugadorDTO jugador) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idPartida);
		return getPartida(idPartida).getDTO();
	}
}
