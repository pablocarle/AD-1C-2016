package org.uade.ad.trucoserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import org.uade.ad.trucorepo.delegates.BusinessDelegate;
import org.uade.ad.trucorepo.interfaces.JuegoService;
import org.uade.ad.trucorepo.interfaces.JugadorService;
import org.uade.ad.trucorepo.interfaces.RankingService;
import org.uade.ad.trucorepo.interfaces.SesionService;

public final class Server {

	private static Properties p = null;
	
	static {
		InputStream is = null;
		try {
			is = BusinessDelegate.class.getClassLoader().getResourceAsStream("rmi.properties");
			p = new Properties();
			p.load(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Server().init();
	}

	private void init() {
		try {
			System.setSecurityManager(null);
			if (isOpenShift()) {
				LocateRegistry.createRegistry(Integer.parseInt(getConfig("rmi.port")));
			} else {
				LocateRegistry.createRegistry(1099);
			}
				
			JuegoService juegoManager = new JuegoServiceImpl();
			JugadorService jugadorManager = new JugadorServiceImpl();
			SesionService sessionManager = new SesionServiceImpl();
			RankingService rankingService = new RankingServiceImpl();
			if (isOpenShift()) {
				String localhost = System.getenv("OPENSHIFT_JBOSSEWS_IP") + ":" + getConfig("rmi.port");
				Naming.rebind("//" + localhost + "/" + JuegoService.SERVICENAME, juegoManager);
				Naming.rebind("//" + localhost + "/" + JugadorService.SERVICENAME, jugadorManager);
				Naming.rebind("//" + localhost + "/" + SesionService.SERVICENAME, sessionManager);
				Naming.rebind("//" + localhost + "/" + RankingService.SERVICENAME, rankingService);
			} else {
				Naming.rebind("//localhost/" + JuegoService.SERVICENAME, juegoManager);
				Naming.rebind("//localhost/" + JugadorService.SERVICENAME, jugadorManager);
				Naming.rebind("//localhost/" + SesionService.SERVICENAME, sessionManager);
				Naming.rebind("//localhost/" + RankingService.SERVICENAME, rankingService);
			}
			System.out.println("Jugador Manager: " + "//localhost/" + JugadorService.SERVICENAME);
			System.out.println("Juego Manager: " + "//localhost/" + JuegoService.SERVICENAME);
			System.out.println("Sesion Manager: " + "//localhost/" + SesionService.SERVICENAME);
			System.out.println("Ranking Manager: " + "//localhost/" + RankingService.SERVICENAME);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String getConfig(String config) {
		return p.getProperty(config);
	}
	
	public static boolean isOpenShift() {
		return System.getenv("OPENSHIFT_JBOSSEWS_IP") != null;
	}
}
