package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NotificacionesDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private List<NotificacionDTO> notificaciones;
	
	public NotificacionesDTO() {
		super();
	}

	public NotificacionesDTO(List<NotificacionDTO> notificaciones) {
		super();
		this.notificaciones = notificaciones;
	}

	public List<NotificacionDTO> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<NotificacionDTO> notificaciones) {
		this.notificaciones = notificaciones;
	}
}
