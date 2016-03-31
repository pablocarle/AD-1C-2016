package org.uade.ad.trucoserver.entities;

public class Categoria implements Comparable<Categoria> {
	
	private int idCategoria;
	private int ordenCategoria;
	private String nombre;
	
	public Categoria() {
		super();
	}
	
	public Categoria(int idCategoria, String nombre) {
		super();
		this.idCategoria = idCategoria;
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombre=" + nombre + "]";
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
