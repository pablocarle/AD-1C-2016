package org.uade.ad.trucoserver.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue ("C")
public class EnvidoEnvite extends Envite {

	public EnvidoEnvite() {
		super();
	}

	public EnvidoEnvite(int idTipoEnvite, String nombreEnvite, int puntajeQuerido,
			int puntajeNoQuerido, int enviteAnterior) {
		super(idTipoEnvite, nombreEnvite, puntajeQuerido, puntajeNoQuerido, enviteAnterior);
	}
}
