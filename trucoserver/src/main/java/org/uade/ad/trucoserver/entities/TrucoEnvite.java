package org.uade.ad.trucoserver.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.uade.ad.trucorepo.dtos.EnviteDTO;

@Entity
@DiscriminatorValue ("T")
public class TrucoEnvite extends Envite {

	public TrucoEnvite() {
		super();
	}

	public TrucoEnvite(int idTipoEnvite, String nombreEnvite, int puntajeQuerido,
			int puntajeNoQuerido, int enviteAnterior) {
		super(idTipoEnvite, nombreEnvite, puntajeQuerido, puntajeNoQuerido, enviteAnterior);
	}
	
	@Override
	public EnviteDTO getDTO() {
		EnviteDTO dto = super.getDTO();
		return dto;
	}
}
