package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;


public class RankingItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JugadorDTO jugador;
	private long puntos;
	private long partidasGanadas;
	private long partidasJugadas;
	private long promedioGanadas;
	private long posicion;
		
	public RankingItemDTO() {
		super();
	}
	
	public RankingItemDTO(JugadorDTO jugador, long puntos, long partidasGanadas, long partidasJugadas,
			long promedioGanadas, long posicion) {
		super();
		this.jugador = jugador;
		this.puntos = puntos;
		this.partidasGanadas = partidasGanadas;
		this.partidasJugadas = partidasJugadas;
		this.promedioGanadas = promedioGanadas;
		this.posicion = posicion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jugador == null) ? 0 : jugador.hashCode());
		result = prime * result + (int) (partidasGanadas ^ (partidasGanadas >>> 32));
		result = prime * result + (int) (partidasJugadas ^ (partidasJugadas >>> 32));
		result = prime * result + (int) (posicion ^ (posicion >>> 32));
		result = prime * result + (int) (promedioGanadas ^ (promedioGanadas >>> 32));
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
		RankingItemDTO other = (RankingItemDTO) obj;
		if (jugador == null) {
			if (other.jugador != null)
				return false;
		} else if (!jugador.equals(other.jugador))
			return false;
		if (partidasGanadas != other.partidasGanadas)
			return false;
		if (partidasJugadas != other.partidasJugadas)
			return false;
		if (posicion != other.posicion)
			return false;
		if (promedioGanadas != other.promedioGanadas)
			return false;
		if (puntos != other.puntos)
			return false;
		return true;
	}
	public JugadorDTO getJugador() {
		return jugador;
	}
	public void setJugador(JugadorDTO jugador) {
		this.jugador = jugador;
	}
	public long getPuntos() {
		return puntos;
	}
	public void setPuntos(long puntos) {
		this.puntos = puntos;
	}
	public long getPartidasGanadas() {
		return partidasGanadas;
	}
	public void setPartidasGanadas(long partidasGanadas) {
		this.partidasGanadas = partidasGanadas;
	}
	public long getPartidasJugadas() {
		return partidasJugadas;
	}
	public void setPartidasJugadas(long partidasJugadas) {
		this.partidasJugadas = partidasJugadas;
	}
	public long getPromedioGanadas() {
		return promedioGanadas;
	}
	public void setPromedioGanadas(long promedioGanadas) {
		this.promedioGanadas = promedioGanadas;
	}
	public long getPosicion() {
		return posicion;
	}
	public void setPosicion(long posicion) {
		this.posicion = posicion;
	}
}
