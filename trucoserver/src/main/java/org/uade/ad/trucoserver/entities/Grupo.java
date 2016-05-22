package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.exceptions.GrupoException;
import org.uade.ad.trucoserver.entities.GrupoDetalle.GrupoDetallePk;

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
	
	@OneToMany(mappedBy="pk.grupo")
	private List<GrupoDetalle> detalle;
	
	public Grupo() {
		super();
	}
	
	public Grupo(String nombre, Jugador admin, Pareja pareja1, Pareja pareja2) throws GrupoException {
		super();
		assert(!pareja1.equals(pareja2));
		this.nombre = nombre;
		this.detalle = new ArrayList<>();
		this.detalle.add(new GrupoDetalle(new GrupoDetallePk(pareja1, this)));
		this.detalle.add(new GrupoDetalle(new GrupoDetallePk(pareja2, this)));
		if (!pareja1.contieneJugador(admin) && !pareja2.contieneJugador(admin))
			throw new GrupoException("Admin " + admin + " no pertenece a ninguna de las parejas del grupo provistas");
		this.admin = admin;
	}
	
	public List<Pareja> getParejas() {
		List<Pareja> parejas = new ArrayList<>();
		for (GrupoDetalle detalle : this.detalle) {
			if (!detalle.isEliminado()) {
				parejas.add(detalle.getPk().getPareja());
			}
		}
		return parejas;
	}
	
	public Pareja getParejaNum(int parejaNum) {
		List<Pareja> parejas = getParejas();
		switch (parejaNum) {
		case 0:
			return parejas.get(0);
		case 1:
			return parejas.get(1);
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
		dto.setPareja1(getParejaNum(0).getDTO());
		dto.setPareja2(getParejaNum(1).getDTO());
		dto.setAdmin(admin.getDTO());
		dto.setNombre(nombre);
		return dto;
	}

	public List<Jugador> getJugadoresNoAdmin() {
		List<Jugador> retList = new ArrayList<>();
		if (!admin.equals(getParejaNum(0).getJugador1()))
			retList.add(getParejaNum(0).getJugador1());
		if (!admin.equals(getParejaNum(0).getJugador2()))
			retList.add(getParejaNum(0).getJugador2());
		if (!admin.equals(getParejaNum(1).getJugador1()));
			retList.add(getParejaNum(1).getJugador1());
		if (!admin.equals(getParejaNum(1).getJugador2()))
			retList.add(getParejaNum(1).getJugador2());
		return retList;
	}
}
