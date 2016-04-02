package org.uade.ad.trucoserver.entities;

import java.util.Date;

public class LogJuego {

	private int idLog;
	private Date fecha;
	private Jugador jugador;
	private boolean victoria;
	private int puntos;
	
	public LogJuego() {
		// TODO Auto-generated constructor stub
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
}
