package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.uade.ad.trucorepo.dtos.ParejaDTO;

@Entity
@Table(name="parejas")
public class Pareja implements HasDTO<ParejaDTO> {

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
	
	public boolean contieneJugador(int idJugador) {
		return (jugador1 != null && jugador1.getIdJugador() == idJugador) ||
				(jugador2 != null && jugador2.getIdJugador() == idJugador);
	}
	
	public boolean contieneJugador(String apodoJugador) {
		return (jugador1 != null && jugador1.getApodo().equals(apodoJugador)) ||
				(jugador2 != null && jugador2.getApodo().equals(apodoJugador));
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
	
	public List<Jugador> getJugadores() {
		List<Jugador> retList = new ArrayList<>();
		retList.add(jugador1);
		retList.add(jugador2);
		return retList;
	}
	
	@Override
	public String toString() {
		return "[" + jugador1.getApodo() + " y " + jugador2.getApodo() + "]";
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
	
	public boolean esNull() {
		return this.equals(Null);
	}

	public void setIdPareja(int idPareja) {
		this.idPareja = idPareja;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}
	
	@Override
	public ParejaDTO getDTO() {
		ParejaDTO dto = new ParejaDTO();
		dto.setJugador1(jugador1.getApodo());
		dto.setJugador2(jugador2.getApodo());
		return dto;
	}
}
