package org.uade.ad.trucoserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
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
import org.uade.ad.trucoserver.business.CartasManager;
import org.uade.ad.trucoserver.business.JuegoLogManager;
import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.business.JugadorManager;

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
			Registry registry = null;
			if (isOpenShift()) {
				System.out.println("Set property java.rmi.server.hostname a " + System.getenv("OPENSHIFT_JBOSSEWS_IP"));
				System.setProperty("java.rmi.server.hostname", System.getenv("OPENSHIFT_JBOSSEWS_IP"));
				registry = LocateRegistry.createRegistry(Integer.parseInt(getConfig("rmi.port")));
				//LocateRegistry.createRegistry(Integer.parseInt(getConfig("rmi.port")));
				//registry = LocateRegistry.getRegistry(System.getenv("OPENSHIFT_JBOSSEWS_IP"), Integer.parseInt(getConfig("rmi.port")));
				System.out.println("Registry: " + registry);
			} else {
				LocateRegistry.createRegistry(1099);
			}
			if (isOpenShift()) {
//				String localhost = System.getenv("OPENSHIFT_JBOSSEWS_IP") + ":" + getConfig("rmi.port");
				JuegoService juegoManager = new JuegoServiceImpl(21634);
				JugadorService jugadorManager = new JugadorServiceImpl(21635);
				SesionService sessionManager = new SesionServiceImpl(21636);
				RankingService rankingService = new RankingServiceImpl(21637);
				registry.bind(JuegoService.SERVICENAME, juegoManager);
				registry.bind(JugadorService.SERVICENAME, jugadorManager);
				registry.bind(SesionService.SERVICENAME, sessionManager);
				registry.bind(RankingService.SERVICENAME, rankingService);
//				Naming.rebind("//" + localhost + "/" + JuegoService.SERVICENAME, juegoManager);
//				Naming.rebind("//" + localhost + "/" + JugadorService.SERVICENAME, jugadorManager);
//				Naming.rebind("//" + localhost + "/" + SesionService.SERVICENAME, sessionManager);
//				Naming.rebind("//" + localhost + "/" + RankingService.SERVICENAME, rankingService);
			} else {
				JuegoService juegoManager = new JuegoServiceImpl();
				JugadorService jugadorManager = new JugadorServiceImpl();
				SesionService sessionManager = new SesionServiceImpl();
				RankingService rankingService = new RankingServiceImpl();
				Naming.rebind("//localhost/" + JuegoService.SERVICENAME, juegoManager);
				Naming.rebind("//localhost/" + JugadorService.SERVICENAME, jugadorManager);
				Naming.rebind("//localhost/" + SesionService.SERVICENAME, sessionManager);
				Naming.rebind("//localhost/" + RankingService.SERVICENAME, rankingService);
			}
			CartasManager.getManager();
			JuegoManager.getManager();
			JugadorManager.getManager();
			JuegoLogManager.getManager();
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
		} catch (AlreadyBoundException e) {
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
