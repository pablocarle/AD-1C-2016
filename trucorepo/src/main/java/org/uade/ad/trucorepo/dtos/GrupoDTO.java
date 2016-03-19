package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.Set;

public class GrupoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private Set<JugadorDTO> integrantes;
	
	public GrupoDTO() {
		super();
	}
	
	public GrupoDTO(String nombre, Set<JugadorDTO> integrantes) {
		super();
		this.nombre = nombre;
		this.integrantes = integrantes;
	}

	@Override
	public String toString() {
		return "GrupoDTO [nombre=" + nombre + ", integrantes=" + integrantes + "]";
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
		GrupoDTO other = (GrupoDTO) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public Set<JugadorDTO> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(Set<JugadorDTO> integrantes) {
		this.integrantes = integrantes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
