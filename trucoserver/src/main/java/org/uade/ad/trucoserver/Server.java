package org.uade.ad.trucoserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.uade.ad.trucorepo.interfaces.JuegoService;
import org.uade.ad.trucorepo.interfaces.JugadorService;
import org.uade.ad.trucorepo.interfaces.SesionService;

public class Server {

	public static void main(String[] args) {
		new Server().init();
	}

	private void init() {
		try {
			LocateRegistry.createRegistry(1099);
			JuegoService juegoManager = new JuegoServiceImpl();
			JugadorService jugadorManager = new JugadorServiceImpl();
			SesionService sessionManager = new SesionServiceImpl();
			Naming.rebind("//localhost/" + JuegoService.SERVICENAME, juegoManager);
			Naming.rebind("//localhost/" + JugadorService.SERVICENAME, jugadorManager);
			Naming.rebind("//localhost/" + SesionService.SERVICENAME, sessionManager);
			System.out.println("Jugador Manager: " + "//localhost/" + JugadorService.SERVICENAME);
			System.out.println("Juego Manager: " + "//localhost/" + JuegoService.SERVICENAME);
			System.out.println("Sesion Manager: " + "//localhost/" + SesionService.SERVICENAME);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
