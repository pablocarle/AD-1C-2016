package org.uade.ad.trucoserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bazas_cartas")
public class CartaJugada {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBazasCartas;
	@ManyToOne
	@JoinColumn(name="idBaza")
	private Baza baza;
	@ManyToOne
	@JoinColumn(name="idJugador")
	private Jugador jugador;
	@ManyToOne
	@JoinColumn(name="idCarta")
	private Carta carta;
	
	public CartaJugada() {
		super();
	}
	
	public CartaJugada(Baza baza, Jugador jugador, Carta carta) {
		super();
		this.jugador = jugador;
		this.carta = carta;
		this.baza = baza;
	}
	
	public Jugador getJugador() {
		return jugador;
	}

	public Carta getCarta() {
		return carta;
	}

	public int getIdBazasCartas() {
		return idBazasCartas;
	}

	public void setIdCartaJugada(int idBazasCartas) {
		this.idBazasCartas = idBazasCartas;
	}

	public Baza getBaza() {
		return baza;
	}

	public void setBaza(Baza baza) {
		this.baza = baza;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public void setIdBazasCartas(int idBazasCartas) {
		this.idBazasCartas = idBazasCartas;
	}
}
