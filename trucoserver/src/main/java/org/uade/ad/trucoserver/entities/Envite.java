package org.uade.ad.trucoserver.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Table (name = "tipo_envites")
@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoEnvite", discriminatorType=DiscriminatorType.STRING)
public abstract class Envite {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int idTipoEnvite;
	@Column
	protected String nombreEnvite;
	@Column
	protected String tipoEnvite;
	@Column
	protected int puntajeQuerido;
	@Column
	protected int puntajeNoQuerido;
	@Column
	protected int enviteAnterior;
	
	
	public Envite() {
		super();

	}
	
	public Envite(int idTipoEnvite, String nombreEnvite, String tipoEnvite, int puntajeQuerido, int puntajeNoQuerido,
			int enviteAnterior) {
		super();
		this.idTipoEnvite = idTipoEnvite;
		this.nombreEnvite = nombreEnvite;
		this.tipoEnvite = tipoEnvite;
		this.puntajeQuerido = puntajeQuerido;
		this.puntajeNoQuerido = puntajeNoQuerido;
		this.enviteAnterior = enviteAnterior;
	}
	public int getIdTipoEnvite() {
		return idTipoEnvite;
	}
	public void setIdTipoEnvite(int idTipoEnvite) {
		this.idTipoEnvite = idTipoEnvite;
	}
	public String getNombreEnvite() {
		return nombreEnvite;
	}
	public void setNombreEnvite(String nombreEnvite) {
		this.nombreEnvite = nombreEnvite;
	}
	public String getTipoEnvite() {
		return tipoEnvite;
	}
	public void setTipoEnvite(String tipoEnvite) {
		this.tipoEnvite = tipoEnvite;
	}
	public int getPuntajeQuerido() {
		return puntajeQuerido;
	}
	public void setPuntajeQuerido(int puntajeQuerido) {
		this.puntajeQuerido = puntajeQuerido;
	}
	public int getPuntajeNoQuerido() {
		return puntajeNoQuerido;
	}
	public void setPuntajeNoQuerido(int puntajeNoQuerido) {
		this.puntajeNoQuerido = puntajeNoQuerido;
	}
	public int getEnviteAnterior() {
		return enviteAnterior;
	}
	public void setEnviteAnterior(int enviteAnterior) {
		this.enviteAnterior = enviteAnterior;
	}
	
	
}
	
	