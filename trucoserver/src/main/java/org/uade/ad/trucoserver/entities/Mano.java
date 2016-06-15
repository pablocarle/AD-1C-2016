package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.business.ManoTerminadaObservable;
import org.uade.ad.trucoserver.business.ManoTerminadaObserver;
import org.uade.ad.trucoserver.entities.Baza.BazaResultado;

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
	@OneToMany(mappedBy="mano")
	private List<EnvitesManoPareja> envites;
	@OneToMany(mappedBy="mano", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Baza> bazas;
	@Transient
	private List<Jugador> ordenJuegoInicial;
	@Transient
	private List<Jugador> ordenJuegoActual;
	@Transient
	private int turnoActualIdx = 0;
	@Transient
	private Map<Jugador, Set<Carta>> cartasAsignadas = new HashMap<>();
	@Transient
	private List<Jugador> jugadoresEnMazo;
	@Column
	private int idEnviteTruco = -1;
	@Column
	private int idEnviteEnvido = -1;
	
	@Transient
	private boolean envidoEnCurso = false;
	@Transient
	private boolean trucoEnCurso = false;
	
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
		this.idEnviteEnvido=-1;
		this.idEnviteTruco=-1;
	}
	
	Mano(Chico c) {
		super();
		this.chico = c;
	}
	
	public List<Baza> getBazas() {
		return new ArrayList<>(bazas);
	}
	
	public void jugar(Jugador jugador, Carta carta) {
		assertCartaValida(jugador, carta);
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
	
	
	//XXX Ver aclaración en obtenerEnvitesDispo
	public List<Envite> getEnvidosDisponibles(Jugador j){
		if(esTurno(j)){
			if(this.idEnviteTruco==-1){
				List<Envite> envitetotales = JuegoManager.getManager().getEnvites();
				List<Envite> envitesDisponibles = this.obtenerEnvitesDispo(envitetotales, this.idEnviteEnvido);
				List<Envite> posiblesEnvidos = null;  
				for (Envite env: envitesDisponibles){
					if(env instanceof EnvidoEnvite)
						posiblesEnvidos.add(env);
				}
				if(!posiblesEnvidos.isEmpty())
					return posiblesEnvidos;
				else 
					if(posiblesEnvidos.isEmpty())
						throw new RuntimeException("No hay envidos disponibles para cantar");
					else
						throw new RuntimeException("Hay un error en envidos disponibles");
			}
			else
				throw new RuntimeException("No se puede cantar envido luego del truco");
		}
		else
			throw new RuntimeException("No es el turno del jugador");
	}
	
	//XXX Ver aclaración en obtenerEnvitesDispo
	public List<Envite> getTrucosDisponibles(Jugador j){
		if(esTurno(j)){
			//FIXME Ojo llamar managers que inician transacciones desde aca (donde seguramente ya 
			//nos encontremos en una transaccion), no hay soporte de transacciones anidadas.
			//Buscar otra forma de darle a la mano los envites disponibles (puede ser una unica vez
			//ya que no cambia durante el ciclo de vida de la aplicacion)
			List<Envite> envitetotales = JuegoManager.getManager().getEnvites();
			List<Envite> envitesDisponibles = this.obtenerEnvitesDispo(envitetotales, this.idEnviteTruco);
			List<Envite> posiblesTruco = null;  
			for (Envite truco: envitesDisponibles){
				if(truco instanceof TrucoEnvite)
					posiblesTruco.add(truco);
			}
			if(!posiblesTruco.isEmpty())
				return posiblesTruco;
			else
				throw new RuntimeException("No trucos disponibles para cantar");
		}else
			throw new RuntimeException("No es el turno del jugador");
	}

	@SuppressWarnings("null")
	//XXX: 2 cositas aca: proxEnvites es null y no cambia.
	/*
	 * Lo otro es recomendacion: Cuando se devuelve una coleccion debería esperarse 1 de 3
	 * resultados:
	 * 1) La coleccion vuelve y tiene elementos.
	 * 2) La coleccion vuelve y no tiene elemnetos (lista vacia)
	 * 3) Se arroja una excepcion (el estado no era el correcto, no se levanto la config, etc)
	 * 
	 * Lo aclaro porque devolver null es un nullpointerexception en potencia.
	 * */
	private List<Envite> obtenerEnvitesDispo(List<Envite> envitesTot, int EnvitePrevio){
		List<Envite> proxEnvites=null;
		for(Envite env: envitesTot){
			if(env.getEnviteAnterior()==EnvitePrevio){
				proxEnvites.add(env);
			}
		}
		if(!proxEnvites.isEmpty())
			return proxEnvites;
		else 
			if (proxEnvites.isEmpty())
				throw new RuntimeException("no hay envites disponibles");
			else 
				throw new RuntimeException("Error en la funcion obtenerEnvitesdispo");
		
	}

	
	
	// TODO Terminar cantar envites. 
	public void cantar(Jugador jugador, Envite envite) {
		
		Pareja parejaEnvite = null;
	
		if(pareja1.contieneJugador(jugador))
			parejaEnvite = pareja1;
		else if (pareja2.contieneJugador(jugador))
			parejaEnvite=pareja2;
		if(esTurno(jugador)){
			if(envite instanceof EnvidoEnvite){
				this.idEnviteEnvido=envite.getIdTipoEnvite();
			}
			if(envite instanceof TrucoEnvite){
				this.idEnviteTruco=envite.getIdTipoEnvite();
			}
			
		if(envite.getIdTipoEnvite()>14){
			boolean booleano=false;
			if(envite.getNombreEnvite().contains("_Querido")){
				booleano=true;
			}
			EnvitesManoPareja env = new EnvitesManoPareja(envite,this,booleano,envite.getPuntaje(),parejaEnvite);
		}
			
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
		if (turnoActualIdx >= ordenJuegoActual.size()) {
			turnoActualIdx = 0;
		}
		return ordenJuegoActual.get(turnoActualIdx).equals(jugador);
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
	
	public void irseAlMazo(Jugador jugador) {
		//TODO Completar irse al mazo
		List<Jugador> j = this.getJugadoresEnMazo();
		for (int i = 0; i < j.size(); i++) {
			// Si el jugador no está en la Lista, lo agrego para irse al mazo
			if (j.get(i).getIdJugador() != jugador.getIdJugador()) {
				j.add(jugador);
			} else {
				// Terminar la mano y dar por ganado al contrario
			}
		}
	}

	public List<Jugador> getJugadoresEnMazo() {
		return jugadoresEnMazo;
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
}
