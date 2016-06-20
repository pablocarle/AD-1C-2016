package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.business.EnviteManager;
import org.uade.ad.trucoserver.business.ManoTerminadaEvent;
import org.uade.ad.trucoserver.business.ManoTerminadaObservable;
import org.uade.ad.trucoserver.business.ManoTerminadaObserver;
import org.uade.ad.trucoserver.entities.Baza.BazaResultado;

//TODO Notificar fin de mano, puede terminar en:
/*
 * Jugar una carta
 * No aceptar un envido
 * No aceptar un truco
 * Se fue la pareja completa al mazo (listo)
 * */
@Entity
@Table(name="manos")
public class Mano implements ManoTerminadaObservable {
	
	public static final int BAZAS_MAX_SIZE = 3;
	public static final int NUM_JUGADORES = 4;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMano;
	
	@ManyToOne
	@JoinColumn(name="idChico")
	private Chico chico;
	@Transient
	private Pareja pareja1;
	@Transient
	private Pareja pareja2;
	@OneToMany(mappedBy="mano", cascade=CascadeType.ALL)
	private List<EnvitesManoPareja> envites;
	@OneToMany(mappedBy="mano", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Baza> bazas;
	@Transient
	private List<Jugador> ordenJuegoInicial;
	@Transient
	private List<Jugador> ordenJuegoActual;
	@Transient
	private List<Jugador> ordenJuegoRespuestaEnvite = new ArrayList<>(); //XXX Usar para llevar el orden de respuesta a envite
	@Transient
	private int turnoActualIdx = 0;
	@Transient
	private int turnoJugadorCantoEnvite = 0;
	@Transient
	private Map<Jugador, Set<Carta>> cartasAsignadas = new HashMap<>();
	@Transient
	private List<Jugador> jugadoresEnMazo = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name="idEnviteTruco")
	private Envite enviteTruco;
	@ManyToOne
	@JoinColumn(name="idEnviteEnvido")
	private Envite enviteEnvido;
	
	@Transient
	private boolean envidoEnCurso = false;
	@Transient
	private boolean trucoEnCurso = false;
	@Transient
	private int puntosPareja1 = 0;
	@Transient
	private int puntosPareja2 = 0;
	
	@Transient
	private List<ManoTerminadaObserver> observers = new ArrayList<>();
	
	public Mano() {
		super();
	}
	
	Mano(Chico c, Pareja pareja1, Pareja pareja2, Map<Jugador, Set<Carta>> cartasAsignadas, List<Jugador> ordenJuegoInicial) {
		super();
		this.chico = c;
		this.pareja1 = pareja1;
		this.pareja2 = pareja2;
		this.ordenJuegoInicial = ordenJuegoInicial;
		this.ordenJuegoActual = ordenJuegoInicial;
		this.cartasAsignadas = cartasAsignadas;
	}
	
	Mano(Chico c) {
		super();
		this.chico = c;
	}

	public List<Baza> getBazas() {
		return new ArrayList<>(bazas);
	}
	
	public void jugar(Jugador jugador, Carta carta) throws JuegoException {
		assertCartaValida(jugador, carta);
		if (envidoEnCurso || trucoEnCurso)
			throw new JuegoException("Hay truco o envido en curso. No se puede jugar carta " + envidoEnCurso + ", " + trucoEnCurso);
		if (bazas == null) {
			bazas = new ArrayList<>(BAZAS_MAX_SIZE);
		} else if (bazas.size() == BAZAS_MAX_SIZE && bazas.get(bazas.size() - 1).esCompleta()) {
			throw new RuntimeException("??");
		}
		Baza bazaActual = null;
		if (bazas.isEmpty()) {
			bazaActual = new Baza(this, NUM_JUGADORES, ordenJuegoInicial);
			bazas.add(bazaActual);
			bazaActual.jugarCarta(jugador, carta);
			turnoActualIdx = 1;
		} else {
			Jugador ganadorAnterior = null;
			bazaActual = bazas.get(bazas.size() - 1);
			if (bazaActual.esCompleta() && getGanador() == Pareja.Null) {
				bazaActual = new Baza(this, NUM_JUGADORES, ordenJuegoActual);
				bazaActual.jugarCarta(jugador, carta);
				bazas.add(bazaActual);
				turnoActualIdx++;
			} else if (!bazaActual.esCompleta()) {
				bazaActual.jugarCarta(jugador, carta);
				turnoActualIdx++;
				if (bazaActual.esCompleta()) {
					ganadorAnterior = bazaActual.getResultado().getJugador(); 
					ordenJuegoActual = getNuevoOrdenJuego(ganadorAnterior, ordenJuegoInicial);
					turnoActualIdx = 0;
				}
				if (terminada()) {
					notificarObserversFinMano();
				}
			}
		}
	}
	
	private List<Jugador> getNuevoOrdenJuego(Jugador ganadorAnterior, List<Jugador> ordenJuegoOriginal) {
		List<Jugador> retList = new ArrayList<>(ordenJuegoOriginal.size());
		retList.add(ganadorAnterior);
		int index = ordenJuegoOriginal.indexOf(ganadorAnterior);
		if (index == ordenJuegoOriginal.size() - 1) {
			for (int i = 0; i < ordenJuegoOriginal.size() - 1; i++) {
				retList.add(ordenJuegoOriginal.get(i));
			}
		} else {
			for (int i = index + 1; i < ordenJuegoOriginal.size(); i++) {
				retList.add(ordenJuegoOriginal.get(i));
			}
			for (int i = 0; i < ordenJuegoOriginal.size() && i < index; i++) {
				retList.add(ordenJuegoOriginal.get(i));
			}
		}
		return retList;
	}

	/**
	 * Obtener pareja ganadora de la mano o Pareja.Null si no lo hay aun
	 * 
	 * @return
	 */
	public Pareja getGanador() {
		// Verifico si hay una pareja completa en mazo
		List<Pareja> parejas = chico.getPartida().getParejas();
		Iterator<Pareja> it = parejas.iterator();
		Pareja p = null;
		boolean alMazo = false;
		while (it.hasNext()) {
			p = it.next();
			if (jugadoresEnMazo.contains(p.getJugador1()) && jugadoresEnMazo.contains(p.getJugador2())) {
				//Gano la otra pareja la mano
				it.remove();
				alMazo = true;
			}
		}
		if (alMazo)
			return parejas.get(0);
		if (bazas != null && bazas.size() >= 2) {
			int countPareja1 = 0;
			int countPareja2 = 0;
			boolean todasCompletas = true;
			BazaResultado resultado = null;
			for (Baza baza : bazas) {
				resultado = baza.getResultado();
				if (baza.esCompleta()) {
					if (pareja1.contieneJugador(resultado.getJugador())) {
						countPareja1++;
					} else  if (pareja2.contieneJugador(resultado.getJugador())) {
						countPareja2++;
					}
				} else {
					todasCompletas = false;
					break;
				}
			}
			if (todasCompletas) {
				if (countPareja1 >= 2) {
					return pareja1;
				} else if (countPareja2 >= 2) {
					return pareja2;
				}
			}
		}
		return Pareja.Null;
	}

	private void assertCartaValida(Jugador jugador, Carta carta) {
		if (cartasAsignadas.containsKey(jugador)) {
			if (!cartasAsignadas.get(jugador).contains(carta)) {
				throw new RuntimeException("El jugador " + jugador + " no tiene asignada la carta " + carta);
			} else {
				return;
			}
		}
		throw new RuntimeException("El jugador " + jugador + " no pertenece a la partida");
	}
	
	private List<Envite> getEnvidosCantados() {
		List<Envite> retList = new ArrayList<>();
		if (envites != null) {
			for (EnvitesManoPareja e : envites) {
				if (e.isEnvido()) {
					retList.add(e.getEnvite());
				}
			}
		}
		return retList;
	}
	
	@SuppressWarnings("unused")
	private List<Envite> getTrucosCantados() {
		List<Envite> retList = new ArrayList<>();
		if (envites != null) {
			for (EnvitesManoPareja e : envites) {
				if (e.isTruco()) {
					retList.add(e.getEnvite());
				}
			}
		}
		return retList;
	}
	
	private EnvitesManoPareja getUltimoEnvidoCantado() {
		if (envites != null) {
			for (int i = envites.size() - 1; i >= 0; i--) {
				if (envites.get(i).isEnvido()) {
					return envites.get(i);
				}
			}
		}
		return null;
	}
	
	private EnvitesManoPareja getUltimoTrucoCantado() {
		if (envites != null) {
			for (int i = envites.size() - 1; i >= 0; i--) {
				if (envites.get(i).isTruco()) {
					return envites.get(i);
				}
			}
		}
		return null;
	}
	
	public List<Envite> getEnvidosDisponibles(Jugador j) {
		List<Envite> retList = new ArrayList<>();
		if(esTurno(j) && bazas != null && !bazas.isEmpty() && bazas.size() == 1) {
			List<Envite> cantados = getEnvidosCantados();
			if (cantados.isEmpty()) {
				retList.addAll(EnviteManager.getManager().getEnvidos());
			} else {
				EnvitesManoPareja ultimoEnvido = getUltimoEnvidoCantado();
				if (!ultimoEnvido.getPareja().contieneJugador(j)) {
					retList.addAll(EnviteManager.getManager().getEnvitesPosteriores(ultimoEnvido.getEnvite()));
				}
			}
		}
		return retList;
	}
	
	public List<Envite> getTrucosDisponibles(Jugador j) {
		List<Envite> retList = new ArrayList<>();
		if (esTurno(j) && !envidoEnCurso) {
			if (trucoEnCurso) {
				EnvitesManoPareja ultimoTruco = getUltimoTrucoCantado();
				if (!ultimoTruco.getPareja().contieneJugador(j)) {
					retList.addAll(EnviteManager.getManager().getEnvitesPosteriores(ultimoTruco.getEnvite()));
				}
			} else {
				retList.addAll(EnviteManager.getManager().getTrucos());
			}
		}
		return retList;
	}

	// TODO Terminar cantar envites. 
	public void cantar(Jugador jugador, Envite envite) {
		if (envite instanceof EnvidoEnvite) {
			cantarEnvido(jugador, (EnvidoEnvite) envite);
		} else if (envite instanceof TrucoEnvite) {
			cantarTruco(jugador, (TrucoEnvite) envite);
		} else {
			throw new RuntimeException("No se identifica que hacer con envite " + envite);
		}
		// TODO Usar trucoEnCurso y envidoEnCurso (validar que no haya un canto anterior, etc)
		Pareja parejaEnvite = null;
	
		if(pareja1.contieneJugador(jugador))
			parejaEnvite = pareja1;
		else if (pareja2.contieneJugador(jugador))
			parejaEnvite=pareja2;
		if(esTurno(jugador)){
//			if(envite instanceof EnvidoEnvite){
//				this.idEnviteEnvido=envite.getIdTipoEnvite();
//			}
//			if(envite instanceof TrucoEnvite){
//				this.idEnviteTruco=envite.getIdTipoEnvite();
//			}
			
		if(envite.getIdTipoEnvite()>14){
			boolean booleano=false;
			if(envite.getNombreEnvite().contains("_Querido")){
				booleano=true;
			}
			EnvitesManoPareja env = new EnvitesManoPareja(envite,this,booleano,envite.getPuntaje(),parejaEnvite);
		}
			
		}
		
	}

	private void cantarTruco(Jugador jugador, TrucoEnvite envite) {
		if (trucoEnCurso) {
			
		} else {
			
		}
	}

	private void cantarEnvido(Jugador jugador, EnvidoEnvite envite) {
		if (envidoEnCurso) {
			
		} else {
			
		}
	}

	public boolean terminada() {
		return getGanador() != Pareja.Null;
	}

	Map<Jugador, Set<Carta>> getCartasAsignadas() {
		return new HashMap<>(cartasAsignadas);
	}

	/**
	 * Determina si en la mano se ha jugado alguna carta
	 * 
	 * @return
	 */
	public boolean tieneMovimientos() {
		return bazas == null || bazas.isEmpty();
	}

	/**
	 * Determina si es el turno del jugador
	 * 
	 * @param jugador
	 * @return
	 */
	public boolean esTurno(Jugador jugador) {
		return getTurnoActual().equals(jugador);
	}
	
	public Jugador getTurnoActual() {
		if (turnoActualIdx >= ordenJuegoActual.size()) {
			turnoActualIdx = 0;
		}
		return ordenJuegoActual.get(turnoActualIdx);
	}

	public boolean tieneEnvites() {
		return envites != null && !envites.isEmpty();
	}
	
	public void irseAlMazo(Jugador jugador) throws JuegoException {
		if (getGanador() != Pareja.Null)
			throw new JuegoException("La mano ya terminó, no puede irse al mazo!");
		Pareja p = chico.getPartida().getPareja(jugador);
		if (envidoEnCurso || trucoEnCurso) 
			throw new JuegoException("Hay truco / envido en curso!");
		if (p == Pareja.Null)
			throw new JuegoException("No hay pareja");
		
		if (!jugadoresEnMazo.contains(p.getJugador1()) && !jugadoresEnMazo.contains(p.getJugador2())) {
			jugadoresEnMazo.add(jugador);
		} else {
			// El otro ya estaba en el mazo
			jugadoresEnMazo.add(jugador);
			if (p.equals(pareja1)) {
				
			} else if (p.equals(pareja2)) {
				
			}
			//TODO Sumar puntos acumulados
			notificarObserversFinMano();
		}
	}

	private void notificarObserversFinMano() throws JuegoException {
		for (ManoTerminadaObserver o : observers) {
			o.manoTerminada(new ManoTerminadaEvent(this.chico.getPartida().idPartida, this, puntosPareja1, puntosPareja2));
		}
	}

	public List<Carta> getCartasDisponibles(Jugador j) {
		Set<Carta> cartasAsignadas = new HashSet<>(getCartasAsignadas().get(j));
		if (bazas != null) {
			Carta jugada = null;
			for (Baza b : bazas) {
				jugada = b.getCartaJugada(j);
				if (jugada != null) {
					cartasAsignadas.remove(jugada);
				}
			}
		}
		return new ArrayList<>(cartasAsignadas);
	}

	@Override
	public void agregarObserver(ManoTerminadaObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	@Override
	public void eliminarObserver(ManoTerminadaObserver observer) {
		observers.remove(observer);
	}

	public boolean isEnvidoEnCurso() {
		return envidoEnCurso;
	}

	public boolean isTrucoEnCurso() {
		return trucoEnCurso;
	}
	
	public int getIdMano() {
		return idMano;
	}
}
