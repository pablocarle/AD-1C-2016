package org.uade.ad.trucoserver.business;

import java.util.Calendar;

import org.hibernate.Transaction;
import org.uade.ad.trucoserver.dao.Juego_LogDao;
import org.uade.ad.trucoserver.dao.Juego_LogDaoImpl;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.LogJuego;

public class JuegoLogManager implements PartidaTerminadaObserver {

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

	@Override
	public void finPartida(PartidaTerminadaEvent partida) throws Exception {
		//Actualizar log
		Transaction tr = dao.getSession().beginTransaction();
		LogJuego entity = null;
		for (Jugador ganador : partida.getGanadores()) {
			entity = new LogJuego();
			entity.setFecha(Calendar.getInstance().getTime());
			entity.setJugador(ganador);
			entity.setPuntos(partida.getPartida().getPuntosObtenidos(ganador));
			entity.setVictoria(true);
			dao.guardar(entity);
		}
		for (Jugador perdedor : partida.getPerdedores()) {
			entity = new LogJuego();
			entity.setFecha(Calendar.getInstance().getTime());
			entity.setJugador(perdedor);
			entity.setPuntos(partida.getPartida().getPuntosObtenidos(perdedor));
			entity.setVictoria(false);
			dao.guardar(entity);
		}
		tr.commit();
	}
}
