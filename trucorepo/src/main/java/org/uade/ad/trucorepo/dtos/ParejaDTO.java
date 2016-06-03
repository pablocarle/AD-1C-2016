package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ParejaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private JugadorDTO jugador1;
	@XmlElement
	private JugadorDTO jugador2;
	
	public ParejaDTO() {
		super();
	}

	public JugadorDTO getJugador1() {
		return jugador1;
	}

	public void setJugador1(JugadorDTO jugador1) {
		this.jugador1 = jugador1;
	}

	public JugadorDTO getJugador2() {
		return jugador2;
	}

	public void setJugador2(JugadorDTO jugador2) {
		this.jugador2 = jugador2;
	}
}
