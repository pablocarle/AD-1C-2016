package org.uade.ad.trucorepo.delegates;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.GrupoException;
import org.uade.ad.trucorepo.exceptions.JugadorException;
import org.uade.ad.trucorepo.interfaces.JugadorService;
import org.uade.ad.trucorepo.interfaces.SesionService;

public class JugadoresDelegate extends BusinessDelegate {

	private JugadorService service = null;
	private SesionService sessionService = null;
	
	public JugadoresDelegate() throws JugadorException, GrupoException {
		super();
		try {
			if (!isOpenShift()) {
				service = (JugadorService) Naming.lookup("//" + webServerProperties.getProperty("server.url") + "/" + JugadorService.SERVICENAME);
				sessionService = (SesionService) Naming.lookup("//" + webServerProperties.getProperty("server.url") + "/" + SesionService.SERVICENAME);
			} else {
				service = (JugadorService) Naming.lookup("//" + System.getenv("OPENSHIFT_JBOSSEWS_IP") + ":" + webServerProperties.getProperty("server.port") + "/" + JugadorService.SERVICENAME);
				sessionService = (SesionService) Naming.lookup("//" + System.getenv("OPENSHIFT_JBOSSEWS_IP") + ":" + webServerProperties.getProperty("server.port") + "/" + SesionService.SERVICENAME);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new JugadorException("No se pudo obtener servicio remoto", e);
		}
	}
	
	public JugadorDTO validarUsuario(String apodo, String pass) throws JugadorException {
		try {
			return sessionService.inicioSesion(apodo, pass);
		} catch (RemoteException e) {
			throw new JugadorException("Fallo en el inicio de sesion", e);
		}
	}

	public JugadorDTO registrarJugador(String apodo, String email, String password) throws JugadorException {
		try {
			System.out.println("TrucoRepo - JugadoresDelegate --> registrarJugador: "+email+""+apodo+" "+password);
			return service.registrarJugador(email, apodo, password);
		} catch (RemoteException e) {
			throw new JugadorException("No se pudo registrar el jugador " + apodo, e);
		}
	}

	public GrupoDTO crearGrupo(String nombreGrupo, String apodoJugadorAdmin, String apodoJugador1, String apodoJugador2,
			String apodoJugador3) throws GrupoException {
		try {
			return service.crearGrupo(nombreGrupo, apodoJugadorAdmin, new String[]{ apodoJugador1, apodoJugador2, apodoJugador3});
		} catch (RemoteException e) {
			throw new GrupoException("" ,e); //TODO Mensaje de error por comunicacion
		}
	}
}
