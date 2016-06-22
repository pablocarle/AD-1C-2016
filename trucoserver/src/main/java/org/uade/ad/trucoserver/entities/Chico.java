package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.uade.ad.trucoserver.business.ChicoTerminadoEvent;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((partida == null) ? 0 : partida.hashCode());
		result = prime * result + ((primerOrdenJuego == null) ? 0 : primerOrdenJuego.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chico other = (Chico) obj;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (partida == null) {
			if (other.partida != null)
				return false;
		} else if (!partida.equals(other.partida))
			return false;
		if (primerOrdenJuego == null) {
			if (other.primerOrdenJuego != null)
				return false;
		} else if (!primerOrdenJuego.equals(other.primerOrdenJuego))
			return false;
		return true;
	}

	public void cantar(Jugador j, Envite envite) throws JuegoException {
		Mano manoActual = getManoActual();
		if (manoActual != null) {
			manoActual.cantar(j, envite);
		} else {
			throw new JuegoException("No hay mano en curso");
		}
	}
	
	/**
	 * Obtener la mano actual o null si no arranco el juego
	 * 
	 * @return
	 * @throws Exception 
	 */
	private Mano getManoActual() {
		if (manos != null && !manos.isEmpty()) {
			if (!manos.get(manos.size() - 1).terminada()) {
				return manos.get(manos.size() - 1);
			}
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
		if (manoActual == null && manos != null && manos.size() > 0) {
			System.out.println("Orden juego actual pre rotate: " + Arrays.toString(ordenJuegoActual.toArray()));
			Collections.rotate(ordenJuegoActual, -1);
			System.out.println("Orden juego actual post rotate: " + Arrays.toString(ordenJuegoActual.toArray()));
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
			Mano mano = new Mano(this, partida.getParejas().get(0), partida.getParejas().get(1), cartas, new ArrayList<>(ordenJuego));
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
	 * @throws JuegoException 
	 * @throws Exception 
	 */
	public boolean manoTerminada() throws JuegoException {
		Mano manoActual = getManoActual();
		if (manoActual == null) {
			throw new JuegoException("No hay mano en curso!");
		} else {
			return manoActual.terminada();
		}
	}

	public Pareja getParejaGanadora() {
		if (!enCurso()) {
			if (pareja1Score > pareja2Score) {
				return  partida.getParejas().get(0);
			} else {
				return partida.getParejas().get(1);
			}
		} else {
			return Pareja.Null;
		}
	}
	
	public Pareja getParejaPerdedora() {
		if (!enCurso()) {
			if (pareja1Score > pareja2Score) {
				return  partida.getParejas().get(1);
			} else {
				return partida.getParejas().get(0);
			}
		} else {
			return Pareja.Null;
		}
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
		Mano mano = getManoActual();
		if (mano != null) {
			return mano.getEnvidosDisponibles(j);
		}
		return retList;
	}

	public List<Envite> getTrucosDisponibles(Jugador j) {
		List<Envite> retList = new ArrayList<>();
		Mano mano = getManoActual();
		if (mano != null) {
			return mano.getTrucosDisponibles(j);
		}
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
	public void manoTerminada(ManoTerminadaEvent event) throws JuegoException {
		System.out.println("Chico notificado de Mano terminada");
		pareja1Score+=event.getPuntosObtenidosPareja1();
		pareja2Score+=event.getPuntosObtenidosPareja2();
		try {
			circularOrdenJuego();
		} catch (Exception e) {
			throw new JuegoException(e);
		}
		for (ManoTerminadaObserver o : manoTerminadaObservers) {
			o.manoTerminada(event);
		}
		if (!enCurso()) {
			for (ChicoTerminadoObserver o : chicoTerminadoObservers) {
				o.chicoTerminado(new ChicoTerminadoEvent(this, getParejaGanadora()));
			}
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
		if (!this.equals(observer)) {
			if (!manoTerminadaObservers.contains(observer)) {
				manoTerminadaObservers.add(observer);
			}
		}
	}

	@Override
	public void eliminarObserver(ManoTerminadaObserver observer) {
		manoTerminadaObservers.remove(observer);
	}
}
