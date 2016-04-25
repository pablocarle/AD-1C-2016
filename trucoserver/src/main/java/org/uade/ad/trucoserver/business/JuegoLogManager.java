package org.uade.ad.trucoserver.business;

import java.util.Date;

import org.uade.ad.trucoserver.dao.Juego_LogDao;
import org.uade.ad.trucoserver.dao.Juego_LogDaoImpl;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.LogJuego;

public class JuegoLogManager {

	private static JuegoLogManager instancia = null;
	
	//private JugadorDao dao = JugadorDaoImpl.getDAO();
	private Juego_LogDao dao = Juego_LogDaoImpl.getDAO();
	
	private JuegoLogManager() {
		super();
	}
	
	public static JuegoLogManager getManager() {
		if (instancia == null) {
			instancia = new JuegoLogManager();
		}
		return instancia;
	}

	public LogJuego registrarJuego_Log(Jugador j,String apodo, String email, boolean victoria, int puntos) throws Exception {
		//JuegoLog existe = dao.getPorApodo(apodo);//No hace falta buscarlo.
		//if (existe == null) {
		//	throw new Exception("Ya existe");
		//} else {
		
			LogJuego nuevo = new LogJuego();
			nuevo.setFecha(new Date());
			nuevo.setJugador(j);
			nuevo.setPuntos(puntos);
			nuevo.setVictoria(victoria);
			dao.guardar(nuevo);
			return nuevo;
		//}
	}

	public boolean login(String apodo, String password) {
		return false;
	}
	
	public boolean isValidLogin(String apodo, String password) {
		
		return false;
	}

	public Jugador getJugador(String apodo) {
		return dao.getPorApodo(apodo);
	}
}
