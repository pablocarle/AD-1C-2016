package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NotificacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String url;
	@XmlElement
	private String descripcion;
	@XmlElement
	private int idPartida;
	@XmlElement
	private PartidaDTO partida;
	@XmlElement
	private String tipoNotificacion;
	@XmlElement
	private Date fechaNotificacion;
	
	public NotificacionDTO() {
		super();
		this.fechaNotificacion = Calendar.getInstance().getTime();
	}
	
	public NotificacionDTO(String url, String descripcion, int idPartida, PartidaDTO partida, String tipoNotificacion,
			Date fechaNotificacion) {
		super();
		this.url = url;
		this.descripcion = descripcion;
		this.idPartida = idPartida;
		this.partida = partida;
		this.tipoNotificacion = tipoNotificacion;
		this.fechaNotificacion = fechaNotificacion;
	}

	public NotificacionDTO(String mensaje) {
		this.descripcion = mensaje;
		this.fechaNotificacion = Calendar.getInstance().getTime();
		this.tipoNotificacion = "mensaje";
	}

	public NotificacionDTO(String mensaje, Integer idPartida) {
		this.descripcion = mensaje;
		this.fechaNotificacion = Calendar.getInstance().getTime();
		this.tipoNotificacion = "mensaje";
		if (idPartida != null) {
			this.idPartida = idPartida;
		}
	}

	@Override
	public String toString() {
		return "NotificacionDTO [url=" + url + ", descripcion=" + descripcion + ", partida=" + partida
				+ ", tipoNotificacion=" + tipoNotificacion + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPartida;
		result = prime * result + ((tipoNotificacion == null) ? 0 : tipoNotificacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificacionDTO other = (NotificacionDTO) obj;
		if (idPartida != other.idPartida)
			return false;
		if (tipoNotificacion == null) {
			if (other.tipoNotificacion != null)
				return false;
		} else if (!tipoNotificacion.equals(other.tipoNotificacion))
			return false;
		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public PartidaDTO getPartida() {
		return partida;
	}

	public void setPartida(PartidaDTO partida) {
		this.partida = partida;
	}

	public String getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}
}
