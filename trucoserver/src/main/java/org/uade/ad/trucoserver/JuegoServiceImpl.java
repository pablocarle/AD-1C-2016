package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.NotificacionDTO;
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

	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas(int idJuego) throws RemoteException {
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PartidaDTO crearPartidaCerrada(JugadorDTO user, GrupoDTO grupo) throws RemoteException, JuegoException {
		Partida partida = manager.crearPartidaCerrada(user.getApodo(), grupo.getIdGrupo(), this);
		return partida.getDTO();
	}

	@Override
	public List<NotificacionDTO> getNotificaciones(JugadorDTO jugador) throws RemoteException, JuegoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartidaDTO cantarEnvite(int idJuego, JugadorDTO jugador, int idEnvite)
			throws RemoteException, JuegoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartidaDTO jugarCarta(int idJuego, JugadorDTO jugador, int idCarta) throws RemoteException, JuegoException {
		// TODO Auto-generated method stub
		/**
		 * TODO
		 * Aca debe verificar que la partida exista. Si existe llama al manager que se encarga de las
		 * demas validaciones
		 */
		return null;
	}

	@Override
	public PartidaDTO irAlMazo(int idPartida, JugadorDTO jugador) throws RemoteException, JuegoException {
		// TODO Auto-generated method stub
		return null;
	}
}
