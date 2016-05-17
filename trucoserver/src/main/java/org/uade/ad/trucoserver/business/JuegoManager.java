package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.Context;
import org.uade.ad.trucoserver.dao.GrupoDao;
import org.uade.ad.trucoserver.dao.GrupoDaoImpl;
import org.uade.ad.trucoserver.dao.PartidaDao;
import org.uade.ad.trucoserver.dao.PartidaDaoImpl;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.PartidaCerrada;

public class JuegoManager {

	private static JuegoManager instance = null;
	
	public static JuegoManager getManager() {
		if (instance == null) {
			instance = new JuegoManager();
		}
		return instance;
	}
	
	private GrupoDao gDao = GrupoDaoImpl.getDAO();
	private PartidaDao pDao = PartidaDaoImpl.getDAO();
	
	private JuegoManager() {
		super();
	}

	public Partida crearPartidaCerrada(String nombreGrupo, Context juegoContext) throws JuegoException {
		Transaction tr = gDao.getSession().beginTransaction();
		Grupo grupo = gDao.getPorNombreGrupo(nombreGrupo);
		if (grupo == null) {
			tr.rollback();
			throw new JuegoException("No se encontro grupo con nombre " + nombreGrupo);
		}
		//Hay que enviar las notificaciones a los otro usuarios para que acepten ingresar al juego
		Partida partida = null;
		try {
			partida = new PartidaCerrada(grupo, getPrimerOrdenJuego(grupo.getParejaNum(0), grupo.getParejaNum(1)));
			pDao.guardar(partida);
			juegoContext.agregarInvitaciones(partida.getIdPartida(), grupo.getJugadoresNoAdmin());
			tr.commit();
		} catch (Exception e) {
			throw new JuegoException(e);
		}
		return partida;
	}
	
	private List<Jugador> getPrimerOrdenJuego(Pareja pareja1, Pareja pareja2) {
		List<Jugador> retList = new ArrayList<>(4);
		retList.add(pareja1.getJugador1());
		retList.add(pareja2.getJugador1());
		retList.add(pareja1.getJugador2());
		retList.add(pareja2.getJugador2());
		return retList;
	}
}
