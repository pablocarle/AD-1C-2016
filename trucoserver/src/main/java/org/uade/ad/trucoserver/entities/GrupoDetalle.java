package org.uade.ad.trucoserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="grupos_detail")
public class GrupoDetalle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idGrupoDetail;
	@ManyToOne
	private Grupo grupo;
	@ManyToOne
	private Jugador jugador;
	@Column
	private boolean admin;
	@Column
	private int parejaNum;
	
	public GrupoDetalle() {
		super();
	}

	@Override
	public String toString() {
		return "GrupoDetalle [idGrupoDetail=" + idGrupoDetail + ", grupo=" + grupo + ", jugador=" + jugador + ", admin="
				+ admin + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((jugador == null) ? 0 : jugador.hashCode());
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
		GrupoDetalle other = (GrupoDetalle) obj;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (jugador == null) {
			if (other.jugador != null)
				return false;
		} else if (!jugador.equals(other.jugador))
			return false;
		return true;
	}

	public int getIdGrupoDetail() {
		return idGrupoDetail;
	}

	public void setIdGrupoDetail(int idGrupoDetail) {
		this.idGrupoDetail = idGrupoDetail;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getParejaNum() {
		return parejaNum;
	}

	public void setParejaNum(int parejaNum) {
		this.parejaNum = parejaNum;
	}
}
