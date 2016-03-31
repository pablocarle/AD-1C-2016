package org.uade.ad.trucoserver.entities;

import javax.persistence.Entity;

import org.uade.ad.trucorepo.dtos.JugadorDTO;

@Entity(name="jugador")
public class Jugador {

	private int idJugador;
	private String email;
	private String apodo;
	private String password;
	
	private Categoria categoria;
	
	public Jugador() {
		super();
	}
	
	public Jugador(String email, String apodo, String password) {
		super();
		this.email = email;
		this.apodo = apodo;
		this.password = password;
	}

	public JugadorDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
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
}
