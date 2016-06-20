package org.uade.ad.trucoserver.entities;

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
}
