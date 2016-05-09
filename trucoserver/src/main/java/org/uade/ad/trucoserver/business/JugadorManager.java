package org.uade.ad.trucoserver.business;

import java.util.Collections;
import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucoserver.dao.CategoriaDao;
import org.uade.ad.trucoserver.dao.CategoriaDaoImpl;
import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.dao.JugadorDaoImpl;
import org.uade.ad.trucoserver.entities.Categoria;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;


public class JugadorManager {

	private static JugadorManager instancia = null;
	
	private static final List<Categoria> categorias = leerCategorias();
	
	private JugadorDao dao = JugadorDaoImpl.getDAO();
	
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
	
	/**
	 * TODO definir argumentos. Parejas?
	 * 
	 * @param nombre
	 * @param idJugadores
	 * @return
	 * @throws Exception
	 */
	public Grupo crearGrupo(String nombre, int[] idJugadores) throws Exception {
		//Debo obtener todos los jugadores con esos ids
		
		return null;
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
}
