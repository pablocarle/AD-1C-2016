package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucorepo.exceptions.GrupoException;
import org.uade.ad.trucoserver.dao.CategoriaDao;
import org.uade.ad.trucoserver.dao.CategoriaDaoImpl;
import org.uade.ad.trucoserver.dao.GrupoDao;
import org.uade.ad.trucoserver.dao.GrupoDaoImpl;
import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.dao.JugadorDaoImpl;
import org.uade.ad.trucoserver.entities.Categoria;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;


public class JugadorManager {

	private static JugadorManager instancia = null;
	
	private static final List<Categoria> categorias = leerCategorias();
	
	private JugadorDao dao = JugadorDaoImpl.getDAO();
	private GrupoDao gDao = GrupoDaoImpl.getDAO();
	
	private JugadorManager() {
		super();
	}
	
	private static List<Categoria> leerCategorias() {
		CategoriaDao dao = CategoriaDaoImpl.getDAO();
		Transaction tr = dao.getSession().beginTransaction();
		List<Categoria> categorias = dao.getTodos(Categoria.class);
		if (categorias == null || categorias.isEmpty()) {
		}
		Collections.sort(categorias);
		tr.commit();
		return categorias;
	}

	public List<Categoria> getCategoriasOrdenadas() {
		return new ArrayList<>(categorias);
	}
	
	public static JugadorManager getManager() {
		if (instancia == null) {
			instancia = new JugadorManager();
		}
		return instancia;
	}
	
	/**
	 * Registrar un nuevo usuario (jugador) en el sistema
	 * 
	 * @param apodo el apodo del usuario
	 * @param email email del usuario
	 * @param password password del nuevo usuario
	 * @return
	 * @throws Exception Si ya existe un jugador con el apodo proporcionado
	 */
	public Jugador registrarJugador(String apodo, String email, String password) throws Exception {
		Transaction tr = dao.getSession().beginTransaction();
		Jugador existe = dao.getPorApodo(apodo);
		tr.commit();
		if (existe != null) {
			throw new Exception("Ya existe");
		} else {
			Jugador nuevo = new Jugador();
			nuevo.setApodo(apodo);
			nuevo.setNombre(apodo);
			nuevo.setEmail(email);
			nuevo.setPassword(password);
			nuevo.setCategoria(categorias.get(0));
			tr = dao.getSession().beginTransaction();
			dao.guardar(nuevo);
			tr.commit();
			return nuevo;
		}
	}
	
	public boolean esLoginValido(String apodo, String password) {
		Transaction tr = dao.getSession().beginTransaction();
		Jugador j = dao.getPorApodo(apodo);
		tr.commit();
		if (j != null && j.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public Jugador getJugador(String apodo) {
		Transaction tr = dao.getSession().beginTransaction();
		Jugador j = dao.getPorApodo(apodo);
		tr.commit();
		return j; 
	}

	public Grupo crearGrupo(String nombreGrupo, String apodoJugadorAdmin, String[] apodoJugadores) throws GrupoException {
		Transaction tr = gDao.getSession().beginTransaction();
		if (existeGrupo(nombreGrupo)) {
			tr.rollback();
			throw new GrupoException("Ya existe grupo con nombre " + nombreGrupo);
		}
		if (apodoJugadores.length != 3) {
			tr.rollback();
			throw new GrupoException("Se deben informar 3 jugadores integrantes ademas de admin");
		}
		//Busco todos los jugadores
		Jugador admin = dao.getPorApodo(apodoJugadorAdmin);
		Jugador j1 = dao.getPorApodo(apodoJugadores[0]);
		Jugador j2 = dao.getPorApodo(apodoJugadores[1]);
		Jugador j3 = dao.getPorApodo(apodoJugadores[2]);
		StringBuilder message = new StringBuilder();
		if (admin == null)
			message.append("No se encontro jugador con apodo " + apodoJugadorAdmin + "\n");
		if (j1 == null)
			message.append("No se encontro jugador con apodo " + apodoJugadores[0] + "\n");
		if (j2 == null)
			message.append("No se encontro jugador con apodo " + apodoJugadores[1] + "\n");
		if (j3 == null)
			message.append("No se encontro jugador con apodo " + apodoJugadores[2] + "\n");
		
		if (message.length() == 0) {
			Pareja pareja1 = new Pareja(admin, j1);
			Pareja pareja2 = new Pareja(j2, j3);
			Grupo nuevoGrupo = new Grupo(nombreGrupo, admin, pareja1, pareja2);
			gDao.guardar(nuevoGrupo);
			tr.commit();
			return nuevoGrupo;
		} else {
			throw new GrupoException(message.toString());
		}
	}

	private boolean existeGrupo(String nombreGrupo) {
		Grupo g = gDao.getPorNombreGrupo(nombreGrupo);
		if (g == null)
			return false;
		return true;
	}
}
