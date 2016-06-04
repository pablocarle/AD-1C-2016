package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.uade.ad.trucorepo.dtos.ChicoDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.trucoserver.DTOUtil;
import org.uade.ad.trucoserver.business.PartidaTerminadaObservable;
import org.uade.ad.trucoserver.business.PartidaTerminadaObserver;

@Entity
@Table(name="partidas")
@Inheritance(strategy=InheritanceType.JOINED)
public class Partida implements HasDTO<PartidaDTO>, PartidaTerminadaObservable {

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
	@OneToMany(mappedBy="partida")
	protected List<Chico> chicos;
	@ManyToOne
	@JoinColumn(name="idTipoPartida")
	protected TipoPartida tipoPartida;
	@Transient
	private List<PartidaTerminadaObserver> observers = new ArrayList<>();
	
	private Partida(int idPartida) {
		super();
		this.idPartida = idPartida;
	}

	public Partida() {
		super();
	}

	public List<Envite> getEnvitesDisponibles(Jugador jugador) throws JuegoException {
		return null;
	}
	
	public int getPuntosObtenidos(Jugador j) {
		// TODO Obtener los puntos segun que pareja gano la partida (gano 2 chicos)
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
			} else {
				synchronized(this) {
					chicos = new ArrayList<>();
					chicos.add(new Chico());
				}
				return chicos.get(0);
			}
			throw new JuegoException("No se encontro chico en curso");
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
		// TODO Auto-generated method stub
		PartidaDTO dto = new PartidaDTO();
		dto.setIdPartida(idPartida);
		dto.setChicos(DTOUtil.getDTOs(chicos, ChicoDTO.class));
		if (parejas != null && !parejas.isEmpty()) {
			if (parejas.size() == 1) {
				dto.setPareja1(parejas.get(0).getDTO());
			} else {
				dto.setPareja1(parejas.get(0).getDTO());
				dto.setPareja2(parejas.get(1).getDTO());
			}
		}
		dto.setTipoPartida(tipoPartida.getDTO());
		Chico chicoActual = null;
		try {
			chicoActual = getChicoActual();
		} catch (JuegoException e) {
			e.printStackTrace();
		}
		if (chicoActual != null) {
			dto.setTurnoActual(chicoActual.getTurnoActual().getDTO());
			dto.setTurnoActualCartasDisponibles(DTOUtil.getDTOs(chicoActual.getCartasDisponibles(chicoActual.getTurnoActual()), CartaDTO.class));
			dto.setTurnoActualEnvidos(DTOUtil.getDTOs(chicoActual.getEnvidosDisponibles(chicoActual.getTurnoActual()), EnviteDTO.class));
			dto.setTurnoActualTrucos(DTOUtil.getDTOs(chicoActual.getTrucosDisponibles(chicoActual.getTurnoActual()), EnviteDTO.class));
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
		this.observers.add(observer);
	}

	public void cantarEnvite(Jugador j, Envite e) throws Exception {
		getChicoActual().cantar(j, e);
	}
}
