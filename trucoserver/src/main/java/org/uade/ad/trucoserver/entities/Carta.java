package org.uade.ad.trucoserver.entities;

import java.util.Comparator;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.uade.ad.trucorepo.dtos.CartaDTO;

/**
 * El orden natural es por numero de carta?
 * 
 * @author Grupo9
 *
 */
@Entity(name="cartas")
@Access(value=AccessType.FIELD)
public class Carta implements Comparable<Carta>, HasDTO<CartaDTO> {

	@Id
	private int idCarta;
	@Column
	private String palo;
	@Column
	private int pesoEnvido;
	@Column
	private int pesoTruco;
	@Column
	private int numero;

	//Hibernate requiere un constructor que no tome argumentos
	public Carta() {
		super();
	}
	
	public Carta(int idCarta, String palo, int pesoEnvido, int pesoTruco, int numero) {
		super();
		this.idCarta = idCarta;
		this.palo = palo;
		this.pesoEnvido = pesoEnvido;
		this.pesoTruco = pesoTruco;
		this.numero = numero;
	}

	@Override
	public String toString() {
		return numero + " de " + palo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCarta;
		result = prime * result + numero;
		result = prime * result + ((palo == null) ? 0 : palo.hashCode());
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
		Carta other = (Carta) obj;
		if (idCarta != other.idCarta)
			return false;
		if (numero != other.numero)
			return false;
		if (palo == null) {
			if (other.palo != null)
				return false;
		} else if (!palo.equals(other.palo))
			return false;
		return true;
	}

	public int compareTo(Carta o) {
		if (numero < o.numero)
			return -1;
		else if (numero > o.numero)
			return 1;
		return 0;
	}

	public int getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}

	public String getPalo() {
		return palo;
	}

	public void setPalo(String palo) {
		this.palo = palo;
	}

	public int getPesoEnvido() {
		return pesoEnvido;
	}

	public void setPesoEnvido(int pesoEnvido) {
		this.pesoEnvido = pesoEnvido;
	}

	public int getPesoTruco() {
		return pesoTruco;
	}

	public void setPesoTruco(int pesoTruco) {
		this.pesoTruco = pesoTruco;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	/**
	 * Comparador por valor de truco
	 * 
	 * @author Grupo9
	 *
	 */
	public static final class ValorTrucoComparador implements Comparator<Carta> {

		private boolean ascending;
		
		public ValorTrucoComparador(boolean ascending) {
			super();
			this.ascending = ascending;
		}
		
		public int compare(Carta o1, Carta o2) {
			if (o1.getPesoTruco() < o2.getPesoTruco())
				return ascending ? -1 : 1;
			else if (o1.getPesoTruco() > o2.getPesoTruco())
				return ascending ? 1 : -1;
			return 0;
		}
	}

	@Override
	public CartaDTO getDTO() {
		return new CartaDTO();
	}
}
