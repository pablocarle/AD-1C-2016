package org.uade.ad.trucoserver.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue ("T")
public class TrucoEnvite extends Envite {

	public TrucoEnvite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrucoEnvite(int idTipoEnvite, String nombreEnvite, char tipoEnvite, int puntajeQuerido,
			int puntajeNoQuerido, int enviteAnterior) {
		super(idTipoEnvite, nombreEnvite, tipoEnvite, puntajeQuerido, puntajeNoQuerido, enviteAnterior);
		// TODO Auto-generated constructor stub
	}



}
