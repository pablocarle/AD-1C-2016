package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.Context;
import org.uade.ad.trucoserver.dao.GrupoDao;
import org.uade.ad.trucoserver.dao.GrupoDaoImpl;
import org.uade.ad.trucoserver.dao.PartidaDao;
import org.uade.ad.trucoserver.dao.PartidaDaoImpl;
import org.uade.ad.trucoserver.dao.TipoPartidaDao;
import org.uade.ad.trucoserver.dao.TipoPartidaDaoImpl;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.PartidaCerrada;
import org.uade.ad.trucoserver.entities.TipoPartida;

public class JuegoManager {

	private static final String PARTIDA_ABIERTA_INDIVIDUAL = "Abierta Individual";
	private static final String PARTIDA_ABIERTA_PAREJA = "Abierta en Pareja";
	private static final String PARTIDA_CERRADA = "Cerrada";
	
	private static JuegoManager instance = null;
	
	public static JuegoManager getManager() {
		if (instance == null) {
			instance = new JuegoManager();
		}
		return instance;
	}
	
	private GrupoDao gDao = GrupoDaoImpl.getDAO();
	private PartidaDao pDao = PartidaDaoImpl.getDAO();
	
	private List<TipoPartida> tiposPartidas;
	{
		TipoPartidaDao dao = TipoPartidaDaoImpl.getDao();
		Transaction tr = dao.getSession().beginTransaction();
		tiposPartidas = dao.getTodos(TipoPartida.class);
		tr.commit();
	}
	
	private JuegoManager() {
		super();
	}

	public Partida crearPartidaCerrada(String nombreGrupo, int idGrupo, Context juegoContext) throws JuegoException {
		Transaction tr = gDao.getSession().beginTransaction();
		Grupo grupo = gDao.getPorId(Grupo.class, idGrupo);
		if (grupo == null) {
			tr.rollback();
			throw new JuegoException("No se encontro grupo con nombre " + nombreGrupo);
		}
		//Hay que enviar las notificaciones a los otro usuarios para que acepten ingresar al juego
		Partida partida = null;
		try {
			partida = new PartidaCerrada(grupo);
			pDao.guardar(partida);
			juegoContext.agregarInvitaciones(partida.getIdPartida(), grupo.getJugadoresNoAdmin());
			tr.commit();
		} catch (Exception e) {
			System.out.println("transaction rollback");
			tr.rollback();
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

	public Partida crearPartidaAbiertaIndividual(String apodo, Context juegoContext) {
		Partida partida = new Partida();
		partida.setFechaInicio(Calendar.getInstance().getTime());
		partida.setTipoPartida(getTipoPartida(PARTIDA_ABIERTA_INDIVIDUAL));
		Transaction tr = pDao.getSession().beginTransaction();
		pDao.guardar(partida);
		tr.commit();
		//Avisa a contexto para que envie las notificaciones
		return partida;
	}

	private TipoPartida getTipoPartida(String nombreTipoPartida) {
		if (tiposPartidas != null && !tiposPartidas.isEmpty()) {
			for (TipoPartida tp : tiposPartidas) {
				if (tp.getNombre().equals(nombreTipoPartida)) {
					return tp;
				}
			}
			throw new RuntimeException("No se encontro tipo de partida " + nombreTipoPartida);
		} else {
			throw new RuntimeException("No hay tipos de partidas definidos");
		}
	}
}