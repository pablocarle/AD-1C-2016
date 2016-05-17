package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idPartida;
	private ParejaDTO pareja1;
	private ParejaDTO pareja2;
	
	private int puntosPareja1;
	private int puntosPareja2;
	
	private String estado;
	
	public PartidaDTO() {
		super();
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
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

	public int getPuntosPareja1() {
		return puntosPareja1;
	}

	public void setPuntosPareja1(int puntosPareja1) {
		this.puntosPareja1 = puntosPareja1;
	}

	public int getPuntosPareja2() {
		return puntosPareja2;
	}

	public void setPuntosPareja2(int puntosPareja2) {
		this.puntosPareja2 = puntosPareja2;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
