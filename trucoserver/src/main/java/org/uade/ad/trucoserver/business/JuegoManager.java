package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.Context;
import org.uade.ad.trucoserver.dao.CartaDao;
import org.uade.ad.trucoserver.dao.CartaDaoImpl;
import org.uade.ad.trucoserver.dao.ChicoDao;
import org.uade.ad.trucoserver.dao.ChicoDaoImpl;
import org.uade.ad.trucoserver.dao.EnviteDao;
import org.uade.ad.trucoserver.dao.EnviteDaoImpl;
import org.uade.ad.trucoserver.dao.GrupoDao;
import org.uade.ad.trucoserver.dao.GrupoDaoImpl;
import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.dao.JugadorDaoImpl;
import org.uade.ad.trucoserver.dao.ManoDao;
import org.uade.ad.trucoserver.dao.ManoDaoImpl;
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
import org.uade.ad.trucoserver.entities.Mano;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.PartidaCerrada;
import org.uade.ad.trucoserver.entities.TipoPartida;

public class JuegoManager implements ChicoTerminadoObserver, ManoTerminadaObserver {

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
	private ChicoDao chicoDao = ChicoDaoImpl.getDAO();
	private ManoDao manoDao = ManoDaoImpl.getDAO();
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
			partida.agregarObserver((ChicoTerminadoObserver)this);
			partida.agregarObserver((ManoTerminadaObserver)this);
			juegoContext.agregarInvitaciones(partida.getIdPartida(), grupo.getJugadoresNoAdmin());
			juegoContext.agregarJuego(partida);
			juegoContext.agregarNotificacion("Turno de " + partida.getTurnoActual().getApodo(), partida.getIdPartida());
		} catch (Exception e) {
			System.out.println("transaction rollback");
			tr.rollback();
			throw new JuegoException(e);
		}
		return partida;
	}
	
	public List<Jugador> sortOrden(List<Pareja> parejas) {
		List<Jugador> retList = new ArrayList<>(parejas.size() * 2);
		List<Jugador> listaAux = new ArrayList<>();
		Collections.shuffle(parejas);
		List<Jugador> jugadoresAux = null;
		for (int i = 0; i < parejas.size(); i++) {
			jugadoresAux = parejas.get(i).getJugadores();
			Collections.shuffle(jugadoresAux);
			listaAux.addAll(jugadoresAux);
		}
		int jugadorIdx = 0;
		for (int i = 0; i < parejas.size(); i++) {
			jugadorIdx = i;
			retList.add(listaAux.get(jugadorIdx));
			jugadorIdx+=2;
			retList.add(listaAux.get(jugadorIdx));
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
				partida.agregarObserver((ChicoTerminadoObserver)this);
				partida.agregarObserver((ManoTerminadaObserver)this);
				juegoContext.agregarJuego(partida);
				juegoContext.agregarNotificacion("Turno de " + partida.getTurnoActual().getApodo(), partida.getIdPartida());
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
				partida.agregarObserver((ChicoTerminadoObserver)this);
				partida.agregarObserver((ManoTerminadaObserver)this);
				juegoContext.agregarJuego(partida);
				juegoContext.agregarNotificacion("Turno de " + partida.getTurnoActual().getApodo(), partida.getIdPartida());
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
		context.agregarNotificacion(apodo + " jugó carta " + CartasManager.getManager().getCarta(idCarta), idJuego);
		context.agregarNotificacion("Turno de " + p.getTurnoActual(), idJuego);
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
		context.agregarNotificacion(apodo + " se fue al mazo", idPartida);
		context.agregarNotificacion("Turno de " + p.getTurnoActual(), idPartida);
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
		context.agregarNotificacion(apodo + " cantó " + e.getNombreEnvite(), idJuego);
		context.agregarNotificacion("Turno de " + p.getTurnoActual(), idJuego);
		return p;
	}

	public PartidaDTO repartirCartas(int idJuego, String apodo, Context context) throws JuegoException {
		Partida p = context.getPartida(idJuego);
		if (p == null) {
			throw new JuegoException("No existe partida en curso con id " + idJuego);
		}
		Transaction tr = jDao.getSession().beginTransaction();
		Jugador j = jDao.getPorApodo(apodo);
		try {
			p.repartirCartas(j);
		} catch (Exception e) {
			throw new JuegoException(e);
		}
		context.agregarNotificacion("Jugador " + apodo + " repartio cartas", idJuego);
		context.agregarNotificacion("Turno de " + p.getTurnoActual().getApodo(), p.getIdPartida());
		context.actualizarPartida(p);
		try {
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p.getDTO();
	}

	@Override
	public void manoTerminada(ManoTerminadaEvent event) throws JuegoException {
		//Guardar en base de datos
		Mano mano = event.getMano();
		if (mano.getIdMano() <= 0) {
			manoDao.guardar(mano);
		}
	}

	@Override
	public void chicoTerminado(ChicoTerminadoEvent event) throws JuegoException {
		Chico chico = event.getChico();
		if (chico.getIdChico() <= 0){
			chicoDao.guardar(chico);
		}
	}
}
