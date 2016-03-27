package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.interfaces.SesionService;
import org.uade.ad.trucoserver.business.JugadorManager;

public class SesionServiceImpl extends UnicastRemoteObject implements SesionService {

	private JugadorManager jugadorManager = JugadorManager.getManager();
	
	protected SesionServiceImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public JugadorDTO inicioSesion(String apodo, String password) throws RemoteException {
		if (jugadorManager.isValidLogin(apodo, password)) {
			return jugadorManager.getJugador(apodo).getDTO();
		} else {
			throw new RemoteException("Invalid Login");
		}
	}
}
