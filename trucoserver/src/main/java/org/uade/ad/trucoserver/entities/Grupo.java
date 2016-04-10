package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="grupos")
public class Grupo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idGrupo;
	@Column
	private String nombre;
	@OneToMany
	private List<GrupoDetalle> detalle;
	
	public Grupo() {
		super();
	}
	
	public List<Pareja> getParejas() {
		List<Pareja> parejas = new ArrayList<>(2);
		if (detalle != null && !detalle.isEmpty()) {
			//TODO Completar
		}
		return parejas;
	}
	
	public Pareja getParejaNum(int parejaNum) throws Exception {
		Pareja pareja = null;
		if (detalle != null && !detalle.isEmpty()) {
			List<Jugador> jugadores = detalle.stream()
											 .filter(x -> x.getParejaNum() == parejaNum)
										     .map(y -> y.getJugador())
										     .collect(Collectors.toList());
			//Otra forma, en vez de lambdas: List<Jugador> jugadores = getJugadores(parejaNum);
			if (jugadores.size() == 2) {
				pareja = new Pareja(jugadores.get(0), jugadores.get(1));
				return pareja;
			} else {
				throw new Exception("Pareja numero " + parejaNum + " no contiene 2 jugadores en grupo " + this);
			}
		}
		throw new Exception("No se encontro pareja numero " + parejaNum + " en el grupo " + this);
	}
	
	@SuppressWarnings("unused")
	private List<Jugador> getJugadores(int parejaNum) {
		List<Jugador> jugadores = new ArrayList<>();
		for (GrupoDetalle detalle : this.detalle) {
			if (detalle.getParejaNum() == parejaNum) {
				jugadores.add(detalle.getJugador());
			}
		}
		return jugadores;
	}

	@Override
	public String toString() {
		return "Grupo [idGrupo=" + idGrupo + ", nombre=" + nombre + ", detalle=" + detalle + "]";
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

	public List<GrupoDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<GrupoDetalle> detalle) {
		this.detalle = detalle;
	}
}
