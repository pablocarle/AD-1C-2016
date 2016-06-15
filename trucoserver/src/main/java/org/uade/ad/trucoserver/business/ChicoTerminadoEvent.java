package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Chico;
import org.uade.ad.trucoserver.entities.Pareja;

public class ChicoTerminadoEvent {

	private Chico chico;
	private Pareja parejaGanadora;

	public ChicoTerminadoEvent(Chico chico, Pareja parejaGanadora) {
		super();
		this.chico = chico;
		this.parejaGanadora = parejaGanadora;
	}
	
	public Chico getChico() {
		return chico;
	}
	public void setChico(Chico chico) {
		this.chico = chico;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
}
