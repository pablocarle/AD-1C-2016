package org.uade.ad.trucoserver.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.uade.ad.trucorepo.dtos.PartidaDTO;

@Entity
@Table(name="partidas")
@Inheritance(strategy=InheritanceType.JOINED)
public class Partida implements HasDTO<PartidaDTO> {

	@Id
	protected int idPartida;
	@Column
	protected Date fechaInicio;
	@Column
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
	
	
	public Partida() {
		super();
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

	public Chico getChicoActual() {
		// TODO get chico actual
		return null;
	}

	@Override
	public PartidaDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}
}
