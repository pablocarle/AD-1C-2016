package org.uade.ad.trucoserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.uade.ad.trucorepo.interfaces.JuegoService;
import org.uade.ad.trucorepo.interfaces.JugadorService;

public class Server {

	public static void main(String[] args) {
		new Server().init();
	}

	private void init() {
		try {
			LocateRegistry.createRegistry(1099);
			JuegoService juegoManager = new JuegoServiceImpl();
			JugadorService jugadorManager = new JugadorServiceImpl();
			Naming.rebind("//localhost/" + JuegoService.SERVICENAME, juegoManager);
			Naming.rebind("//localhost/" + JugadorService.SERVICENAME, jugadorManager);
			System.out.println("Jugador Manager: " + "//localhost/" + JugadorService.SERVICENAME);
			System.out.println("Juego Manager: " + "//localhost/" + JuegoService.SERVICENAME);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
