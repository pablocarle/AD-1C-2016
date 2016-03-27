package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.uade.ad.trucorepo.dtos.JugadorDTO;

public interface SesionService extends Remote {
	
	public static final String SERVICENAME = "sessionService";
	
	public JugadorDTO inicioSesion(String apodo, String password) throws RemoteException;
	
}
