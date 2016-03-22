package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.entities.Jugador;

public class JugadorManager {

	private static JugadorManager instancia = null;
	
	private JugadorDao dao;
	
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
			dao.guardar(nuevo);
			return nuevo;
		}
	}
	
}
