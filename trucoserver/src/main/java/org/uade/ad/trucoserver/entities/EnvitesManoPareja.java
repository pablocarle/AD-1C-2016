package org.uade.ad.trucoserver.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="envites_pareja_mano")
public class EnvitesManoPareja {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEnviteParejaMano;
	
	@OneToOne
	@JoinColumn(name="idTipoEnvite")
	private Envite envite;
	@ManyToOne
	@JoinColumn(name="idMano")
	private Mano mano;
	@Column
	private boolean aceptado;
	@Column
	private int puntosObtenidos;
	@OneToOne
	@JoinColumn(name="idPareja")
	private Pareja pareja;
	
	public EnvitesManoPareja() {
		super();

	} 
		
	public EnvitesManoPareja(int idEnviteParejaMano, Envite envite, Mano mano, boolean aceptado, int puntosObtenidos,
			Pareja pareja) {
		super();
		this.idEnviteParejaMano = idEnviteParejaMano;
		this.envite = envite;
		this.mano = mano;
		this.aceptado = aceptado;
		this.puntosObtenidos = puntosObtenidos;
		this.pareja = pareja;
	}

	public int getIdEnviteParejaMano() {
		return idEnviteParejaMano;
	}

	public void setIdEnviteParejaMano(int idEnviteParejaMano) {
		this.idEnviteParejaMano = idEnviteParejaMano;
	}

	public Envite getEnvite() {
		return envite;
	}

	public void setEnvite(Envite envite) {
		this.envite = envite;
	}

	public Mano getMano() {
		return mano;
	}

	public void setMano(Mano mano) {
		this.mano = mano;
	}

	public int getPuntosObtenidos() {
		return puntosObtenidos;
	}

	public void setPuntosObtenidos(int puntosObtenidos) {
		this.puntosObtenidos = puntosObtenidos;
	}

	public Pareja getPareja() {
		return pareja;
	}

	public void setPareja(Pareja pareja) {
		this.pareja = pareja;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	} 
}
