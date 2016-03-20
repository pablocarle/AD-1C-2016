package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.interfaces.JugadorManager;

public class JugadorManagerImpl extends UnicastRemoteObject implements JugadorManager {

	protected JugadorManagerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public JugadorDTO registrarJugador(String email, String apodo, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoDTO crearGrupo(String nombreGrupo, Set<JugadorDTO> integrantes) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
