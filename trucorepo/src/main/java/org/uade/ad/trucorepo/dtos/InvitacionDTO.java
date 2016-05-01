package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa invitacion a un juego (pareja / grupo)
 * 
 * @author Grupo9
 *
 */
@XmlRootElement
public class InvitacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String invitacionDe;

	public InvitacionDTO() {
		super();
	}

}
