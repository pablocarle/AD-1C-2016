package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EnviteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private int idEnvite;
	@XmlAttribute
	private String nombre;
	
	public EnviteDTO() {
		super();
	}

	public int getIdEnvite() {
		return idEnvite;
	}

	public void setIdEnvite(int idEnvite) {
		this.idEnvite = idEnvite;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
