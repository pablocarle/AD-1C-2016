package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GrupoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private int idGrupo;
	private ParejaDTO pareja1;
	private ParejaDTO pareja2;
	private String admin;
	
	public GrupoDTO() {
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
		GrupoDTO other = (GrupoDTO) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ParejaDTO getPareja1() {
		return pareja1;
	}

	public void setPareja1(ParejaDTO pareja1) {
		this.pareja1 = pareja1;
	}

	public ParejaDTO getPareja2() {
		return pareja2;
	}

	public void setPareja2(ParejaDTO pareja2) {
		this.pareja2 = pareja2;
	}

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
