package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;

public interface JuegoService extends Remote {
	
	public static final String SERVICENAME = "juegoService"; //TODO El nombre del servicio (Para el Naming.lookup)
	
	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas(int idJuego) throws RemoteException;
	
	public PartidaDTO cantarEnvite(int idJuego, JugadorDTO jugador, EnviteDTO envite) throws RemoteException;
	
	public PartidaDTO jugarCarta(int idJuego, JugadorDTO jugador, CartaDTO carta) throws RemoteException;
	
	public List<EnviteDTO> getEnvitesDisponibles(int idJuego) throws RemoteException, JuegoException;
	
	public PartidaDTO crearPartidaCerrada(String nombreGrupo) throws RemoteException, JuegoException;
	
	public PartidaDTO crearPartidaAbiertaIndividual() throws RemoteException, JuegoException;
	
	public PartidaDTO crearPartidaAbiertaPareja(String parejaApodo) throws RemoteException, JuegoException;
	
}
