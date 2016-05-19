package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipoPartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private int puntosVictoria;
	
	public TipoPartidaDTO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntosVictoria() {
		return puntosVictoria;
	}

	public void setPuntosVictoria(int puntosVictoria) {
		this.puntosVictoria = puntosVictoria;
	}
}
