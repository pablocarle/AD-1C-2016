package org.uade.ad.trucoserver.entities;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.uade.ad.trucorepo.dtos.EnviteDTO;

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

	public Jugador calcular(Map<Jugador, Set<Carta>> cartasAsignadas, List<Jugador> ordenJuegoInicial) {
		int maxEnvido = Integer.MIN_VALUE;
		Jugador ganador = null;
		int envido = 0;
		Set<Carta> cartas = null;
		for (Jugador j : ordenJuegoInicial) {
			cartas = cartasAsignadas.get(j);
			envido = calcularEnvido(cartas);
			if (envido > maxEnvido) {
				maxEnvido = envido;
				ganador = j;
			}
		}
		return ganador;
	}
	
	private int calcularEnvido(Set<Carta> cartas) {
		return 0;
	}
}
