package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucorepo.interfaces.JuegoService;
import org.uade.ad.trucoserver.entities.Carta;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Partida;

/**
 * Entry point servicios de juego
 * 
 * @author Grupo9
 *
 */
public class JuegoServiceImpl extends Context implements JuegoService {

	protected JuegoServiceImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas(int idJuego) throws RemoteException {
		try {
			Partida partida = getPartida(idJuego);
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
	public void cantarEnvite(int idJuego, JugadorDTO jugador, EnviteDTO envite) throws RemoteException {
		
	}

	@Override
	public void jugarCarta(int idJuego, JugadorDTO jugador, CartaDTO carta) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EnviteDTO> getEnvitesDisponibles(int idJuego) throws RemoteException, JuegoException {
		// TODO Auto-generated method stub
		return null;
	}
}
