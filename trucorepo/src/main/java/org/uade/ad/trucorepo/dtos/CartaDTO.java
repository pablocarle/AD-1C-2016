package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

public class CartaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idCarta;
	private int pesoTruco;
	private int pesoEnvido;
	private String palo;
	private int numero;
	
	public CartaDTO() {
		super();
	}
	
	public CartaDTO(int idCarta, int pesoTruco, int pesoEnvido, String palo, int numero) {
		super();
		this.idCarta = idCarta;
		this.pesoTruco = pesoTruco;
		this.pesoEnvido = pesoEnvido;
		this.palo = palo;
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "CartaDTO [idCarta=" + idCarta + ", pesoTruco=" + pesoTruco + ", pesoEnvido=" + pesoEnvido + ", palo="
				+ palo + ", numero=" + numero + "]";
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
		CartaDTO other = (CartaDTO) obj;
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

	public int getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}

	public int getPesoTruco() {
		return pesoTruco;
	}

	public void setPesoTruco(int pesoTruco) {
		this.pesoTruco = pesoTruco;
	}

	public int getPesoEnvido() {
		return pesoEnvido;
	}

	public void setPesoEnvido(int pesoEnvido) {
		this.pesoEnvido = pesoEnvido;
	}

	public String getPalo() {
		return palo;
	}

	public void setPalo(String palo) {
		this.palo = palo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
