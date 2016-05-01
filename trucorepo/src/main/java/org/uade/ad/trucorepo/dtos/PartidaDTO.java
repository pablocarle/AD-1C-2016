package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idPartida;
	private ParejaDTO pareja1;
	private ParejaDTO pareja2;
	
	private int puntosPareja1;
	private int puntosPareja2;
	
	public PartidaDTO() {
		super();
	}

}
