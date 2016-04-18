package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uade.ad.trucoserver.entities.Baza.BazaResultado;

@Entity
@Table(name="manos")
public class Mano {
	
	public static final int BAZAS_MAX_SIZE = 3;
	public static final int NUM_JUGADORES = 4;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMano;
	@Transient
	private Pareja pareja1;
	@Transient
	private Pareja pareja2;
	@OneToMany
	private List<Envite> envites;
	{
		envites = new ArrayList<>();
	}
	@OneToMany(mappedBy="mano")
	private List<Baza> bazas;
	@Transient
	private List<Jugador> ordenJuego;
	
	public Mano(Pareja pareja1, Pareja pareja2, List<Jugador> ordenJuego) {
		super();
		this.pareja1 = pareja1;
		this.pareja2 = pareja2;
		this.ordenJuego = ordenJuego;
	}
	
	public List<Baza> getBazas() {
		return new ArrayList<>(bazas);
	}
	
	public void jugar(Jugador jugador, Carta carta) {
		if (bazas == null) {
			bazas = new ArrayList<>(BAZAS_MAX_SIZE);
		} else if (bazas.size() == BAZAS_MAX_SIZE && bazas.get(bazas.size() - 1).esCompleta()) {
			throw new RuntimeException("??");
		}
		Baza bazaActual = null;
		if (bazas.isEmpty()) {
			bazaActual = new Baza(NUM_JUGADORES, ordenJuego);
			bazas.add(bazaActual);
			bazaActual.jugarCarta(jugador, carta);
		} else {
			bazaActual = bazas.get(bazas.size() - 1);
			if (bazaActual.esCompleta() && getGanador() != Pareja.Null) {
				Jugador ganadorAnterior = bazaActual.getResultado().getJugador();
				bazaActual = new Baza(NUM_JUGADORES, getNuevoOrdenJuego(ganadorAnterior, ordenJuego)); //TODO Orden de juego actualizado segun quien gano la ultima baja
				bazaActual.jugarCarta(jugador, carta);
			} else if (!bazaActual.esCompleta()) {
				bazaActual.jugarCarta(jugador, carta);
			}
		}
	}
	
	private List<Jugador> getNuevoOrdenJuego(Jugador ganadorAnterior, List<Jugador> ordenJuegoOriginal) {
		List<Jugador> retList = new ArrayList<>(ordenJuegoOriginal.size());
		retList.add(ganadorAnterior);
		int index = ordenJuegoOriginal.indexOf(ganadorAnterior);
		if (index == ordenJuegoOriginal.size() - 1) {
			for (int i = 0; i < ordenJuegoOriginal.size() - 1; i++) {
				retList.add(ordenJuegoOriginal.get(i));
			}
		} else {
			for (int i = index + 1; i < ordenJuegoOriginal.size(); i++) {
				retList.add(ordenJuegoOriginal.get(i));
			}
			for (int i = 0; i < ordenJuegoOriginal.size() && i < index; i++) {
				retList.add(ordenJuegoOriginal.get(i));
			}
		}
		return retList;
	}

	/**
	 * Obtener pareja ganadora de la mano o Pareja.Null si no lo hay aun
	 * 
	 * @return
	 */
	public Pareja getGanador() {
		if (bazas.size() >= 2) {
			int countPareja1 = 0;
			int countPareja2 = 0;
			boolean todasCompletas = true;
			BazaResultado resultado = null;
			for (Baza baza : bazas) {
				resultado = baza.getResultado();
				if (baza.esCompleta()) {
					if (pareja1.contieneJugador(resultado.getJugador())) {
						countPareja1++;
					} else  if (pareja2.contieneJugador(resultado.getJugador())) {
						countPareja2++;
					}
				} else {
					todasCompletas = false;
					break;
				}
			}
			if (todasCompletas) {
				if (countPareja1 >= 2) {
					return pareja1;
				} else if (countPareja2 >= 2) {
					return pareja2;
				}
			}
		}
		return Pareja.Null;
	}

	public void cantar(Jugador jugador, Envite envite) {
		// TODO Gestionar envites en la mano
		
		envites.add(envite);
		
	}

	public boolean terminada() {
		return getGanador() != Pareja.Null;
	}
}
