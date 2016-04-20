package org.uade.ad.trucoserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipopartidas")
public class TipoPartida {

	@Id
	private int idTipoPartida;
	@Column
	private String nombre;
	
	public TipoPartida() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		TipoPartida other = (TipoPartida) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoPartida [idTipoPartida=" + idTipoPartida + ", nombre=" + nombre + "]";
	}

	public int getIdTipoPartida() {
		return idTipoPartida;
	}

	public void setIdTipoPartida(int idTipoPartida) {
		this.idTipoPartida = idTipoPartida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
