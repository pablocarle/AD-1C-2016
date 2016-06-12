package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CartasDisponiblesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private JugadorDTO jugador;
	@XmlElement
	private List<CartaDTO> cartas;
	
	public CartasDisponiblesDTO() {
		super();
	}
	
	public CartasDisponiblesDTO(JugadorDTO jugador, List<CartaDTO> cartas) {
		super();
		this.jugador = jugador;
		this.cartas = cartas;
	}
	
	public JugadorDTO getJugador() {
		return jugador;
	}
	public void setJugador(JugadorDTO jugador) {
		this.jugador = jugador;
	}
	public List<CartaDTO> getCartas() {
		return cartas;
	}
	public void setCartas(List<CartaDTO> cartas) {
		this.cartas = cartas;
	}
}
