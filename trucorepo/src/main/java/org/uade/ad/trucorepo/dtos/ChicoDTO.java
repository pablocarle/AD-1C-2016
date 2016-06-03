package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChicoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private int puntosPareja1;
	@XmlElement
	private int puntosPareja2;
	
	public ChicoDTO() {
		super();
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
}
