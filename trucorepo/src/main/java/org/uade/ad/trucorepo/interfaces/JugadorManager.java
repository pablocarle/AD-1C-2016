package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;

public interface JugadorManager extends Remote {

	public JugadorDTO registrarJugador(String email, String apodo, String password) throws RemoteException;
	
	public GrupoDTO crearGrupo(String nombreGrupo, Set<JugadorDTO> integrantes) throws RemoteException;
}
