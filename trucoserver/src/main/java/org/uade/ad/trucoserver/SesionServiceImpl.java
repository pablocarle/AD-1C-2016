package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.interfaces.SesionService;

public class SesionServiceImpl extends UnicastRemoteObject implements SesionService {

	protected SesionServiceImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public JugadorDTO inicioSesion(String apodo, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
