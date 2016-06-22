package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.CartasDisponiblesDTO;
import org.uade.ad.trucorepo.dtos.ChicoDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.DTOUtil;
import org.uade.ad.trucoserver.business.ChicoTerminadoEvent;
import org.uade.ad.trucoserver.business.ChicoTerminadoObservable;
import org.uade.ad.trucoserver.business.ChicoTerminadoObserver;
import org.uade.ad.trucoserver.business.JuegoManager;
import org.uade.ad.trucoserver.business.ManoTerminadaEvent;
import org.uade.ad.trucoserver.business.ManoTerminadaObservable;
import org.uade.ad.trucoserver.business.ManoTerminadaObserver;
import org.uade.ad.trucoserver.business.OnEnvidoEvaluado;
import org.uade.ad.trucoserver.business.OnEnvidoQuerido;
import org.uade.ad.trucoserver.business.PartidaTerminadaEvent;
import org.uade.ad.trucoserver.business.PartidaTerminadaObservable;
import org.uade.ad.trucoserver.business.PartidaTerminadaObserver;

@Entity
@Table(name="partidas")
@Inheritance(strategy=InheritanceType.JOINED)
public class Partida implements HasDTO<PartidaDTO>, PartidaTerminadaObservable, ChicoTerminadoObserver, ManoTerminadaObserver, ChicoTerminadoObservable, ManoTerminadaObservable {

	public static final transient Partida Null = new Partida(-1);
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int idPartida;
	@Column(nullable=false)
	protected Date fechaInicio;
	@Column(nullable=true)
	protected Date fechaFin;
	@ManyToMany
	@JoinTable(name="partidas_parejas", 
				joinColumns=@JoinColumn(name="idPartida", referencedColumnName="idPartida"),
				inverseJoinColumns=@JoinColumn(name="idPareja", referencedColumnName="idPareja"))
	protected List<Pareja> parejas;
	@OneToMany(mappedBy="partida", cascade=CascadeType.ALL)
	protected List<Chico> chicos;
	@ManyToOne
	@JoinColumn(name="idTipoPartida")
	protected TipoPartida tipoPartida;
	@Transient
	private List<PartidaTerminadaObserver> partidaTerminadaObservers = new ArrayList<>();
	@Transient
	private List<ChicoTerminadoObserver> chicoTerminadoObservers = new ArrayList<>();
	@Transient
	private List<ManoTerminadaObserver> manoTerminadaObservers = new ArrayList<>();
	
	
	private Partida(int idPartida) {
		super();
		this.idPartida = idPartida;
	}

	public Partida() {
		super();
	}
	
	public Partida(List<Pareja> parejas) {
		super();
		this.parejas = parejas;
		this.chicos = new ArrayList<>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((parejas == null) ? 0 : parejas.hashCode());
		result = prime * result + ((tipoPartida == null) ? 0 : tipoPartida.hashCode());
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
		Partida other = (Partida) obj;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (parejas == null) {
			if (other.parejas != null)
				return false;
		} else if (!parejas.equals(other.parejas))
			return false;
		if (tipoPartida == null) {
			if (other.tipoPartida != null)
				return false;
		} else if (!tipoPartida.equals(other.tipoPartida))
			return false;
		return true;
	}

	public int getPuntosObtenidos(Jugador j) {
		List<Jugador> ganadores = getGanadores();
		if (ganadores.contains(j)) {
			return tipoPartida.getPuntosVictoria();
		}
		return 0;
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
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

	public List<Pareja> getParejas() {
		return parejas;
	}

	public void setParejas(List<Pareja> parejas) {
		this.parejas = parejas;
	}

	public List<Chico> getChicos() {
		return chicos;
	}

	public void setChicos(List<Chico> chicos) {
		this.chicos = chicos;
	}

	public TipoPartida getTipoPartida() {
		return tipoPartida;
	}

	public void setTipoPartida(TipoPartida tipoPartida) {
		this.tipoPartida = tipoPartida;
	}

	public Chico getChicoActual() throws JuegoException {
		//El chico actual es el chico que este en curso o ninguno si ya hay ganador
		if (!partidaTerminada()) {
			if (chicos != null) {
				for (Chico c : chicos) {
					if (c.enCurso()) {
						return c;
					}
				}
				//Si llega aca, no hay chico en curso, crear nuevo (no termino la partida tampoco)
				Chico nuevoChico = new Chico(this, JuegoManager.getManager().sortOrden(parejas));
				chicos.add(nuevoChico);
				nuevoChico.agregarObserver((ChicoTerminadoObserver)this);
				nuevoChico.agregarObserver((ManoTerminadaObserver)this);
				return nuevoChico;
			} else {
				synchronized(this) {
					chicos = new ArrayList<>();
					Chico nuevoChico = new Chico(this, JuegoManager.getManager().sortOrden(parejas));
					nuevoChico.agregarObserver((ChicoTerminadoObserver)this);
					nuevoChico.agregarObserver((ManoTerminadaObserver)this);
					chicos.add(nuevoChico);
				}
				return chicos.get(0);
			}
		} else{
			throw new JuegoException("La partida esta terminada");
		}
	}

	private boolean partidaTerminada() {
		//Si hay 3 chicos y hay ganador en 2 o bien hay 2 chicos y es el mismo ganador en los 2
		if (chicos == null || chicos.size() < 2) {
			return false;
		} else {
			if (chicos.size() == 2) {
				return chicos.get(0).getParejaGanadora().equals(chicos.get(1).getParejaGanadora()) 
						&& !chicos.get(0).getParejaGanadora().esNull() && !chicos.get(1).getParejaGanadora().esNull();
			} else {
				boolean all = true;
				for (Chico c : chicos) {
					if (c.enCurso())
						all = false;
				}
				return all;
			}
		}
	}

	@Override
	public PartidaDTO getDTO() {
		PartidaDTO dto = new PartidaDTO();
		dto.setIdPartida(idPartida);
		dto.setChicos(DTOUtil.getDTOs(chicos, ChicoDTO.class));
		if (this.partidaTerminada()) {
			dto.setEstado("terminada");
		}
		if (parejas != null && !parejas.isEmpty()) {
			if (parejas.size() == 1) {
				dto.setPareja1(parejas.get(0).getDTO());
			} else {
				dto.setPareja1(parejas.get(0).getDTO());
				dto.setPareja2(parejas.get(1).getDTO());
			}
		}
		if (!dto.esNull()) {
			dto.setTipoPartida(tipoPartida.getDTO());
			Chico chicoActual = null;
			try {
				chicoActual = getChicoActual();
			} catch (JuegoException e) {
				e.printStackTrace();
			}
			if (chicoActual != null) {
				Jugador turnoActual = chicoActual.getTurnoActual();
				if (turnoActual != null) {
					dto.setTurnoActual(turnoActual.getDTO());
					dto.setTurnoActualCartasDisponibles(DTOUtil.getDTOs(chicoActual.getCartasDisponibles(turnoActual), CartaDTO.class));
					dto.setTurnoActualEnvidos(DTOUtil.getDTOs(chicoActual.getEnvidosDisponibles(turnoActual), EnviteDTO.class));
					dto.setTurnoActualTrucos(DTOUtil.getDTOs(chicoActual.getTrucosDisponibles(turnoActual), EnviteDTO.class));
				}
				List<CartasDisponiblesDTO> cartasDisponibles = new ArrayList<>();
				List<CartaDTO> jCartasDisponibles;
				List<Jugador> jugadores = getJugadores();
				for (int i = 0; i < jugadores.size(); i++) {
					jCartasDisponibles = DTOUtil.getDTOs(chicoActual.getCartasDisponibles(jugadores.get(i)), CartaDTO.class);
					cartasDisponibles.add(new CartasDisponiblesDTO(jugadores.get(i).getDTO(), jCartasDisponibles));
				}
				dto.setCartasDisponibles(cartasDisponibles);
				dto.setEnvidoEnCurso(chicoActual.hayEnvidoEnCurso());
				dto.setTrucoEnCurso(chicoActual.hayTrucoEnCurso());
			}
		}
		return dto;
	}

	public boolean contieneJugador(int idJugador) {
		if (parejas != null) {
			for (Pareja p : parejas) {
				if (p.contieneJugador(idJugador)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean contieneJugador(String apodoJugador) {
		if (parejas != null) {
			for (Pareja p : parejas) {
				if (p.contieneJugador(apodoJugador)) {
					return true;
				}
			}
		}
		return false;
	}

	public void jugarCarta(Jugador j, Carta c) throws Exception {
		getChicoActual().jugarCarta(j, c);
	}

	public void irAlMazo(Jugador j) throws JuegoException {
		getChicoActual().irseAlMazo(j);
	}

	@Override
	public void agregarObserver(PartidaTerminadaObserver observer) {
		if (!this.equals(observer)) {
			if (!partidaTerminadaObservers.contains(observer)) {
				this.partidaTerminadaObservers.add(observer);
			}
		}
	}

	public void cantarEnvite(Jugador j, Envite e, OnEnvidoEvaluado onEnvidoEvaluado, OnEnvidoQuerido onEnvidoQuerido) throws Exception {
		getChicoActual().cantar(j, e, onEnvidoEvaluado, onEnvidoQuerido);
	}

	public void agregarChico(Chico primerChico) {
		if (chicos == null) {
			chicos = new ArrayList<>();
		}
		primerChico.setPartida(this);
		chicos.add(primerChico);
	}

	public List<Jugador> getJugadores() {
		List<Jugador> retList = new ArrayList<>();
		if (parejas != null) {
			for (Pareja p : parejas) {
				retList.addAll(p.getJugadores());
			}
		}
		return retList;
	}

	public void repartirCartas(Jugador j) throws JuegoException, Exception {
		getChicoActual().repartirCartas(j);
	}

	public Jugador getTurnoActual() throws JuegoException {
		return getChicoActual().getTurnoActual();
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

	@Override
	public void agregarObserver(ChicoTerminadoObserver observer) {
		if (!this.equals(observer)) {
			if (!chicoTerminadoObservers.contains(observer)) {
				chicoTerminadoObservers.add(observer);
			}
		}
	}

	@Override
	public void eliminarObserver(ChicoTerminadoObserver observer) {
		chicoTerminadoObservers.remove(observer);
	}

	@Override
	public void manoTerminada(ManoTerminadaEvent event) throws JuegoException {
		System.out.println("Partida notificada de fin de Mano");
		for (ManoTerminadaObserver o : manoTerminadaObservers) {
			o.manoTerminada(event);
		}
	}

	@Override
	public void chicoTerminado(ChicoTerminadoEvent event) throws JuegoException {
		System.out.println("Partida notificada de fin de Chico");
		for (ChicoTerminadoObserver o : chicoTerminadoObservers) {
			o.chicoTerminado(event);
		}
		if (partidaTerminada()) {
			for (PartidaTerminadaObserver o : partidaTerminadaObservers) {
				o.finPartida(new PartidaTerminadaEvent(this, getGanadores().toArray(new Jugador[0]), getPerdedores().toArray(new Jugador[0])));
			}
		}
	}

	public List<Jugador> getGanadores() {
		List<Jugador> retList = new ArrayList<>();
		if (partidaTerminada()) {
			int count1 = 0;
			int count2 = 0;
			for (Chico c : chicos) {
				if (c.getParejaGanadora().equals(parejas.get(0))) {
					count1++;
				} else {
					count2++;
				}
			}
			if (count1 > count2) {
				return parejas.get(0).getJugadores();
			} else {
				return parejas.get(1).getJugadores();
			}
		}
		return retList;
	}

	public List<Jugador> getPerdedores() {
		List<Jugador> retList = new ArrayList<>();
		if (partidaTerminada()) {
			int count1 = 0;
			int count2 = 0;
			for (Chico c : chicos) {
				if (c.getParejaPerdedora().equals(parejas.get(0))) {
					count1++;
				} else {
					count2++;
				}
			}
			if (count1 > count2) {
				return parejas.get(0).getJugadores();
			} else {
				return parejas.get(1).getJugadores();
			}
		}
		return retList;
	}

	public Pareja getPareja(Jugador jugador) {
		if (parejas != null) {
			for (Pareja p : parejas) {
				if (p.contieneJugador(jugador))
					return p;
			}
		}
		return Pareja.Null;
	}
}
