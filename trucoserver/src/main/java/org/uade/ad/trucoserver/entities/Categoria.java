package org.uade.ad.trucoserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categorias")
public class Categoria implements Comparable<Categoria> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCategoria;
	@Column
	private int ordenCategoria;
	@Column
	private String nombre;
	@Column
	private int puntajeMin;
	@Column
	private int partidosMin;
	@Column
	private int promedioVictoriasMin;
	
	public Categoria() {
		super();
	}
	
	public Categoria(int idCategoria, int ordenCategoria, String nombre, int puntajeMin, int partidosMin,
			int promedioVictoriasMin) {
		super();
		this.idCategoria = idCategoria;
		this.ordenCategoria = ordenCategoria;
		this.nombre = nombre;
		this.puntajeMin = puntajeMin;
		this.partidosMin = partidosMin;
		this.promedioVictoriasMin = promedioVictoriasMin;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", ordenCategoria=" + ordenCategoria + ", nombre=" + nombre
				+ ", puntajeMin=" + puntajeMin + ", partidosMin=" + partidosMin + ", promedioVictoriasMin="
				+ promedioVictoriasMin + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCategoria;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Categoria other = (Categoria) obj;
		if (idCategoria != other.idCategoria)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getOrdenCategoria() {
		return ordenCategoria;
	}

	public void setOrdenCategoria(int ordenCategoria) {
		this.ordenCategoria = ordenCategoria;
	}

	@Override
	public int compareTo(Categoria o) {
		if (this.ordenCategoria < o.ordenCategoria) {
			return -1;
		} else if (this.ordenCategoria > o.ordenCategoria)
			return 1;
		return 0;
	}
}
