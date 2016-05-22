package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;

@Entity
@Table(name="jugadores")
public class Jugador implements HasDTO<JugadorDTO> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idJugador;
	@Column(length=50)
	private String nombre;
	@Column(length=100)
	private String email;
	@Column(length=50)
	private String apodo;
	@Column(length=50)
	private String password;
	
	@ManyToOne
	@JoinColumn(name="idCategoria")
	private Categoria categoria;
	@OneToMany(mappedBy="admin", fetch=FetchType.EAGER)
	private List<Grupo> grupos;
	
	public Jugador() {
		super();
	}
	
	public Jugador(String email, String apodo, String password) {
		super();
		this.email = email;
		this.apodo = apodo;
		this.nombre = apodo;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Jugador [idJugador=" + idJugador + ", email=" + email + ", apodo=" + apodo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apodo == null) ? 0 : apodo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Jugador other = (Jugador) obj;
		if (apodo == null) {
			if (other.apodo != null)
				return false;
		} else if (!apodo.equals(other.apodo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public JugadorDTO getDTO() {
		JugadorDTO jugadorDTO = new JugadorDTO();
		jugadorDTO.setEmail(this.email);
		jugadorDTO.setApodo(this.apodo);
		jugadorDTO.setGrupos(getDTOs(grupos));
		return jugadorDTO;
	}

	private List<GrupoDTO> getDTOs(List<Grupo> grupos) {
		if (grupos != null) {
			List<GrupoDTO> retList = new ArrayList<>(grupos.size());
			for (Grupo g : grupos) {
				retList.add(g.getDTO());
			}
			return retList;
		} else {
			return null;
		}
	}
}
