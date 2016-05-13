package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Jugador;

public final class RankingItem implements Comparable<RankingItem> {

	private Jugador jugador;
	private long puntos;
	private long partidasGanadas;
	private long partidasJugadas;
	private long promedioGanadas;
	
	public RankingItem(Jugador jugador, long puntos, long partidasGanadas, long partidasJugadas) {
		super();
		this.jugador = jugador;
		this.puntos = puntos;
		this.partidasGanadas = partidasGanadas;
		this.partidasJugadas = partidasJugadas;
		this.promedioGanadas = partidasGanadas / partidasJugadas;
	}
	
	public void agregarPartidaGanada(int puntosObtenidos) {
		puntos += puntosObtenidos;
		partidasGanadas++;
		partidasJugadas++;
		this.promedioGanadas = partidasGanadas / partidasJugadas;
	}
	
	public void agregarPartidaPerdida() {
		partidasJugadas++;
		this.promedioGanadas = partidasGanadas / partidasJugadas;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jugador == null) ? 0 : jugador.hashCode());
		result = prime * result + (int) (puntos ^ (puntos >>> 32));
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
		RankingItem other = (RankingItem) obj;
		if (jugador == null) {
			if (other.jugador != null)
				return false;
		} else if (!jugador.equals(other.jugador))
			return false;
		if (puntos != other.puntos)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RankingItem [jugador=" + jugador + ", puntos=" + puntos + ", partidasGanadas=" + partidasGanadas
				+ ", partidasJugadas=" + partidasJugadas + "]";
	}

	public Jugador getJugador() {
		return jugador;
	}
	public long getPuntos() {
		return puntos;
	}
	public long getPartidasGanadas() {
		return partidasGanadas;
	}
	public long getPartidasJugadas() {
		return partidasJugadas;
	}
	public long getPromedioGanadas() {
		return promedioGanadas;
	}

	@Override
	public int compareTo(RankingItem o) {
		if (this.equals(o) || this == o)
			return 0;
		if (this.puntos == o.puntos)
			return 0;
		if (this.puntos < o.puntos)
			return -1;
		if (this.puntos > o.puntos)
			return 1;
		return 0;
	}
}
