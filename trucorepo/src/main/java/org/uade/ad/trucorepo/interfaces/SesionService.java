package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.JugadorException;

public interface SesionService extends Remote {
	
	public static final String SERVICENAME = "sessionService";
	
	public JugadorDTO inicioSesion(String apodo, String password) throws RemoteException, JugadorException;
	
}
