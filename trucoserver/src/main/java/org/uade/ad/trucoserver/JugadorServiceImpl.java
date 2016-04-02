package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
		// TODO Auto-generated constructor stub
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
	public GrupoDTO crearGrupo(String nombreGrupo, Set<JugadorDTO> integrantes) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
