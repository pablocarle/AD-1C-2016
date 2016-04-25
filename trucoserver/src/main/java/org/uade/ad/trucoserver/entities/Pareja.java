package org.uade.ad.trucoserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="parejas")
public class Pareja {

	public static final Pareja Null = new Pareja(null, null);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPareja;
	@ManyToOne
	@JoinColumn(name="idJugador1")
	private Jugador jugador1;
	@ManyToOne
	@JoinColumn(name="idJugador2")
	private Jugador jugador2;

	public Pareja() {
		super();
	}
	
	public Pareja(Jugador jugador1, Jugador jugador2) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}

	public boolean contieneJugador(Jugador jugador) {
		return jugador != null && (jugador.equals(jugador1) || jugador.equals(jugador2));
	}

	public Categoria getCategoria() {
		int compare = jugador1.getCategoria().compareTo(jugador2.getCategoria());
		if (compare == 0)
			return jugador1.getCategoria();
		else if (compare < 0) {
			return jugador2.getCategoria();
		} else {
			return jugador1.getCategoria();
		}
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
		} 
		if (jugador2 == null) {
			if (other.jugador2 != null)
				return false;
		}
		if ((jugador1.equals(other.jugador1) && jugador2.equals(other.jugador2))
				|| (jugador1.equals(other.jugador2) && jugador2.equals(other.jugador1))
				)
			return true;
		return false;
	}
	
	public Jugador getJugador1() {
		return jugador1;
	}
	
	public Jugador getJugador2() {
		return jugador2;
	}
	
	public int getIdPareja() {
		return idPareja;
	}
}
