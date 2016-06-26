package org.uade.ad.trucoserver.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="juego_log")
public class LogJuego {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLog;
	@Column
	private Date fecha;
	@ManyToOne
	@JoinColumn(name="idJugador")
	private Jugador jugador;
	@ManyToOne
	@JoinColumn(name="idPartida")
	private Partida partida;
	@Column(columnDefinition="bit")
	private boolean victoria;
	@Column
	private int puntos;
	
	public LogJuego() {
		super();
	}

	@Override
	public String toString() {
		return "LogJuego [idLog=" + idLog + ", fecha=" + fecha + ", jugador=" + jugador + ", victoria=" + victoria
				+ ", puntos=" + puntos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLog;
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
		LogJuego other = (LogJuego) obj;
		if (idLog != other.idLog)
			return false;
		return true;
	}

	public int getIdLog() {
		return idLog;
	}

	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public boolean isVictoria() {
		return victoria;
	}

	public void setVictoria(boolean victoria) {
		this.victoria = victoria;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}
}
