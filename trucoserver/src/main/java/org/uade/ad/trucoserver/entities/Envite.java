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
import javax.persistence.ManyToOne;
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
	@ManyToOne
	protected Envite enviteAnterior;
	
	public Envite() {
		super();
	}
	
	public Envite(int idTipoEnvite, String nombreEnvite, int puntajeQuerido, int puntajeNoQuerido, Envite enviteAnterior) {
		super();
		this.idTipoEnvite = idTipoEnvite;
		this.nombreEnvite = nombreEnvite;
		this.puntaje = puntajeQuerido;
		this.enviteAnterior = enviteAnterior;
	}
	
	public boolean posteriorA(Envite envite) {
		boolean root = enviteAnterior == null;
		do {
			// TODO Completar recursividad
		} while (!root);
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enviteAnterior == null) ? 0 : enviteAnterior.hashCode());
		result = prime * result + idTipoEnvite;
		result = prime * result + ((nombreEnvite == null) ? 0 : nombreEnvite.hashCode());
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
		Envite other = (Envite) obj;
		if (enviteAnterior == null) {
			if (other.enviteAnterior != null)
				return false;
		} else if (!enviteAnterior.equals(other.enviteAnterior))
			return false;
		if (idTipoEnvite != other.idTipoEnvite)
			return false;
		if (nombreEnvite == null) {
			if (other.nombreEnvite != null)
				return false;
		} else if (!nombreEnvite.equals(other.nombreEnvite))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Envite [nombreEnvite=" + nombreEnvite + "]";
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
	public Envite getEnviteAnterior() {
		return enviteAnterior;
	}
	public void setEnviteAnterior(Envite enviteAnterior) {
		this.enviteAnterior = enviteAnterior;
	}

	@Override
	public EnviteDTO getDTO() {
		EnviteDTO dto = new EnviteDTO();
		dto.setIdEnvite(idTipoEnvite);
		dto.setNombre(nombreEnvite);
		return dto;
	}
}
