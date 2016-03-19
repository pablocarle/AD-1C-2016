package org.uade.ad.trucoserver.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity(name="carta")
@Access(value=AccessType.FIELD)
public class Carta implements Comparable<Carta> {

	private int idCarta;
	private String palo;
	private int pesoEnvido;
	private int pesoTruco;
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
		return "Carta [idCarta=" + idCarta + ", palo=" + palo + ", pesoEnvido=" + pesoEnvido + ", pesoTruco="
				+ pesoTruco + ", numero=" + numero + "]";
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
		// TODO Auto-generated method stub
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
	
	
}