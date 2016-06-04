package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.Context;
import org.uade.ad.trucoserver.dao.CartaDao;
import org.uade.ad.trucoserver.dao.CartaDaoImpl;
import org.uade.ad.trucoserver.dao.EnviteDao;
import org.uade.ad.trucoserver.dao.EnviteDaoImpl;
import org.uade.ad.trucoserver.dao.GrupoDao;
import org.uade.ad.trucoserver.dao.GrupoDaoImpl;
import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.dao.JugadorDaoImpl;
import org.uade.ad.trucoserver.dao.ParejaDao;
import org.uade.ad.trucoserver.dao.ParejaDaoImpl;
import org.uade.ad.trucoserver.dao.PartidaDao;
import org.uade.ad.trucoserver.dao.PartidaDaoImpl;
import org.uade.ad.trucoserver.dao.TipoPartidaDao;
import org.uade.ad.trucoserver.dao.TipoPartidaDaoImpl;
import org.uade.ad.trucoserver.entities.Carta;
import org.uade.ad.trucoserver.entities.Chico;
import org.uade.ad.trucoserver.entities.Envite;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.PartidaCerrada;
import org.uade.ad.trucoserver.entities.TipoPartida;

public class JuegoManager {

	public static final String PARTIDA_ABIERTA_INDIVIDUAL = "Abierta Individual";
	public static final String PARTIDA_ABIERTA_PAREJA = "Abierta en Pareja";
	public static final String PARTIDA_CERRADA = "Cerrada";
	
	private static JuegoManager instance = null;
	
	public static JuegoManager getManager() {
		if (instance == null) {
			instance = new JuegoManager();
		}
		return instance;
	}
	
	private GrupoDao gDao = GrupoDaoImpl.getDAO();
	private ParejaDao parejaDao = ParejaDaoImpl.getDAO();
	private PartidaDao pDao = PartidaDaoImpl.getDAO();
	private JugadorDao jDao = JugadorDaoImpl.getDAO();
	private CartaDao cDao = CartaDaoImpl.getDAO();
	private EnviteDao eDao = EnviteDaoImpl.getDAO();
	
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
			Chico primerChico = new Chico(sortOrden(partida.getParejas()));
			partida.agregarChico(primerChico);
			pDao.guardar(partida);
			tr.commit();
			juegoContext.agregarInvitaciones(partida.getIdPartida(), grupo.getJugadoresNoAdmin());
			juegoContext.agregarJuego(partida);
		} catch (Exception e) {
			System.out.println("transaction rollback");
			tr.rollback();
			throw new JuegoException(e);
		}
		return partida;
	}
	
	private List<Jugador> sortOrden(List<Pareja> parejas) {
		List<Jugador> retList = new ArrayList<>(parejas.size() * 2);
		List<Jugador> listaAux = new ArrayList<>();
		Collections.shuffle(parejas);
		List<Jugador> jugadoresAux = null;
		for (int i = 0; i < parejas.size(); i++) {
			jugadoresAux = parejas.get(i).getJugadores();
			Collections.shuffle(jugadoresAux);
			listaAux.addAll(jugadoresAux);
		}
		for (int i = 0; i < parejas.size(); i++) {
			retList.add(listaAux.get(i++));
			retList.add(listaAux.get(i));
		}
		return retList;
	}

	public Partida crearPartidaAbiertaIndividual(String apodo, Context juegoContext) throws JuegoException {
		//Avisa a contexto para que envie las notificaciones
		//En realidad en esta etapa no hay creacion de partida
		Transaction tr = pDao.getSession().beginTransaction();
		Jugador jugador = jDao.getPorApodo(apodo);
		if (jugador != null) {
			Partida partida = juegoContext.matchearPartidaAbierta(jugador);
			if (partida == null) {
				tr.rollback();
				//No se logro conseguir match. Va a cola.
				juegoContext.agregarJugadorAColaPartidaAbierta(jugador);
				return Partida.Null;
			} else {
				Chico primerChico = new Chico(sortOrden(partida.getParejas()));
				partida.agregarChico(primerChico);
				guardarPartida(partida);
				for (Pareja p : partida.getParejas()) {
					//Agrega para notificar a jugadores de inclusion en la partida nueva
					if (!p.getJugador1().equals(jugador)) {
						juegoContext.agregarInvitacion(partida, p.getJugador1());
					}
					if (!p.getJugador2().equals(jugador)) {
						juegoContext.agregarInvitacion(partida, p.getJugador2());
					}
				}
				tr.commit();
				juegoContext.agregarJuego(partida);
				return partida;
			}
		} else {
			tr.rollback();
			throw new JuegoException("No se encontro el jugador con apodo " + apodo);
		}
	}
	
	private void guardarPartida(Partida partida) {
		if (!(partida instanceof PartidaCerrada)) {
			//Si es una partida cerrada las parejas y grupo ya existen
			List<Pareja> parejas = partida.getParejas();
			Pareja parejaDb = null;
			for (Pareja pareja : parejas) {
				parejaDb = parejaDao.getParejaNoGrupo(pareja.getJugador1().getIdJugador(), pareja.getJugador2().getIdJugador());
				if (parejaDb == null) {
					parejaDao.guardar(pareja);
				} else {
					pareja.setIdPareja(parejaDb.getIdPareja());
				}
			}
		}
		pDao.guardar(partida);
	}

	public Partida crearPartidaAbiertaPareja(String apodo, int idPareja, Context juegoContext) throws JuegoException {
		Transaction tr = pDao.getSession().beginTransaction();
		Jugador jugador = jDao.getPorApodo(apodo);
		Pareja pareja = parejaDao.getPorId(Pareja.class, idPareja);
		if (jugador != null && pareja != null && pareja.contieneJugador(jugador)) {
			Partida partida = juegoContext.matchearPartidaAbiertaPareja(jugador, pareja);
			if (partida == null) {
				tr.rollback();
				//No se logro conseguir match. Va a cola
				juegoContext.agregarParejaAColaPartidaAbierta(pareja);
				return Partida.Null;
			} else {
				guardarPartida(partida);
				for (Pareja p : partida.getParejas()) {
					if (!p.getJugador1().equals(jugador)) {
						juegoContext.agregarInvitacion(partida, p.getJugador1());
					}
					if (!p.getJugador2().equals(jugador)) {
						juegoContext.agregarInvitacion(partida, p.getJugador2());
					}
				}
				tr.commit();
				return partida;
			}
		} else{
			tr.rollback();
			throw new JuegoException();
		}
	}

	public TipoPartida getTipoPartida(String nombreTipoPartida) {
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
	private EnviteDao dao = EnviteDaoImpl.getDAO();
	
	public List<Envite> getEnvites()
	{
		List<Envite> envites;
		Transaction tr = dao.getSession().beginTransaction();
		envites = dao.getTodos(Envite.class);
		tr.commit();
		return envites;
	}
	
	public Partida jugarCarta(int idJuego, String apodo, int idCarta, Context context) throws JuegoException {
		Partida p = context.getPartida(idJuego);
		if (p == null){
			throw new JuegoException("No existe partida en curso con id " + idJuego);
		}
		Transaction tr = jDao.getSession().beginTransaction();
		Jugador j = jDao.getPorApodo(apodo);
		Carta c = cDao.getPorId(Carta.class, idCarta);
		try {
			p.jugarCarta(j, c);
		} catch (Exception e) {
			tr.rollback();
			throw new JuegoException(e);
		}
		tr.commit();
		context.actualizarPartida(p);
		return p;
	}

	public Partida irAlMazo(int idPartida, String apodo, Context context) throws JuegoException {
		Partida p = context.getPartida(idPartida);
		if (p == null) {
			throw new JuegoException("No existe partida en curso con id " + idPartida);
		}
		Transaction tr = jDao.getSession().beginTransaction();
		Jugador j = jDao.getPorApodo(apodo);
		p.irAlMazo(j);
		context.actualizarPartida(p);
		tr.commit();
		return p;
	}

	public Partida cantarEnvite(int idJuego, String apodo, int idEnvite, Context context) throws JuegoException {
		Partida p = context.getPartida(idJuego);
		if (p == null) {
			throw new JuegoException("No existe partida en curso con id " + idJuego);
		}
		Transaction tr = jDao.getSession().beginTransaction();
		Jugador j = jDao.getPorApodo(apodo);
		Envite e = eDao.getPorId(Envite.class, idEnvite);
		try {
			p.cantarEnvite(j, e);
		} catch (Exception e1) {
			throw new JuegoException(e1);
		}
		context.actualizarPartida(p);
		tr.commit();
		return p;
	}
}
