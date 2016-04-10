package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;

public interface JuegoService extends Remote {
	
	public static final String SERVICENAME = "juegoService"; //TODO El nombre del servicio (Para el Naming.lookup)
	
	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas(int idJuego) throws RemoteException;
	
	public void cantarEnvite(int idJuego, JugadorDTO jugador, EnviteDTO envite) throws RemoteException;
	
	public void jugarCarta(int idJuego, JugadorDTO jugador, CartaDTO carta) throws RemoteException;
	
}
