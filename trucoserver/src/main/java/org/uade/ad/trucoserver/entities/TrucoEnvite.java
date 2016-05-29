package org.uade.ad.trucoserver.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
}
