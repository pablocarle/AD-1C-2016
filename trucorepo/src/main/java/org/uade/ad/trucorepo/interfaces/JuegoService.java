package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.NotificacionesDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;

public interface JuegoService extends Remote {
	
	public static final String SERVICENAME = "juegoService"; //TODO El nombre del servicio (Para el Naming.lookup)
	
	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas(int idJuego, JugadorDTO jugador) throws RemoteException, JuegoException;
	
	public PartidaDTO cantarEnvite(int idJuego, JugadorDTO jugador, int idEnvite) throws RemoteException, JuegoException;
	
	public PartidaDTO jugarCarta(int idJuego, JugadorDTO jugador, int idCarta) throws RemoteException, JuegoException;
	
	public List<EnviteDTO> getEnvitesDisponibles(int idJuego, JugadorDTO jugador) throws RemoteException, JuegoException;
	
	public PartidaDTO crearPartidaAbiertaIndividual(JugadorDTO jugador) throws RemoteException, JuegoException;
	
	public List<JugadorDTO> getJugadoresDisponibles() throws RemoteException, JuegoException;

	public PartidaDTO crearPartidaCerrada(JugadorDTO user, GrupoDTO grupo) throws RemoteException, JuegoException;

	public NotificacionesDTO getNotificaciones(JugadorDTO jugador, Date fechaReferencia) throws RemoteException, JuegoException;

	public PartidaDTO crearPartidaAbiertaPareja(JugadorDTO user, int idPareja) throws RemoteException, JuegoException;

	public PartidaDTO irAlMazo(int idPartida, JugadorDTO jugador) throws RemoteException, JuegoException;

	public PartidaDTO getPartida(int idPartida, JugadorDTO jugador) throws RemoteException, JuegoException;
	
}
