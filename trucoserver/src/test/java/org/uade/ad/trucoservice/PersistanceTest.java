package org.uade.ad.trucoservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uade.ad.trucoserver.dao.CategoriaDaoImpl;
import org.uade.ad.trucoserver.dao.GrupoDao;
import org.uade.ad.trucoserver.dao.GrupoDaoImpl;
import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.dao.JugadorDaoImpl;
import org.uade.ad.trucoserver.dao.ParejaDao;
import org.uade.ad.trucoserver.dao.ParejaDaoImpl;
import org.uade.ad.trucoserver.dao.PartidaDao;
import org.uade.ad.trucoserver.dao.PartidaDaoImpl;
import org.uade.ad.trucoserver.entities.Categoria;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.PartidaCerrada;
import org.uade.ad.trucoserver.entities.PartidaLibreIndividual;
import org.uade.ad.trucoserver.entities.PartidaLibrePareja;

/**
 * Pruebas de persistencia
 * 
 * @author Grupo7
 *
 */
public class PersistanceTest {

	private Jugador j1;
	private Jugador j2;
	private Jugador j3;
	private Jugador j4;
	
	private Pareja p1;
	private Pareja p2;
	
	private Partida partidaCerrada;
	private Partida partidaAbiertaIndividual;
	private Partida partidaAbiertaPareja;
	
	private Grupo grupo;
	
	private JugadorDao jDao;
	private PartidaDao pDao;
	private ParejaDao parejaDao;
	private GrupoDao grupoDao;
	
	private List<Categoria> categorias;
	
	@Before
	public void setUp() throws Exception {
		jDao = JugadorDaoImpl.getDAO();
		pDao = PartidaDaoImpl.getDAO();
		parejaDao = ParejaDaoImpl.getDAO();
		grupoDao = GrupoDaoImpl.getDAO();
		
		Transaction tr = CategoriaDaoImpl.getDAO().getSession().beginTransaction();
		categorias = CategoriaDaoImpl.getDAO().getTodos(Categoria.class);
		tr.commit();
		
		Collections.sort(categorias);
		
		j1 = new Jugador("j1@j1.com", "j1", "");
		j2 = new Jugador("j2@j2.com", "j2", "");
		j3 = new Jugador("j3@j3.com", "j3", "");
		j4 = new Jugador("j4@j4.com", "j4", "");
		
		j1.setCategoria(categorias.get(0));
		j2.setCategoria(categorias.get(0));
		j3.setCategoria(categorias.get(0));
		j4.setCategoria(categorias.get(0));
		
		p1 = new Pareja(j1, j2);
		p2 = new Pareja(j3, j4);
		
		grupo = new Grupo("grupoTest", j1, p1, p2);
		List<Jugador> ordenJuego = new ArrayList<>();
		ordenJuego.add(j1);
		ordenJuego.add(j3);
		ordenJuego.add(j2);
		ordenJuego.add(j4);
		
		partidaCerrada = new PartidaCerrada(grupo, ordenJuego);
		partidaAbiertaIndividual = new PartidaLibreIndividual(p1, p2, ordenJuego);
		partidaAbiertaPareja = new PartidaLibrePareja(p1, p2, ordenJuego);
	}

	@After
	public void tearDown() {
		deleteAll();
	}
	
	/**
	 * Eliminaciones en bd
	 */
	private void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testPersistJugador() {
		Transaction tr = null;
		try {
			tr = jDao.getSession().beginTransaction();
			jDao.guardar(j1);
			assertTrue(j1.getIdJugador() > 0);
			jDao.guardar(j2);
			assertTrue(j2.getIdJugador() > 0);
			jDao.guardar(j3);
			assertTrue(j3.getIdJugador() > 0);
			jDao.guardar(j4);
			assertTrue(j4.getIdJugador() > 0);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			tr.rollback();
		}
		
		//Damos la baja
		try {
			tr = jDao.getSession().beginTransaction();
			jDao.eliminar(j1);
			jDao.eliminar(j2);
			jDao.eliminar(j3);
			jDao.eliminar(j4);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			tr.rollback();
		}
	}
	
	@Test
	public void testPersistParejas() {
		Transaction tr = null;
		try {
			tr = parejaDao.getSession().beginTransaction();
			jDao.guardar(j1);
			assertTrue(j1.getIdJugador() > 0);
			jDao.guardar(j2);
			assertTrue(j2.getIdJugador() > 0);
			jDao.guardar(j3);
			assertTrue(j3.getIdJugador() > 0);
			jDao.guardar(j4);
			assertTrue(j4.getIdJugador() > 0);
			
			parejaDao.guardar(p1);
			assertTrue(p1.getIdPareja() > 0);
			parejaDao.guardar(p2);
			assertTrue(p2.getIdPareja() > 0);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			tr.rollback();
		}
		
		try {
			tr = parejaDao.getSession().beginTransaction();
			parejaDao.eliminar(p1);
			parejaDao.eliminar(p2);
			
			jDao.eliminar(j1);
			jDao.eliminar(j2);
			jDao.eliminar(j3);
			jDao.eliminar(j4);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			tr.rollback();
		}
	}
	
	@Test
	public void testPersistGrupo() {
		Transaction tr = null;
		try {
			tr = parejaDao.getSession().beginTransaction();
			jDao.guardar(j1);
			assertTrue(j1.getIdJugador() > 0);
			jDao.guardar(j2);
			assertTrue(j2.getIdJugador() > 0);
			jDao.guardar(j3);
			assertTrue(j3.getIdJugador() > 0);
			jDao.guardar(j4);
			assertTrue(j4.getIdJugador() > 0);

			parejaDao.guardar(p1);
			assertTrue(p1.getIdPareja() > 0);
			parejaDao.guardar(p2);
			assertTrue(p2.getIdPareja() > 0);
			
			grupoDao.guardar(grupo);
			assertTrue(grupo.getIdGrupo() > 0);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			tr.rollback();
		}
		
		try {
			tr = parejaDao.getSession().beginTransaction();
			grupoDao.eliminar(grupo);
			
			parejaDao.eliminar(p1);
			parejaDao.eliminar(p2);
			
			jDao.eliminar(j1);
			jDao.eliminar(j2);
			jDao.eliminar(j3);
			jDao.eliminar(j4);
			
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			tr.rollback();
		}
	}
}
