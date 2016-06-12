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
import javax.persistence.Table;

import org.uade.ad.trucorepo.dtos.EnviteDTO;

@Table (name = "tipo_envites")
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoEnvite", discriminatorType=DiscriminatorType.STRING)
public abstract class Envite implements HasDTO<EnviteDTO> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int idTipoEnvite;
	@Column
	protected String nombreEnvite;
	@Column
	protected int puntaje;
	@Column
	protected int enviteAnterior;
	
	public Envite() {
		super();

	}
	
	public Envite(int idTipoEnvite, String nombreEnvite, int puntajeQuerido, int puntajeNoQuerido,
			int enviteAnterior) {
		super();
		this.idTipoEnvite = idTipoEnvite;
		this.nombreEnvite = nombreEnvite;
		this.puntaje = puntajeQuerido;
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
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntajeQuerido) {
		this.puntaje = puntajeQuerido;
	}

	public int getEnviteAnterior() {
		return enviteAnterior;
	}
	public void setEnviteAnterior(int enviteAnterior) {
		this.enviteAnterior = enviteAnterior;
	}
	
	@Override
	public EnviteDTO getDTO() {
		EnviteDTO dto = new EnviteDTO();
		return dto;
	}
}
