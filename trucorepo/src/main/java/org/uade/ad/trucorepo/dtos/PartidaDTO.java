package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.List;

public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idPartida;
	private TipoPartidaDTO tipoPartida;
	private List<ChicoDTO> chicos;
	
	public PartidaDTO() {
		super();
	}
	
	public ChicoDTO getChicoActual() {
		//TODO Obtener el chico actual
		return null;
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public TipoPartidaDTO getTipoPartida() {
		return tipoPartida;
	}

	public void setTipoPartida(TipoPartidaDTO tipoPartida) {
		this.tipoPartida = tipoPartida;
	}

	public List<ChicoDTO> getChicos() {
		return chicos;
	}

	public void setChicos(List<ChicoDTO> chicos) {
		this.chicos = chicos;
	}
}
