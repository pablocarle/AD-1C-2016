package org.uade.ad.trucoservice;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uade.ad.trucoserver.entities.Carta;
import org.uade.ad.trucoserver.entities.Chico;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;

/**
 */
public class JuegoTest {

	private Chico partidaCerrada;
	
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
		Grupo grupo = new Grupo("GrupoTest: JuegoTest", j1, p1, p2);
		grupo.setIdGrupo(1);
		grupo.setNombre("testGroup");
		List<Jugador> ordenJuego = new ArrayList<>();
		ordenJuego.add(j1);
		ordenJuego.add(j3);
		ordenJuego.add(j2);
		ordenJuego.add(j4);
		
		partidaCerrada = new Chico(ordenJuego);
	}

	@Test
	public void testCantar() {
		fail("Not yet implemented");
	}

	@Test
	public void testJugarCarta() {
//		try {
//			Map<Jugador, Set<Carta>> cartas = partidaCerrada.repartirCartas();
//			Iterator<Carta> j1It = null;
//			Iterator<Carta> j2It = null;
//			Iterator<Carta> j3It = null;
//			Iterator<Carta> j4It = null;
//			Carta cartaJuego = null;
//			boolean trucoCantado = false;
//			
//			while (partidaCerrada.enCurso()) {
//				
//				if (partidaCerrada.manoTerminada()) {
//					System.out.println("Mano terminada");
//					cartas = partidaCerrada.repartirCartas();
//					j1It = null;
//					j2It = null;
//					j3It = null;
//					j4It = null;
//					trucoCantado = false;
//				}
//				
//				for (Map.Entry<Jugador, Set<Carta>> jugadorCartas : cartas.entrySet()) {
//					if (partidaCerrada.manoTerminada()) {
//						System.out.println("Ganadores:" + partidaCerrada.getParejaGanadora().getJugador1().getApodo() + " y " + partidaCerrada.getParejaGanadora().getJugador2().getApodo() );
//						break;
//					}
//					if (j1.equals(jugadorCartas.getKey()) && partidaCerrada.esTurno(j1)) {
//						if (j1It == null) {
//							j1It = jugadorCartas.getValue().iterator();
//						}
//						if (j1It.hasNext()) {
//							cartaJuego = j1It.next();
//							
//							if (trucoCantado == false)
//								trucoCantado = this.truco(cartaJuego, j1);
//							
//							System.out.println("Jugador: " + j1.getApodo() + " juega carta: " + cartaJuego.getNumero() + " de " + cartaJuego.getPalo());
//							partidaCerrada.jugarCarta(j1, cartaJuego);
//						}
//					} else if (j2.equals(jugadorCartas.getKey()) && partidaCerrada.esTurno(j2)) {
//						if (j2It == null) {
//							j2It = jugadorCartas.getValue().iterator();
//						}
//						if (j2It.hasNext()) {
//							cartaJuego = j2It.next();
//							
//							if (trucoCantado == false)
//								trucoCantado = this.truco(cartaJuego, j2);
//							
//							System.out.println("Jugador: " + j2.getApodo() + " juega carta: " + cartaJuego.getNumero() + " de " + cartaJuego.getPalo());
//							partidaCerrada.jugarCarta(j2, cartaJuego);
//						}
//					} else if (j3.equals(jugadorCartas.getKey()) && partidaCerrada.esTurno(j3)) {
//						if (j3It == null) {
//							j3It = jugadorCartas.getValue().iterator();
//						}
//						if (j3It.hasNext()) {
//							cartaJuego = j3It.next();
//							
//							if (trucoCantado == false)
//								trucoCantado = this.truco(cartaJuego, j3);
//							
//							System.out.println("Jugador: " + j3.getApodo() + " juega carta: " + cartaJuego.getNumero() + " de " + cartaJuego.getPalo());
//							partidaCerrada.jugarCarta(j3, cartaJuego);
//						}
//					} else if (j4.equals(jugadorCartas.getKey()) && partidaCerrada.esTurno(j4)) {
//						if (j4It == null) {
//							j4It = jugadorCartas.getValue().iterator();
//						}
//						if (j4It.hasNext()) {
//							cartaJuego = j4It.next();
//							
//							if (trucoCantado == false)
//								trucoCantado = this.truco(cartaJuego, j4);
//							
//							System.out.println("Jugador: " + j4.getApodo() + " juega carta: " + cartaJuego.getNumero() + " de " + cartaJuego.getPalo());
//							partidaCerrada.jugarCarta(j4, cartaJuego);
//						}
//					}
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
	}

	@Test
	public void testGetPuntosObtenidos() {
		fail("Not yet implemented");
	}
	
	public boolean truco(Carta cartaJuego, Jugador j){
		if (cartaJuego.getPalo().equalsIgnoreCase("espada")) {
			if (cartaJuego.getNumero() == 1 || cartaJuego.getNumero() == 2 || cartaJuego.getNumero() == 3 || cartaJuego.getNumero() == 7) {
				System.out.println(j.getApodo() + " Canta: TRUCO");
				return true;
			}
		}
		else if (cartaJuego.getPalo().equalsIgnoreCase("basto")) {
			if (cartaJuego.getNumero() == 1 || cartaJuego.getNumero() == 2 || cartaJuego.getNumero() == 3) {
				System.out.println(j.getApodo() + " Canta: TRUCO");
				return true;
			}
		}
		else if (cartaJuego.getPalo().equalsIgnoreCase("oro")) {
			if (cartaJuego.getNumero() == 2 || cartaJuego.getNumero() == 3 || cartaJuego.getNumero() == 7) {
				System.out.println(j.getApodo() + " Canta: TRUCO");
				return true;
			}
		}
		else if (cartaJuego.getPalo().equalsIgnoreCase("copa")) {
			if (cartaJuego.getNumero() == 2 || cartaJuego.getNumero() == 3) {
				System.out.println(j.getApodo() + " Canta: TRUCO");
				return true;
			}
		}
		return false;
	}
	
//	private void seeIterator(){
//		List<String> cartas = new ArrayList<>();
//		
//		for (int i = 0; i < cartas.size(); i++) {
//			String c = cartas.get(i);
//			System.out.println(c);
//		}
//		
//		for (String s : cartas) {
//			System.out.println(s);
//		}
//		
//		for (Iterator<String> iterator = cartas.iterator(); iterator.hasNext();) {
//			String string = iterator.next();
//			System.out.println();
//		}
//		
//	}
}
