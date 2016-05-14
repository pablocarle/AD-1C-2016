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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.exceptions.GrupoException;

@Entity
@Table(name="grupos")
public class Grupo implements HasDTO<GrupoDTO> {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idGrupo;
	@Column
	private String nombre;
	@OneToOne
	@JoinColumn(name="idJugadorAdmin")
	private Jugador admin;
	@ManyToOne
	@JoinColumn(name="idPareja1")
	private Pareja pareja1;
	@ManyToOne
	@JoinColumn(name="idPareja2")
	private Pareja pareja2;
	
	public Grupo() {
		super();
	}
	
	public Grupo(String nombre, Jugador admin, Pareja pareja1, Pareja pareja2) throws GrupoException {
		super();
		assert(!pareja1.equals(pareja2));
		this.nombre = nombre;
		this.pareja1 = pareja1;
		this.pareja2 = pareja2;
		if (!pareja1.contieneJugador(admin) && !pareja2.contieneJugador(admin))
			throw new GrupoException("Admin " + admin + " no pertenece a ninguna de las parejas del grupo provistas");
		this.admin = admin;
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

	@Override
	public GrupoDTO getDTO() {
		GrupoDTO dto = new GrupoDTO();
		dto.setPareja1(pareja1.getDTO());
		dto.setPareja2(pareja2.getDTO());
		dto.setAdmin(admin.getDTO());
		dto.setNombre(nombre);
		return dto;
	}
}
