package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.interfaces.JugadorService;
import org.uade.ad.trucoserver.business.JugadorManager;
import org.uade.ad.trucoserver.entities.Jugador;

public class JugadorServiceImpl extends Context implements JugadorService {

	private JugadorManager manager = JugadorManager.getManager();
	
	protected JugadorServiceImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public JugadorDTO registrarJugador(String email, String apodo, String password) throws RemoteException {
		try {
			Jugador jugador = manager.registrarJugador(apodo, email, password);
			return jugador.getDTO();
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<JugadorDTO> getJugadoresOnlineDisponibles() throws RemoteException {
		List<Jugador> jugadoresDisponibles = getJugadoresDisponibles();
		List<JugadorDTO> jugadores = new ArrayList<>(jugadoresDisponibles.size());
		for (Jugador j : jugadoresDisponibles) {
			jugadores.add(j.getDTO());
		}
		return jugadores;
	}

	@Override
	public GrupoDTO crearGrupo(String nombreGrupo, int idJugadorAdmin, int[] integrantes) throws RemoteException {
		//TODO 
		return null;
	}
}
