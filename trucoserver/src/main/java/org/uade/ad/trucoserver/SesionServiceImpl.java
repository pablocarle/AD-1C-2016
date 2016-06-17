package org.uade.ad.trucoserver;

import java.rmi.RemoteException;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.JugadorException;
import org.uade.ad.trucorepo.interfaces.SesionService;
import org.uade.ad.trucoserver.business.JugadorManager;
import org.uade.ad.trucoserver.entities.Jugador;

public class SesionServiceImpl extends Context implements SesionService {

	private JugadorManager jugadorManager = JugadorManager.getManager();
	
	protected SesionServiceImpl() throws RemoteException {
		super();
	}
	
	public SesionServiceImpl(int port) throws RemoteException {
		super(port);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public JugadorDTO inicioSesion(String apodo, String password) throws RemoteException, JugadorException {
		if (jugadorManager.esLoginValido(apodo, password)) {
			Jugador jugador = jugadorManager.getJugador(apodo);
			agregarJugadorDisponible(jugador);
			return jugador.getDTO();
		} else {
			throw new JugadorException("No es login valido");
		}
	}
}
