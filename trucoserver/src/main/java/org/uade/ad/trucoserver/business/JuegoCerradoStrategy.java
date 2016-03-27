package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Grupo;

public class JuegoCerradoStrategy extends JuegoStrategy {

	//Guarda por si hace falta por alguna razon algo que solo nos da el grupo y no las parejas
	private Grupo grupo;
	
	public JuegoCerradoStrategy(Grupo grupo) throws Exception {
		super(grupo.getParejaNum(0), grupo.getParejaNum(1));
		this.grupo = grupo;
	}

}
