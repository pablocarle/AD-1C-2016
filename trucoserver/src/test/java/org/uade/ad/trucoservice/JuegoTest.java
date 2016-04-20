package org.uade.ad.trucoservice;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.PartidaCerrada;

public class JuegoTest {

	private Partida partidaCerrada;
	private Partida partidaAbierta;
	
	private Jugador j1;
	private Jugador j2;
	private Jugador j3;
	private Jugador j4;	
	
	private Pareja p1;
	private Pareja p2;
	
	@Before
	public void setUp() throws Exception {
		j1 = new Jugador("1", "j1", "");
		j2 = new Jugador("2", "j2", "");
		j3 = new Jugador("3", "j3", "");
		j4 = new Jugador("4", "j4", "");
		p1 = new Pareja(j1, j2);
		p2 = new Pareja(j3, j4);
		Grupo grupo = new Grupo(p1, p2);
		grupo.setIdGrupo(1);
		grupo.setNombre("testGroup");
		List<Jugador> ordenJuego = new ArrayList<>();
		ordenJuego.add(j1);
		ordenJuego.add(j3);
		ordenJuego.add(j2);
		ordenJuego.add(j4);
		
		partidaCerrada = new PartidaCerrada(grupo, ordenJuego);
	}

	@Test
	public void testCantar() {
		fail("Not yet implemented");
	}

	@Test
	public void testJugarCarta() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPuntosObtenidos() {
		fail("Not yet implemented");
	}
}
