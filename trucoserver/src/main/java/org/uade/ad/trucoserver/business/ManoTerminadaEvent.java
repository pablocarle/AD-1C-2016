package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Mano;

public class ManoTerminadaEvent {
	
	private int idPartida;
	private Mano mano;
	
	private int puntosObtenidosPareja1;
	private int puntosObtenidosPareja2;

	public ManoTerminadaEvent(int idPartida, Mano mano, int puntosObtenidosPareja1, int puntosObtenidosPareja2) {
		super();
		this.puntosObtenidosPareja1 = puntosObtenidosPareja1;
		this.puntosObtenidosPareja2 = puntosObtenidosPareja2;
		this.mano = mano;
		this.idPartida = idPartida;
	}

	public int getPuntosObtenidosPareja1() {
		return puntosObtenidosPareja1;
	}
	public void setPuntosObtenidosPareja1(int puntosObtenidosPareja1) {
		this.puntosObtenidosPareja1 = puntosObtenidosPareja1;
	}
	public int getPuntosObtenidosPareja2() {
		return puntosObtenidosPareja2;
	}
	public void setPuntosObtenidosPareja2(int puntosObtenidosPareja2) {
		this.puntosObtenidosPareja2 = puntosObtenidosPareja2;
	}
	public Mano getMano() {
		return mano;
	}
	public void setMano(Mano mano) {
		this.mano = mano;
	}

	public int getIdPartida() {
		return idPartida;
	}
}
