package org.uade.ad.trucoserver.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="grupos_detalle")
public class GrupoDetalle {

	@EmbeddedId
	private GrupoDetallePk pk;
	@Column(columnDefinition="bit")
	private boolean eliminado;
	@Column
	private Date fechaCreacion;
	@Column
	private Date fechaModificacion;
	
	public GrupoDetalle() {
		super();
	}
	
	public GrupoDetalle(GrupoDetallePk pk) {
		super();
		this.pk = pk;
		this.eliminado = false;
		this.fechaCreacion = Calendar.getInstance().getTime();
		this.fechaModificacion = Calendar.getInstance().getTime();
	}

	public GrupoDetallePk getPk() {
		return pk;
	}

	public void setPk(GrupoDetallePk pk) {
		this.pk = pk;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Embeddable
	public static class GrupoDetallePk implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@ManyToOne
		@JoinColumn(name="idPareja")
		private Pareja pareja;
		@ManyToOne
		@JoinColumn(name="idGrupo")
		private Grupo grupo;

		public GrupoDetallePk() {
			super();
		}
		
		public GrupoDetallePk(Pareja pareja, Grupo grupo) {
			super();
			this.pareja = pareja;
			this.grupo = grupo;
		}

		public Pareja getPareja() {
			return pareja;
		}
		public void setPareja(Pareja pareja) {
			this.pareja = pareja;
		}
		public Grupo getGrupo() {
			return grupo;
		}
		public void setGrupo(Grupo grupo) {
			this.grupo = grupo;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
			result = prime * result + ((pareja == null) ? 0 : pareja.hashCode());
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
			GrupoDetallePk other = (GrupoDetallePk) obj;
			if (grupo == null) {
				if (other.grupo != null)
					return false;
			} else if (!grupo.equals(other.grupo))
				return false;
			if (pareja == null) {
				if (other.pareja != null)
					return false;
			} else if (!pareja.equals(other.pareja))
				return false;
			return true;
		}
	}
}
