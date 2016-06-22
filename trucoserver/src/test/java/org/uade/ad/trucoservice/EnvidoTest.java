package org.uade.ad.trucoservice;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.uade.ad.trucoserver.entities.Carta;
import org.uade.ad.trucoserver.entities.EnvidoEnvite;
import org.uade.ad.trucoserver.entities.Jugador;

public class EnvidoTest {
	
	private EnvidoEnvite envido;
	
	@Before
	public void setUp() throws Exception {
		envido = new EnvidoEnvite();
	}

	@Test
	public void testCalcular() {
		Map<Jugador, Set<Carta>> mapa = new HashMap<>();
		Jugador j1 = new Jugador("b", "b", "b");
		Jugador j2 = new Jugador("a", "a", "a");
		Jugador j3 = new Jugador("c", "c", "c");
		Jugador j4 = new Jugador("d", "d", "d");
		
		Set<Carta> c1 = new HashSet<>();
		Set<Carta> c2 = new HashSet<>();
		Set<Carta> c3 = new HashSet<>();
		Set<Carta> c4 = new HashSet<>();
		
		List<Jugador> j = Arrays.asList(j1, j2, j3, j4);
		
		c1.addAll(Arrays.asList(new Carta(7, "oro", 7, 0, 7), new Carta(6, "oro", 6, 0, 6), new Carta(1, "oro", 1, 0, 1)));
		c2.addAll(Arrays.asList(new Carta(50, "copa", 1, 0, 1), new Carta(51, "espada", 1, 0, 1), new Carta(52, "basto", 1, 0, 1)));
		c3.addAll(Arrays.asList(new Carta(2, "basto", 2, 0, 2), new Carta(25, "basto", 3, 0, 3), new Carta(89, "oro", 4, 0, 4)));
		c4.addAll(Arrays.asList(new Carta(12, "oro", 0, 0, 12), new Carta(5, "oro", 5, 0, 5), new Carta(76, "espada", 4, 0, 4)));
		
		mapa.put(j1, c1);
		mapa.put(j2, c2);
		mapa.put(j3, c3);
		mapa.put(j4, c4);
		
		Jugador ganador = envido.calcular(mapa, j, null);
		assertTrue(ganador.equals(j1));
	}
}
