package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.GrupoException;

public interface JugadorService extends Remote {

	public static final String SERVICENAME = "jugadorService"; //Nombre del servicio (para el Naming.lookup)
	
	public JugadorDTO registrarJugador(String email, String apodo, String password) throws RemoteException;
	
	public GrupoDTO crearGrupo(String nombreGrupo, String apodoJugadorAdmin, String[] apodoJugadores) throws RemoteException, GrupoException;
	
	public List<JugadorDTO> getJugadoresOnlineDisponibles() throws RemoteException;
}
