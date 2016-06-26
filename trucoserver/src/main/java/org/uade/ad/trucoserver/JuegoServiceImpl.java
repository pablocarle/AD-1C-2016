package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.NotificacionDTO;
import org.uade.ad.trucorepo.dtos.NotificacionesDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucorepo.interfaces.JuegoService;
import org.uade.ad.trucoserver.business.ChicoTerminadoEvent;
import org.uade.ad.trucoserver.business.ChicoTerminadoObserver;
import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.business.ManoTerminadaEvent;
import org.uade.ad.trucoserver.business.ManoTerminadaObserver;
import org.uade.ad.trucoserver.business.PartidaTerminadaEvent;
import org.uade.ad.trucoserver.business.PartidaTerminadaObserver;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Partida;

/**
 * Entry point servicios de juego
 * 
 * @author Grupo9
 *
 */
public class JuegoServiceImpl extends Context implements JuegoService, PartidaTerminadaObserver, ChicoTerminadoObserver, ManoTerminadaObserver {

	private JuegoManager manager = JuegoManager.getManager();
	
	protected JuegoServiceImpl() throws RemoteException {
		super();
	}
	
	public JuegoServiceImpl(int port) throws RemoteException {
		super(port);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public List<JugadorDTO> getJugadoresDisponibles() throws RemoteException, JuegoException {
		List<Jugador> jugadoresOnline = getJugadoresOnline();
		return DTOUtil.getDTOs(jugadoresOnline, JugadorDTO.class);
	}

	@Override
	public PartidaDTO crearPartidaAbiertaIndividual(JugadorDTO jugador) throws RemoteException, JuegoException {
		Partida partida = manager.crearPartidaAbiertaIndividual(jugador.getApodo(), this);
		partida.agregarObserver((ChicoTerminadoObserver)this);
		partida.agregarObserver((ManoTerminadaObserver)this);
		partida.agregarObserver((PartidaTerminadaObserver)this);
		return partida.getDTO();
	}

	@Override
	public PartidaDTO crearPartidaAbiertaPareja(JugadorDTO user, int idPareja) throws RemoteException, JuegoException {
		Partida partida = manager.crearPartidaAbiertaPareja(user.getApodo(), idPareja, this);
		partida.agregarObserver((ChicoTerminadoObserver)this);
		partida.agregarObserver((ManoTerminadaObserver)this);
		partida.agregarObserver((PartidaTerminadaObserver)this);
		return partida.getDTO();
	}
	
	@Override
	public PartidaDTO crearPartidaCerrada(JugadorDTO user, GrupoDTO grupo) throws RemoteException, JuegoException {
		Partida partida = manager.crearPartidaCerrada(user.getApodo(), grupo.getIdGrupo(), this);
		partida.agregarObserver((ChicoTerminadoObserver)this);
		partida.agregarObserver((ManoTerminadaObserver)this);
		partida.agregarObserver((PartidaTerminadaObserver)this);
		return partida.getDTO();
	}

	@Override
	public NotificacionesDTO getNotificaciones(JugadorDTO jugador, Date fechaReferencia, Integer idPartida) throws RemoteException, JuegoException {
		Map<Date, List<NotificacionDTO>> notificaciones = Context.getInvitaciones(jugador.getApodo(), fechaReferencia, idPartida);
		List<NotificacionDTO> nList = new ArrayList<>();
		for (Map.Entry<Date, List<NotificacionDTO>> entry : notificaciones.entrySet()) {
			nList.addAll(entry.getValue());
		}
		return new NotificacionesDTO(nList);
	}

	@Override
	public PartidaDTO cantarEnvite(int idJuego, JugadorDTO jugador, int idEnvite)
			throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idJuego);
		PartidaDTO dto = manager.cantarEnvite(idJuego, jugador.getApodo(), idEnvite, this).getDTO();
		return dto;
	}

	@Override
	public PartidaDTO jugarCarta(int idJuego, JugadorDTO jugador, int idCarta) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idJuego);
		PartidaDTO dto = manager.jugarCarta(idJuego, jugador.getApodo(), idCarta, this).getDTO();
		return dto;
	}

	@Override
	public PartidaDTO irAlMazo(int idPartida, JugadorDTO jugador) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idPartida);
		System.out.println("Irse al mazo (JuegoServiceImpl)");
		PartidaDTO dto = manager.irAlMazo(idPartida, jugador.getApodo(), this).getDTO();
		return dto;
	}

	@Override
	public PartidaDTO repartirCartas(int idJuego, JugadorDTO jugador) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idJuego);
		return manager.repartirCartas(idJuego, jugador.getApodo(), this);
	}

	@Override
	public PartidaDTO getPartida(int idPartida, JugadorDTO jugador) throws RemoteException, JuegoException {
		assertJugadorPartida(jugador.getApodo(), idPartida);
		return getPartida(idPartida).getDTO();
	}

	@Override
	public void manoTerminada(ManoTerminadaEvent event) {
		System.out.println("JuegoServiceImpl notificado de fin de Mano");
		agregarNotificaciones(new String[]{
				"Fin de mano con resultado parcial " + event.getPuntosObtenidosPareja1() + " a " + event.getPuntosObtenidosPareja2()
		}, event.getIdPartida());
	}

	@Override
	public void chicoTerminado(ChicoTerminadoEvent event) {
		System.out.println("JuegoServiceImpl notificado de fin de Chico");
		agregarNotificaciones(new String[]{"Fin de chico con resultado " + event.getChico().getPareja1Score() + " a " + event.getChico().getPareja2Score(),
											"Pareja ganadora: " + event.getParejaGanadora()}, event.getChico().getPartida().getIdPartida());
	}

	@Override
	public void finPartida(PartidaTerminadaEvent partida) throws JuegoException {
		System.out.println("JuegoServiceImpl notificado de fin de Partida");
		agregarNotificaciones(new String[]{"Fin de la partida",
											"Pareja ganadora: " + Arrays.toString(partida.getGanadores()),
											"Puntos de jugador " + partida.getGanadores()[0] + ": " + partida.getPartida().getPuntosObtenidos(partida.getGanadores()[0]),
											"Puntos de jugador " + partida.getGanadores()[1] +  ": " + partida.getPartida().getPuntosObtenidos(partida.getGanadores()[1])}, partida.getPartida().getIdPartida());
	}
}
