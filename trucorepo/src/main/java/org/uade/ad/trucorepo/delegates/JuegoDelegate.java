package org.uade.ad.trucorepo.delegates;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.NotificacionesDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucorepo.interfaces.JuegoService;

public class JuegoDelegate extends BusinessDelegate {

	private JuegoService juegoService;
	
	public JuegoDelegate() throws JuegoException {
		super();
		try {
			if (!isOpenShift()) {
				juegoService = (JuegoService) Naming.lookup("//" + webServerProperties.getProperty("server.url") + "/" + JuegoService.SERVICENAME);
			} else {
				juegoService = (JuegoService) Naming.lookup("//" + System.getenv("OPENSHIFT_JBOSSEWS_IP") + ":" + webServerProperties.getProperty("server.port") + "/" + JuegoService.SERVICENAME);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new JuegoException(e);
		} 
	}
	
	public List<JugadorDTO> getJugadoresDisponibles() throws JuegoException {
		try {
			return juegoService.getJugadoresDisponibles();
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException("RemoteException:", e);
		}
	}

	public PartidaDTO crearNuevaPartidaAbierta(JugadorDTO jugador) throws JuegoException {
		try {
			return juegoService.crearPartidaAbiertaIndividual(jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	public PartidaDTO crearNuevaPartidaCerrada(JugadorDTO user, GrupoDTO grupo) throws JuegoException {
		try {
			return juegoService.crearPartidaCerrada(user, grupo);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	/**
	 * Obtener notificaciones para el jugador
	 * 
	 * @param jugador El jugador referencia de notificaciones
	 * @param fechaReferencia Fecha de referencia (se obtienen notificaciones posteriores
	 * ). Puede ser null para todas las notificaciones
	 * @param idPartida idPartida o null
	 * @return Lista de notificaciones o lista vacia
	 * @throws JuegoException
	 */
	public NotificacionesDTO getNotificaciones(JugadorDTO jugador, Date fechaReferencia, Integer idPartida) throws JuegoException {
		try {
			return juegoService.getNotificaciones(jugador, fechaReferencia, idPartida);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	public PartidaDTO crearNuevaPartidaAbiertaPareja(JugadorDTO user, int idPareja) throws JuegoException {
		try {
			return juegoService.crearPartidaAbiertaPareja(user, idPareja);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	public PartidaDTO irAlMazo(JugadorDTO jugador, int idPartida) throws JuegoException {
		try {
			return juegoService.irAlMazo(idPartida, jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	public PartidaDTO cantar(JugadorDTO jugador, int idPartida, int idEnvite) throws JuegoException {
		try {
			return juegoService.cantarEnvite(idPartida, jugador, idEnvite);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	public PartidaDTO jugarCarta(JugadorDTO jugador, int idPartida, int idCarta) throws JuegoException {
		try {
			return juegoService.jugarCarta(idPartida, jugador, idCarta);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	public PartidaDTO repartirCartas(JugadorDTO jugador, int idPartida) throws JuegoException {
		try {
			return juegoService.repartirCartas(idPartida, jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}

	public PartidaDTO getPartida(JugadorDTO jugador, int idPartida) throws JuegoException {
		try{
			return juegoService.getPartida(idPartida, jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new JuegoException(e);
		}
	}
}
