package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucoserver.business.CartasManager;
import org.uade.ad.trucoserver.business.PartidaTerminadaObservable;
import org.uade.ad.trucoserver.business.PartidaTerminadaObserver;

/**
 * Implementaciones de esta clase son las formas de juego disponibles.
 * Determinada por eleccion de usuario
 * 
 * XXX: Imagino que todo lo referido a envites iria en esta clase base, delegando si hace falta
 * 
 * TODO Invocar observers
 * 
 * @author Grupo9
 *
 */
@Entity
@Table(name="chicos")
public class Chico implements PartidaTerminadaObservable, HasDTO<PartidaDTO> {
	
	@Id
	protected int idChico;
	@OneToMany(mappedBy="chico", fetch=FetchType.LAZY)
	protected List<Mano> manos;
	
	@ManyToOne
	@JoinColumn(name="idPartida")
	protected Partida partida;

	//Variables para acceso rapido
	@Transient
	protected int pareja1Score = 0;
	@Transient
	protected int pareja2Score = 0;
	
	@Transient
	private List<Jugador> primerOrdenJuego = new ArrayList<>();
	@Transient
	private List<Jugador> ordenJuegoActual;
	
	/**
	 * Constructor vacio para hibernate (en v5 todavia necesita del constructor vacio sin argumentos?)
	 */
	public Chico() {
		super();
	}
	
	/**
	 * Constructor para nuevo juego
	 * 
	 * @param pareja1
	 * @param pareja2
	 * @param primerOrdenJuego Primer orden del juego, luego se va rotando
	 */
	public Chico(List<Jugador> primerOrdenJuego) {
		super();
		this.primerOrdenJuego = primerOrdenJuego;
	}
	
	public void cantar(String apodo, Envite envite) throws Exception {
		Mano manoActual = getManoActual();
		Jugador jugador = getJugador(apodo);
		manoActual.cantarTruco(jugador, envite);
	}
	
	private Jugador getJugador(String apodo) {
		Pareja pareja1 = partida.getParejas().get(0);
		Pareja pareja2 = partida.getParejas().get(1);
		if (pareja1.getJugador1().getApodo().equals(apodo))
			return pareja1.getJugador1();
		if (pareja1.getJugador2().getApodo().equals(apodo))
			return pareja1.getJugador2();
		if (pareja2.getJugador1().getApodo().equals(apodo))
			return pareja2.getJugador1();
		if (pareja2.getJugador2().getApodo().equals(apodo))
			return pareja2.getJugador2();
		return null;
	}

	/**
	 * Obtener la mano actual o null si no arranco el juego
	 * 
	 * @return
	 * @throws Exception 
	 */
	private Mano getManoActual() {
		if (manos != null && !manos.isEmpty()) {
			return manos.get(manos.size() - 1);
		} else if (manos == null) {
			manos = new ArrayList<>();
		}
		return null; //Dead
	}
	
	public void jugarCarta(Jugador jugador, Carta carta) throws Exception {
		if (!enCurso()) {
			throw new Exception("Juego de carta con partida terminada!!");
		}
		Mano manoActual = getManoActual();
		if (manoActual == null || manoActual.terminada()) {
			circularOrdenJuego();
			throw new Exception("Mano terminada. Debe repartir cartas.");
		} else {
			manoActual.jugar(jugador, carta);
			if (manoActual.terminada() && !manoActual.tieneEnvites()) {
				Pareja ganador = manoActual.getGanador();
				if (partida.getParejas().get(1).equals(ganador)){
					pareja1Score += 1;
				} else {
					pareja2Score += 1;
				}
			}
		}
	}

	/**
	 * Modifica el orden de juego por cambio de mano
	 * @throws Exception 
	 */
	private void circularOrdenJuego() throws Exception {
		if (ordenJuegoActual == null) {
			ordenJuegoActual = new ArrayList<>(primerOrdenJuego);
		}
		Mano manoActual = getManoActual();
		if (manoActual != null && manoActual.terminada()) {
			Collections.rotate(ordenJuegoActual, -1);
		} else if (manoActual != null) {
			throw new Exception("La mano actual no ha terminado. No se puede modificar el orden de juego"); //TODO Definir excepcion
		}
	}

	/**
	 * Repartir las cartas para la mano actual. Verifica el estado correcto del juego
	 * No se pueden repartir cartas si hay una mano en progreso.
	 * 
	 * @return Mapa K: Jugador V: Set de cartas asignadas
	 * @throws Exception Si no se pueden repartir las cartas por tener mano en progreso
	 */
	public Map<Jugador, Set<Carta>> repartirCartas() throws Exception {
		Map<Jugador, Set<Carta>> cartas = null;
		Mano manoActual = getManoActual();
		if (manoActual == null || manoActual.terminada() || !manoActual.tieneMovimientos()) {
			circularOrdenJuego();
			Map<Integer, Set<Carta>> r = CartasManager.getManager().getRandomSets(partida.getParejas().size()*2);
			cartas = new HashMap<>(partida.getParejas().size()*2);
			Jugador jugador = null;
			for (int i = 0; i < r.size(); i++) {
				//FIXME Cambiar esto:
				if (i == 0) {
					jugador = partida.getParejas().get(0).getJugador1();
				} else if (i == 1) {
					jugador = partida.getParejas().get(0).getJugador2();
				} else if (i == 2) {
					jugador = partida.getParejas().get(1).getJugador1();
				} else if (i == 3) {
					jugador = partida.getParejas().get(1).getJugador2();
				}
				cartas.put(jugador, new HashSet<>(r.get(i)));
			}
			
			List<Jugador> ordenJuego = null;
			if ((ordenJuegoActual == null || ordenJuegoActual.isEmpty())){
				ordenJuego = primerOrdenJuego;
			} else {
				ordenJuego = ordenJuegoActual;
			}
			manos.add(new Mano(partida.getParejas().get(0), partida.getParejas().get(1), cartas, ordenJuego));
		}
		
		return cartas == null ? getManoActual().getCartasAsignadas() : cartas;
	}

	/**
	 * Determina si la partida esta en curso
	 * 
	 * @return
	 */
	public boolean enCurso() {
		if (pareja1Score < 30 && pareja2Score < 30)
			return true;
		else {
			System.out.println("pareja1Score: " + pareja1Score + "         pareja2Score: " + pareja2Score);
			return false;
		}
	}

	/**
	 * Determina si es el turno actual del jugador
	 * 
	 * @param jugador el jugador a consultar
	 * @return
	 */
	public boolean esTurno(Jugador jugador) {
		if (ordenJuegoActual == null) {
			return false;
		} else {
			Mano manoActual = getManoActual();
			if (manoActual == null) {
				return ordenJuegoActual.get(0).equals(jugador);
			} else {
				return manoActual.esTurno(jugador);
			}
		}
	}

	/**
	 * Determina si la mano actual finalizo. Sirve para determinar si se debe repartir
	 * cartas nuevamente
	 * 
	 * @return
	 * @throws Exception 
	 */
	public boolean manoTerminada() {
		Mano manoActual = getManoActual();
		if (manoActual == null) {
			return false;
		} else {
			return manoActual.terminada();
		}
	}

	/** Agregue  la verificacion de la pareja ganadora para la mano actual*/
	public Pareja getParejaGanadora() {
		//FIXME La pareja ganadora debe ser para la partida
		Mano manoActual = getManoActual();
		if (manoActual != null) {
			return manoActual.getGanador();
		} else {
			return null;
		}
	}
	
	public List<Envite> getEnvitesDisponibles() {
		/**
		 * 
		 * 
		 */
		return null;
	}
	
	@Override
	public void agregarObserver(PartidaTerminadaObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public Pareja getParejaPerdedora() {
		//TODO Pareja perdedora (de la partida!)
		return null;
	}
	
	@Override
	public PartidaDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PartidaDTO irseAlMazo() {
		// TODO 
		return null;
	}
	
}
