package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.dao.JugadorDaoImpl;
import org.uade.ad.trucoserver.entities.Jugador;


public class JugadorManager {

	private static JugadorManager instancia = null;
	
	private JugadorDao dao = JugadorDaoImpl.getDAO();
	
	private JugadorManager() {
		super();
	}
	
	public static JugadorManager getManager() {
		if (instancia == null) {
			instancia = new JugadorManager();
		}
		return instancia;
	}
	
	public Jugador registrarJugador(String apodo, String email, String password) throws Exception {
		Jugador existe = dao.getPorApodo(apodo);
		if (existe == null) {
			throw new Exception("Ya existe");
		} else {
			Jugador nuevo = new Jugador();
			nuevo.setApodo(apodo);
			nuevo.setEmail(email);
			nuevo.setPassword(password);
			dao.guardar(nuevo);
			return nuevo;
		}
	}

	public boolean login(String apodo, String password) {
		return false;
	}
	
	public boolean isValidLogin(String apodo, String password) {
		
		return false;
	}

	public Jugador getJugador(String apodo) {
		System.out.println("A punto de buscar el jugador por apodo en la base de datos.");
		Jugador j=dao.getPorApodo(apodo);
		System.out.println("Apodo del jugador leido en la bd: "+j.getApodo());
		return j; 
	}
}
