package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;

public class JugadorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String apodo;
	
	public JugadorDTO() {
		super();
	}
	
	public JugadorDTO(String email, String apodo) {
		super();
		this.email = email;
		this.apodo = apodo;
	}

	@Override
	public String toString() {
		return "JugadorDTO [email=" + email + ", apodo=" + apodo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apodo == null) ? 0 : apodo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		JugadorDTO other = (JugadorDTO) obj;
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
		return true;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
