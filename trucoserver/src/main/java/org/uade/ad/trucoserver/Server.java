package org.uade.ad.trucoserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.uade.ad.trucorepo.interfaces.JuegoManager;
import org.uade.ad.trucorepo.interfaces.JugadorManager;

public class Server {

	public static void main(String[] args) {
		new Server().init();
	}

	private void init() {
		try {
			LocateRegistry.createRegistry(1099);
			JuegoManager juegoManager = new JuegoManagerImpl();
			JugadorManager jugadorManager = new JugadorManagerImpl();
			Naming.rebind("//localhost/" + JuegoManager.SERVICENAME, juegoManager);
			Naming.rebind("//localhost/" + JugadorManager.SERVICENAME, jugadorManager);
			System.out.println("Jugador Manager: " + "//localhost/" + JugadorManager.SERVICENAME);
			System.out.println("Juego Manager: " + "//localhost/" + JuegoManager.SERVICENAME);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
