package org.uade.ad.trucorepo.delegates;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucorepo.interfaces.JuegoService;

public class JuegoDelegate extends BusinessDelegate {

	private JuegoService juegoService;
	
	public JuegoDelegate() throws JuegoException {
		super();
		try {
			juegoService = (JuegoService) Naming.lookup("//" + webServerProperties.getProperty("server.url") + "/" + JuegoService.SERVICENAME);
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

}
