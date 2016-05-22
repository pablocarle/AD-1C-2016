package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Partida")
public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	private int idPartida;
	private TipoPartidaDTO tipoPartida;
	private List<ChicoDTO> chicos;
	private String estado;
	private ParejaDTO pareja1;
	private ParejaDTO pareja2;
	
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
}
