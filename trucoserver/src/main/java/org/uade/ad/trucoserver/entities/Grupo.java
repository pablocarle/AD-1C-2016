package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="grupos")
public class Grupo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idGrupo;
	@Column
	private String nombre;
	@ManyToOne
	@JoinColumn(name="idPareja1")
	private Pareja pareja1;
	@ManyToOne
	@JoinColumn(name="idPareja2")
	private Pareja pareja2;
	
	public Grupo() {
		super();
	}
	
	public Grupo(Pareja pareja1, Pareja pareja2) {
		super();
		assert(!pareja1.equals(pareja2));
		this.pareja1 = pareja1;
		this.pareja2 = pareja2;
	}
	
	public List<Pareja> getParejas() {
		List<Pareja> parejas = new ArrayList<>(2);
		parejas.add(pareja1);
		parejas.add(pareja2);
		return parejas;
	}
	
	public Pareja getParejaNum(int parejaNum) throws Exception {
		switch (parejaNum) {
		case 0:
			return pareja1;
		case 1:
			return pareja2;
			default:
				throw new RuntimeException("No se identifico pareja numero: " + parejaNum);
		}
	}
	
	@Override
	public String toString() {
		return "Grupo [idGrupo=" + idGrupo + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Grupo other = (Grupo) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
