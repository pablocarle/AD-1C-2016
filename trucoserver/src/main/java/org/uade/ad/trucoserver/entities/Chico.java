package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uade.ad.trucorepo.dtos.ChicoDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.business.CartasManager;
import org.uade.ad.trucoserver.business.ChicoTerminadoObservable;
import org.uade.ad.trucoserver.business.ChicoTerminadoObserver;
import org.uade.ad.trucoserver.business.ManoTerminadaEvent;
import org.uade.ad.trucoserver.business.ManoTerminadaObservable;
import org.uade.ad.trucoserver.business.ManoTerminadaObserver;

/**
 * Implementaciones de esta clase son las formas de juego disponibles.
 * Determinada por eleccion de usuario
 * 
 * @author Grupo9
 *
 *TODO Notificar fin de Chico
 */
@Entity
@Table(name="chicos")
public class Chico implements HasDTO<ChicoDTO>, ManoTerminadaObserver, ManoTerminadaObservable, ChicoTerminadoObservable {
	
	@Id
	private int idChico;
	@OneToMany(mappedBy="chico", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Mano> manos;
	
	@ManyToOne
	@JoinColumn(name="idPartida")
	private Partida partida;

	//Variables para acceso rapido
	@Transient
	private int pareja1Score = 0;
	@Transient
	private int pareja2Score = 0;
	
	@Transient
	private List<Jugador> primerOrdenJuego = new ArrayList<>();
	@Transient
	private List<Jugador> ordenJuegoActual;
	
	@Column
	private Date fechaInicio = Calendar.getInstance().getTime();
	@Column
	private Date fechaFin;
	
	@Transient
	private List<ManoTerminadaObserver> manoTerminadaObservers = new ArrayList<>();
	@Transient
	private List<ChicoTerminadoObserver> chicoTerminadoObservers = new ArrayList<>();
	
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
		this.ordenJuegoActual = new ArrayList<>(primerOrdenJuego);
	}
	
	public Chico(Partida partida, List<Jugador> primerOrdenJuego) {
		super();
		this.partida = partida;
		this.primerOrdenJuego = primerOrdenJuego;
		this.ordenJuegoActual = new ArrayList<>(primerOrdenJuego);
	}

	public void cantar(Jugador j, Envite envite) throws Exception {
		Mano manoActual = getManoActual();
		manoActual.cantar(j, envite);
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
		return null;
	}
	
	public void jugarCarta(Jugador jugador, Carta carta) throws Exception {
		if (!enCurso()) {
			throw new Exception("Juego de carta con chico terminado!!");
		}
		Mano manoActual = getManoActual();
		if (manoActual == null || manoActual.terminada()) {
			throw new Exception("Mano terminada. Debe repartir cartas.");
		} else {
			manoActual.jugar(jugador, carta);
		}
	}

	/**
	 * Modifica el orden de juego por cambio de mano
	 * 
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
			throw new Exception("La mano actual no ha terminado. No se puede modificar el orden de juego");
		}
	}

	/**
	 * Repartir las cartas para la mano actual. Verifica el estado correcto del juego
	 * No se pueden repartir cartas si hay una mano en progreso.
	 * 
	 * @return Mapa K: Jugador V: Set de cartas asignadas
	 * @throws Exception Si no se pueden repartir las cartas por tener mano en progreso
	 */
	public Map<Jugador, Set<Carta>> repartirCartas(Jugador j) throws Exception {
		Map<Jugador, Set<Carta>> cartas = null;
		if (!esTurno(j))
			throw new JuegoException("No es el turno de " + j.getApodo());
		Mano manoActual = getManoActual();
		if (manoActual == null || manoActual.terminada() || !manoActual.tieneMovimientos()) {
			circularOrdenJuego();
			Map<Integer, Set<Carta>> r = CartasManager.getManager().getRandomSets(partida.getParejas().size()*2);
			cartas = new HashMap<>(partida.getParejas().size()*2);
			Jugador jugador = null;
			for (int i = 0; i < r.size(); i++) {
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
			if ((ordenJuegoActual == null || ordenJuegoActual.isEmpty())) {
				ordenJuego = primerOrdenJuego;
			} else {
				ordenJuego = ordenJuegoActual;
			}
			Collections.rotate(ordenJuego, -1);
			Mano mano = new Mano(this, partida.getParejas().get(0), partida.getParejas().get(1), cartas, ordenJuego);
			manos.add(mano);
			mano.agregarObserver(this);
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

	public Pareja getParejaGanadora() {
		//FIXME La pareja ganadora debe ser para el chico
		Mano manoActual = getManoActual();
		if (manoActual != null) {
			return manoActual.getGanador();
		} else {
			return null;
		}
	}
	
	public Pareja getParejaPerdedora() {
		//TODO Pareja perdedora (del chico!)
		return null;
	}
	
	@Override
	public ChicoDTO getDTO() {
		ChicoDTO dto = new ChicoDTO();
		dto.setPuntosPareja1(pareja1Score);
		dto.setPuntosPareja2(pareja2Score);
		return dto;
	}
	
	public ChicoDTO irseAlMazo(Jugador j) throws JuegoException {
		Mano mano = getManoActual();
		if (mano == null) {
			throw new JuegoException("No hay mano en curso. No puede irse al mazo");
		}
		mano.irseAlMazo(j);
		return getDTO();
	}

	public Jugador getTurnoActual() {
		if (ordenJuegoActual == null) {
			return null;
		} else {
			Mano manoActual = getManoActual();
			if (manoActual == null) {
				return ordenJuegoActual.get(0);
			} else {
				return manoActual.getTurnoActual();
			}
		}
	}

	public List<Carta> getCartasDisponibles(Jugador j) {
		List<Carta> retList = new ArrayList<>();
		Mano mano = getManoActual();
		if (mano != null) {
			retList = mano.getCartasDisponibles(j);
		}
		return retList;
	}

	public List<Envite> getEnvidosDisponibles(Jugador j) {
		List<Envite> retList = new ArrayList<>();
		// TODO envidos disponibles para el jugador
		return retList;
	}

	public List<Envite> getTrucosDisponibles(Jugador j) {
		List<Envite> retList = new ArrayList<>();
		// TODO trucos disponibles para el jugador
		return retList;
	}

	public int getIdChico() {
		return idChico;
	}

	public void setIdChico(int idChico) {
		this.idChico = idChico;
	}

	public List<Mano> getManos() {
		return manos;
	}

	public void setManos(List<Mano> manos) {
		this.manos = manos;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public int getPareja1Score() {
		return pareja1Score;
	}

	public void setPareja1Score(int pareja1Score) {
		this.pareja1Score = pareja1Score;
	}

	public int getPareja2Score() {
		return pareja2Score;
	}

	public void setPareja2Score(int pareja2Score) {
		this.pareja2Score = pareja2Score;
	}

	public List<Jugador> getPrimerOrdenJuego() {
		return primerOrdenJuego;
	}

	public void setPrimerOrdenJuego(List<Jugador> primerOrdenJuego) {
		this.primerOrdenJuego = primerOrdenJuego;
	}

	public List<Jugador> getOrdenJuegoActual() {
		return ordenJuegoActual;
	}

	public void setOrdenJuegoActual(List<Jugador> ordenJuegoActual) {
		this.ordenJuegoActual = ordenJuegoActual;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean hayEnvidoEnCurso() {
		Mano mano = getManoActual();
		if (mano != null) {
			return mano.isEnvidoEnCurso();
		}
		return false;
	}

	public boolean hayTrucoEnCurso() {
		Mano mano = getManoActual();
		if (mano != null) {
			return mano.isTrucoEnCurso();
		}
		return false;
	}

	@Override
	public void manoTerminada(ManoTerminadaEvent event) {
		pareja1Score+=event.getPuntosObtenidosPareja1();
		pareja2Score+=event.getPuntosObtenidosPareja2();
		for (ManoTerminadaObserver o : manoTerminadaObservers) {
			o.manoTerminada(event);
		}
	}

	@Override
	public void agregarObserver(ChicoTerminadoObserver observer) {
		if (!chicoTerminadoObservers.contains(observer)) {
			chicoTerminadoObservers.add(observer);
		}
	}

	@Override
	public void eliminarObserver(ChicoTerminadoObserver observer) {
		chicoTerminadoObservers.remove(observer);
	}

	@Override
	public void agregarObserver(ManoTerminadaObserver observer) {
		if (!manoTerminadaObservers.contains(observer)) {
			manoTerminadaObservers.add(observer);
		}
	}

	@Override
	public void eliminarObserver(ManoTerminadaObserver observer) {
		manoTerminadaObservers.remove(observer);
	}
}
