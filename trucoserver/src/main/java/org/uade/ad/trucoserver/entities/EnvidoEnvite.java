package org.uade.ad.trucoserver.entities;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue ("C")
public class EnvidoEnvite extends Envite {

	public EnvidoEnvite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnvidoEnvite(int idTipoEnvite, String nombreEnvite, char tipoEnvite, int puntajeQuerido,
			int puntajeNoQuerido, int enviteAnterior) {
		super(idTipoEnvite, nombreEnvite, tipoEnvite, puntajeQuerido, puntajeNoQuerido, enviteAnterior);
		// TODO Auto-generated constructor stub
	}



	
	
}
