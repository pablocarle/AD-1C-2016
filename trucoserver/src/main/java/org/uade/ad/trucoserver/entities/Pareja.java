package org.uade.ad.trucoserver.entities;

public class Pareja extends Jugador {

	public static final Pareja Null = new Pareja(null, null);
	private Jugador jugador1;
	private Jugador jugador2;

	public Pareja(Jugador jugador1, Jugador jugador2) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}

	public boolean contieneJugador(Jugador jugador) {
		return jugador != null && (jugador.equals(jugador1) || jugador.equals(jugador2));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jugador1 == null) ? 0 : jugador1.hashCode());
		result = prime * result + ((jugador2 == null) ? 0 : jugador2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Pareja other = (Pareja) obj;
		if (jugador1 == null) {
			if (other.jugador1 != null)
				return false;
		} else if (!jugador1.equals(other.jugador1))
			return false;
		if (jugador2 == null) {
			if (other.jugador2 != null)
				return false;
		} else if (!jugador2.equals(other.jugador2))
			return false;
		return true;
	}
}
