package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucoserver.business.OnEnvidoEvaluado;

@Entity
@DiscriminatorValue ("E")
public class EnvidoEnvite extends Envite {

	public EnvidoEnvite() {
		super();
	}

	public EnvidoEnvite(int idTipoEnvite, String nombreEnvite, int puntajeQuerido,
			int puntajeNoQuerido, Envite enviteAnterior) {
		super(idTipoEnvite, nombreEnvite, puntajeQuerido, puntajeNoQuerido, enviteAnterior);
	}
	
	@Override
	public EnviteDTO getDTO() {
		EnviteDTO dto = super.getDTO();
		return dto;
	}

	public Jugador calcular(Map<Jugador, Set<Carta>> cartasAsignadas, List<Jugador> ordenJuegoInicial, OnEnvidoEvaluado callback) {
		int maxEnvido = Integer.MIN_VALUE;
		Jugador ganador = null;
		int envido = 0;
		List<Carta> cartas = null;
		for (Jugador j : ordenJuegoInicial) {
			cartas = new ArrayList<>(cartasAsignadas.get(j));
			if (cartas.size() == 3) {
				envido = calcularEnvido(cartas.get(0), cartas.get(1), cartas.get(2));
				if (callback != null) {
					callback.ejecutar(j, envido);
				}
				if (envido > maxEnvido) {
					maxEnvido = envido;
					ganador = j;
				}
			} else {
				throw new RuntimeException("Debe haber 3 cartas para verificar envido");
			}
		}
		return ganador;
	}
	
	public int calcular(Map.Entry<Jugador, Set<Carta>> cartasAsignadas) {
		if (cartasAsignadas.getValue().size() == 3) {
			Carta[] cartas = cartasAsignadas.getValue().toArray(new Carta[0]);
			return calcularEnvido(cartas[0], cartas[1], cartas[2]);
		} else {
			throw new RuntimeException("Debe haber 3 cartas para verificar envido");
		}
	}
	
	private int calcularEnvido(final Carta c1, final Carta c2, final Carta c3) {
		//TODO Verificar orden dado por comparador
		Carta[] cartas = new Carta[]{c1, c2, c3};
		if (!c1.getPalo().equals(c2.getPalo()) && !c1.getPalo().equals(c3.getPalo()) && !c2.getPalo().equals(c3.getPalo())) {
			Arrays.sort(cartas, new Carta.ValorEnvidoComparador(-1));
			return cartas[0].getPesoEnvido();
		} else if (c1.getPalo().equals(c2.getPalo()) && c2.getPalo().equals(c3.getPalo())) {
			Arrays.sort(cartas, new Carta.ValorEnvidoComparador(-1));
			return cartas[0].getPesoEnvido() + cartas[1].getPesoEnvido() + 20;
		} else {
			if (c1.getPalo().equals(c2.getPalo())) {
				return c1.getPesoEnvido() + c2.getPesoEnvido() + 20;
			} else if (c2.getPalo().equals(c3.getPalo())) {
				return c2.getPesoEnvido() + c3.getPesoEnvido() + 20;
			} else if (c1.getPalo().equals(c3.getPalo())) {
				return c1.getPesoEnvido() + c3.getPesoEnvido() + 20;
			}
			return 0;
		}
	}
}
